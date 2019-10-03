package com.poiji.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.poiji.config.DefaultCasting;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DefaultCastingTest {

    private DefaultCasting casting;
    private PoijiOptions options;

    @Before
    public void setUp() {

		casting = new DefaultCasting();
        options = PoijiOptionsBuilder.settings().build();
        Locale.setDefault(Locale.US);
    }

    @After
    public void tearDown() {
        Locale.setDefault(new Locale(""));
    }

    @Test
    public void getInstance() {

        assertNotNull(casting);
        assertFalse(casting.isErrorLoggingEnabled());
    }

    @Test(expected = IllegalStateException.class)
    public void loggingDisabledException() {
        casting.getErrors();
    }

    @Test
    public void castDate() throws Exception {

        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date expectedDate = formatter.parse("05/01/2016");

        Date testDate = (Date) casting.castValue(Date.class, "05/01/2016", options);

        assertEquals(expectedDate, testDate);
    }

    @Test
    public void castInteger() {

        Integer testVal = (Integer) casting.castValue(int.class, "10", options);

        assertEquals(new Integer(10), testVal);
    }

    @Test
    public void castDouble() {

        Double testVal = (Double) casting.castValue(double.class, "81.56891", options);

        assertEquals(new Double(81.56891), testVal);
    }

    @Test
    public void castDoubleException() {

        Integer value = (Integer) casting.castValue(int.class, "81.56891", options);

        Integer expectedValue = 0;

        assertEquals(expectedValue, value);
    }

    @Test
    public void castTextException() {

        Integer value = (Integer) casting.castValue(int.class, "1XYZ", options);

        Integer expectedValue = 0;

        assertEquals(expectedValue, value);
    }

    @Test
    public void castBoolean() {

        Boolean testVal = (Boolean) casting.castValue(boolean.class, "True", options);

        assertEquals(true, testVal);
    }

    @Test
    public void castFloat() {

        Float testVal = (Float) casting.castValue(float.class, "81.56891", options);

        assertEquals(new Float(81.56891), testVal);
    }

    @Test
    public void castLong() {

        Long expectedValue = Long.MAX_VALUE;

        Long testVal = (Long) casting.castValue(long.class, String.valueOf(expectedValue), options);

        assertEquals(expectedValue, testVal);
    }

    @Test
    public void castDoubleToLongException() {

        Long testVal = (Long) casting.castValue(long.class, "9223372036854775807.1", options);

        assertEquals(new Long(0), testVal);
    }

    @Test
    public void castLocalDate() {
        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        LocalDate expectedDate = LocalDate.of(2018, 8, 1);

        LocalDate actualDate = (LocalDate) casting.castValue(LocalDate.class, "01/08/2018", options);

        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void castLocalDateUnmatchedDateRegex()  {

        PoijiOptions options = PoijiOptionsBuilder.settings()
                .dateRegex("\\d{2}\\/\\d{2}\\/\\d{4}")
                .preferNullOverDefault(true)
                .build();

        LocalDate testLocalDate = (LocalDate) casting.castValue(LocalDate.class, "05-01-2016", options);

        assertNull(testLocalDate);
    }


    @Test
    public void castEnum() {

        TestEnum testEnum = (TestEnum) casting.castValue(TestEnum.class, "ITEM1", options);

        assertEquals(TestEnum.ITEM1, testEnum);
    }

    @Test
    public void castBigDecimal() {

        BigDecimal testVal = (BigDecimal) casting.castValue(BigDecimal.class, "81.56891", options);

        assertEquals(BigDecimal.valueOf(81.56891), testVal);
    }

    @Test
    public void castBigDecimalDE() {

        Locale.setDefault(Locale.GERMANY);
        BigDecimal testVal = (BigDecimal) casting.castValue(BigDecimal.class, "81,56", options);

        assertEquals( BigDecimal.valueOf(81.56), testVal);
    }

    private enum TestEnum {
        ITEM1, ITEM2
    }

    @Test
    //ISSUE #55 : additional functionality, trim string values
    public void trimStringDefault() {
        String testVal = (String) casting.castValue(String.class, "    value    ", options);
        assertEquals("    value    ", testVal);
    }

    @Test
    //ISSUE #55 : additional functionality, trim string values
    public void trimStringTrue() {
        PoijiOptions options = PoijiOptionsBuilder.settings().build().setTrimCellValue(true);
        String testVal = (String) casting.castValue(String.class, "    value    ", options);
        assertEquals("value", testVal);
    }

    @Test
    //ISSUE #55 : additional functionality, trim string values
    public void trimStringFalse() {
        PoijiOptions options = PoijiOptionsBuilder.settings().build().setTrimCellValue(false);
        String testVal = (String) casting.castValue(String.class, "    value    ", options);
        assertEquals("    value    ", testVal);
    }

}
