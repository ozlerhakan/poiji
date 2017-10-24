package com.poiji.util;

import com.poiji.option.PoijiOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by hakan on 22/01/2017.
 */
public final class Casting {

    private Casting() {
    }

    static Integer integerValue(String value) {
        try {
            return new Integer(value);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    static Long longValue(String value) {
        try {
            return new Long(value);
        } catch (NumberFormatException nfe) {
            return 0L;
        }
    }

    static Short shortValue(String value) {
        try {
            return new Short(value);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    static Double doubleValue(String value) {
        try {
            return new Double(value);
        } catch (NumberFormatException nfe) {
            return 0d;
        }
    }

    static Float floatValue(String value) {
        try {
            return new Float(value);
        } catch (NumberFormatException nfe) {
            return 0f;
        }
    }

    static Date dateValue(String value, PoijiOptions options) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat(options.datePattern());
            return sdf.parse(value);
        } catch (ParseException e) {
            Calendar calendar = Calendar.getInstance();
            return calendar.getTime();
        }
    }

    public static Object castValue(Class<?> fieldType, String value, PoijiOptions options) {
        Object o;
        if (fieldType.getName().equals("int")) {
            o = Casting.integerValue(Objects.equals(value, "") ? "0" : value);

        } else if (fieldType.getName().equals("long")) {
            o = Casting.longValue(Objects.equals(value, "") ? "0" : value);

        } else if (fieldType.getName().equals("double")) {
            o = Casting.doubleValue(Objects.equals(value, "") ? "0" : value);

        } else if (fieldType.getName().equals("float")) {
            o = Casting.floatValue(Objects.equals(value, "") ? "0" : value);

        } else if (fieldType.getName().equals("boolean")) {
            o = Boolean.valueOf(value);
        } else if (fieldType.getName().equals("java.util.Date")) {

            o = Casting.dateValue(value, options);
        } else
            o = value;
        return o;
    }
}
