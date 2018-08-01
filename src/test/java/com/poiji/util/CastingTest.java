package com.poiji.util;

import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class CastingTest {

    private Casting casting;

    @Before
    public void setUp() {

        casting = Casting.getInstance();
    }

    @Test
    public void getInstance() {

        assertNotNull(casting);
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

        PoijiOptions options = PoijiOptionsBuilder.settings().build();

        Integer testVal = (Integer) casting.castValue(int.class, "10", options);

        assertEquals(new Integer(10), testVal);
    }

    @Test
    public void castDouble() {

        PoijiOptions options = PoijiOptionsBuilder.settings().build();

        Double testVal = (Double) casting.castValue(double.class, "81.56891", options);

        assertEquals(new Double(81.56891), testVal);
    }

    @Test
    public void castDoubleException() {

        PoijiOptions options = PoijiOptionsBuilder.settings().build();

        Integer value = (Integer) casting.castValue(int.class, "81.56891", options);

        int expectedValue = 0;

        assertThat(expectedValue, is(value));
    }

    @Test
    public void castBoolean() {

        PoijiOptions options = PoijiOptionsBuilder.settings().build();

        Boolean testVal = (Boolean) casting.castValue(boolean.class, "True", options);

        assertEquals(true, testVal);
    }

    @Test
    public void castFloat() {

        PoijiOptions options = PoijiOptionsBuilder.settings().build();

        Float testVal = (Float) casting.castValue(float.class, "81.56891", options);

        assertEquals(new Float(81.56891), testVal);
    }

    @Test(expected = ClassCastException.class)
    public void castLongWrongFormat() {

        PoijiOptions options = PoijiOptionsBuilder.settings().build();

        Long testVal = (Long) casting.castValue(int.class, "9223372036854775808", options);
    }

    @Test
    public void castLong() {

        PoijiOptions options = PoijiOptionsBuilder.settings().build();

        Long testVal = (Long) casting.castValue(long.class, "9223372036854775807", options);

        assertEquals(new Long("9223372036854775807"), testVal);
    }

    @Test
    public void castLocalDate() {
        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        LocalDate expectedDate = LocalDate.of(2018, 8, 1);

        LocalDate actualDate = (LocalDate) casting.castValue(LocalDate.class, "01/08/2018", options);

        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void castEnum() {
        PoijiOptions options = PoijiOptionsBuilder.settings().build();

        TestEnum testEnum = (TestEnum) casting.castValue(TestEnum.class, "ITEM1", options);

        assertEquals(TestEnum.ITEM1, testEnum);
    }

    private enum TestEnum {
        ITEM1, ITEM2;
    }
}