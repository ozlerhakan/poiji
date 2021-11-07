package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.bind.mapping.PoijiNumberFormat;
import com.poiji.deserialize.model.byname.DateExcelColumn;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by hakan on 2019-01-10
 */
@RunWith(Parameterized.class)
public class DeserializerCaseDateTest {

    private String path;
    private PoijiExcelType poijiExcelType;

    public DeserializerCaseDateTest(String path, PoijiExcelType type) {
        this.path = path;
        this.poijiExcelType = type;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/dates-not-empty.xlsx", PoijiExcelType.XLSX},
                {"src/test/resources/dates-not-empty-copy.xlsx", PoijiExcelType.XLSX},
        });
    }

    @Test
    public void shouldMapExcelToJava() {
        try (InputStream stream = new FileInputStream(new File(path))) {
            PoijiNumberFormat numberFormat = new PoijiNumberFormat();
            numberFormat.putNumberFormat((short) 47, "mm/dd/yyyy hh.mm aa");
            numberFormat.putNumberFormat((short) 22, "mm/dd/yyyy hh.mm aa");

            PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                    .poijiNumberFormat(numberFormat)
                    .build();

            List<DateExcelColumn> rows = Poiji.fromExcel(stream, poijiExcelType, DateExcelColumn.class, options);

            DateExcelColumn row = rows.get(0);
            assertThat(row.getDate1(), is("12/31/2020 12.00 AM"));
            assertThat(row.getDate2(), is("11/09/2015 12.00 AM"));
            assertThat(row.getDate3(), is("11/09/2015 12.00 AM"));

            assertThat(numberFormat.getNumberFormatAt((short) 47), is("mm/dd/yyyy hh.mm aa"));
            assertThat(numberFormat.getNumberFormatAt((short) 22), is("mm/dd/yyyy hh.mm aa"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldMapExcelToJavaWithNonDefaultLocale() {
        Locale userLocale = Locale.GERMANY;
        try (InputStream stream = new FileInputStream(new File(path))) {
            PoijiNumberFormat numberFormat = new PoijiNumberFormat();
            numberFormat.putNumberFormat((short) 47, "mm/dd/yyyy hh.mm aa");
            numberFormat.putNumberFormat((short) 22, "mm/dd/yyyy hh.mm aa");

            PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                    .poijiNumberFormat(numberFormat)
                    .setLocale(userLocale)
                    .build();

            List<DateExcelColumn> rows = Poiji.fromExcel(stream, poijiExcelType, DateExcelColumn.class, options);

            DateExcelColumn row = rows.get(0);

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh.mm aa", userLocale);
            Calendar calendar = Calendar.getInstance();
            calendar.set(2020, 11,31, 0, 00);
            String expectedValueCol1 = sdf.format(calendar.getTime());
            calendar.set(2015, 10,9, 0, 00);
            String expectedValueCol2And3 = sdf.format(calendar.getTime());


            assertThat(row.getDate1(), is(expectedValueCol1));
            assertThat(row.getDate2(), is(expectedValueCol2And3));
            assertThat(row.getDate3(), is(expectedValueCol2And3));

            assertThat(numberFormat.getNumberFormatAt((short) 47), is("mm/dd/yyyy hh.mm aa"));
            assertThat(numberFormat.getNumberFormatAt((short) 22), is("mm/dd/yyyy hh.mm aa"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
