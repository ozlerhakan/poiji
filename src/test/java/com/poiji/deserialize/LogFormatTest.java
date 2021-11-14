package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.bind.mapping.PoijiLogCellFormat;
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
import java.util.Arrays;
import java.util.List;

import static com.poiji.bind.mapping.PoijiLogCellFormat.InternalCellFormat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by hakan on 1.05.2020
 */
@RunWith(Parameterized.class)
public class LogFormatTest {

    private String path;
    private PoijiExcelType poijiExcelType;

    public LogFormatTest(String path, PoijiExcelType type) {
        this.path = path;
        this.poijiExcelType = type;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> query() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/dates-not-empty.xlsx", PoijiExcelType.XLSX},
        });
    }

    @Test
    public void shouldReturnInternalCellFormats() throws IOException {
        try (InputStream stream = new FileInputStream(new File(path))) {
            PoijiLogCellFormat log = new PoijiLogCellFormat();
            PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                    .poijiLogCellFormat(log)
                    .poijiNumberFormat(null)
                    .build();
            List<DateExcelColumn> dates = Poiji.fromExcel(stream, poijiExcelType, DateExcelColumn.class, options);

            assertNotNull(dates);
            assertNotNull(log.formats());

            List<InternalCellFormat> formats = log.formats();
            InternalCellFormat cell00 = formats.get(0);
            assertThat(cell00.getCellAddress().formatAsString(), is("A1"));
            assertNull(cell00.getCellStypeStr());
            assertNull(cell00.getFormatString());
            assertThat(cell00.getFormatIndex(), is((short) 0));
            assertThat(cell00.getCellType(), is("s"));

            InternalCellFormat cell01 = formats.get(1);
            assertThat(cell01.getCellAddress().formatAsString(), is("B1"));
            assertNull(cell01.getCellStypeStr());
            assertNull(cell01.getFormatString());
            assertThat(cell01.getFormatIndex(), is((short) 0));
            assertThat(cell01.getCellType(), is("s"));

            InternalCellFormat cell02 = formats.get(2);
            assertThat(cell02.getCellAddress().formatAsString(), is("C1"));
            assertNull(cell02.getCellStypeStr());
            assertNull(cell02.getFormatString());
            assertThat(cell02.getFormatIndex(), is((short) 0));
            assertThat(cell02.getCellType(), is("s"));

            InternalCellFormat cell10 = formats.get(3);
            assertThat(cell10.getCellAddress().formatAsString(), is("A2"));
            assertThat(cell10.getCellStypeStr(), is("1"));
            assertThat(cell10.getFormatString(), is("mm:ss.0"));
            assertThat(cell10.getFormatIndex(), is((short) 47));
            assertNull(cell10.getCellType());

            InternalCellFormat cell11 = formats.get(4);
            assertThat(cell11.getCellAddress().formatAsString(), is("B2"));
            assertThat(cell11.getCellStypeStr(), is("1"));
            assertThat(cell11.getFormatString(), is("mm:ss.0"));
            assertThat(cell11.getFormatIndex(), is((short) 47));
            assertNull(cell11.getCellType());

            InternalCellFormat cell12 = formats.get(5);
            assertThat(cell12.getCellAddress().formatAsString(), is("C2"));
            assertThat(cell12.getCellStypeStr(), is("1"));
            assertThat(cell12.getFormatString(), is("mm:ss.0"));
            assertThat(cell12.getFormatIndex(), is((short) 47));
            assertNull(cell12.getCellType());
        }
    }
}
