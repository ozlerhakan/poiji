package com.poiji.util;

import com.poiji.exception.IllegalCastException;
import com.poiji.exception.PoijiInstantiationException;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import com.sun.xml.internal.ws.api.model.ExceptionType;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CastingTest {

    private Casting casting;

    @Before
    public void setUp() throws Exception {

        casting = Casting.getInstance();
    }

    @Test
    public void getInstance() throws Exception {

        assertNotNull(casting);
    }

    @Test
    public void castDate() throws Exception {

        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date expectedDate = formatter.parse("05/01/2016");

        Date testDate = (Date) casting.castValue(Date.class,"05/01/2016",options);

        assertEquals(expectedDate ,testDate);
    }

    @Test
    public void castInteger() throws Exception {

        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        Integer testVal = new Integer((String) casting.castValue(Integer.class,"10",options));

        assertEquals(new Integer(10) ,testVal);
    }

    @Test
    public void castDouble() throws Exception {
        // required
        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        Double testVal = Double.valueOf((String) casting.castValue(Integer.class,"81.56891",options));

        assertEquals(new Double(81.56891) ,testVal);
    }

    @Test(expected = NumberFormatException.class)
    public void castDoubleException() throws Exception {
        // required
        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        Integer testVal = new Integer((String) casting.castValue(Integer.class,"81.56891",options));
    }

    @Test
    public void castBoolean() throws Exception {
        // required
        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        Boolean testVal = Boolean.valueOf(String.valueOf(casting.castValue(Boolean.class,"True",options)));

        assertEquals(true ,testVal);
    }

    @Test(expected = ClassCastException.class)
    public void castBooleanException() throws Exception {
        // required
        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        Boolean testVal = Boolean.valueOf((Boolean) casting.castValue(Boolean.class,"True",options));

        assertEquals(true ,testVal);
    }

    @Test
    public void castFloat() throws Exception {
        // required
        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        Float testVal = Float.valueOf((String) casting.castValue(Integer.class,"81.56891",options));

        assertEquals(new Float(81.56891) ,testVal);
    }

    @Test
    public void castShort() throws Exception {
        // required
        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        Short testVal = Short.valueOf((String) casting.castValue(Integer.class,"32767",options));

        assertEquals(new Short("32767") ,testVal);
    }

    @Test(expected = NumberFormatException.class)
    public void castShortException() throws Exception {
        // required
        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        Short testVal = Short.valueOf((String) casting.castValue(Integer.class,"81.56891",options));

        assertEquals(new Short("81.56891") ,testVal);
    }

    @Test(expected = NumberFormatException.class)
    public void castShortWrongFormat() throws Exception {
        // required
        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        Short testVal = Short.valueOf((String) casting.castValue(Integer.class,"32768",options));

        assertEquals(new Short("32768") ,testVal);
    }

    @Test(expected = NumberFormatException.class)
    public void castLongWrongFormat() throws Exception {
        // required
        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        Long testVal =
                Long.valueOf((String) casting.castValue(Integer.class,"9223372036854775808",options));

        assertEquals(new Long("9223372036854775808") ,testVal);
    }

    @Test
    public void castLong() throws Exception {
        // required
        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy").build();

        Long testVal =
                Long.valueOf((String) casting.castValue(Integer.class,"9223372036854775807",options));

        assertEquals(new Long("9223372036854775807") ,testVal);
    }

}