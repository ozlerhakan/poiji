package com.poiji.util;


import com.poiji.annotation.ExcelCellName;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import org.junit.After;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * ISSUE #91 : Parsing decimal value in other locales than "en" produces 0.0
 */
public class DecimalSeparatorParseTest {

    private static final String FILENAME = "decimal_separator_parse.xlsx";

    private static class Row {

        @ExcelCellName("decimalNumber")
        private double decimalNumber;

    }

    @After
    public void tearDown() {
        Locale.setDefault(new Locale(""));
    }

    @Test
    public void testLocaleUnitedStates() {
        Locale.setDefault(Locale.US);

        parseAndVerify();
    }

    @Test
    public void testLocaleGermany() {
        Locale.setDefault(Locale.GERMANY);

        parseAndVerify();
    }

    @Test
    public void testLocaleSwitzerland() {
        Locale.setDefault(new Locale("de", "CH"));

        parseAndVerify();
    }

    @Test
    public void testCustomLocaleSet() {
        InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(FILENAME);
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .preferNullOverDefault(true)
                .setLocale(Locale.GERMANY)
                .build();

        List<Row> parsedRows = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, Row.class, options);

        assertThat(parsedRows.size(), is(3));
        assertThat(parsedRows.get(0).decimalNumber, is(1.5));
        assertThat(parsedRows.get(1).decimalNumber, is(1000.0));
        assertThat(parsedRows.get(2).decimalNumber, is(123456.79));
    }

    private void parseAndVerify() {
        InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(FILENAME);
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .preferNullOverDefault(true)
                .build();

        List<Row> parsedRows = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, Row.class, options);

        assertThat(parsedRows.size(), is(3));
        assertThat(parsedRows.get(0).decimalNumber, is(1.5));
        assertThat(parsedRows.get(1).decimalNumber, is(1000.0));
        assertThat(parsedRows.get(2).decimalNumber, is(123456.79));
    }

}