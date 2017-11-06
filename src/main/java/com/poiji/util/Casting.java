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

    private static final Casting instance = new Casting();

    public static Casting getInstance() {
        return instance;
    }

    private Casting() {
    }

    private Integer integerValue(String value) {
        try {
            return new Integer(value);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    private Long longValue(String value) {
        try {
            return new Long(value);
        } catch (NumberFormatException nfe) {
            return 0L;
        }
    }

    private Short shortValue(String value) {
        try {
            return new Short(value);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    private Double doubleValue(String value) {
        try {
            return new Double(value);
        } catch (NumberFormatException nfe) {
            return 0d;
        }
    }

    private Float floatValue(String value) {
        try {
            return new Float(value);
        } catch (NumberFormatException nfe) {
            return 0f;
        }
    }

    private Date dateValue(String value, PoijiOptions options) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat(options.datePattern());
            return sdf.parse(value);
        } catch (ParseException e) {
            Calendar calendar = Calendar.getInstance();
            return calendar.getTime();
        }
    }

    public Object castValue(Class<?> fieldType, String value, PoijiOptions options) {
        Object o;
        if (fieldType.getName().equals("int")) {
            o = integerValue(Objects.equals(value, "") ? "0" : value);

        } else if (fieldType.getName().equals("long")) {
            o = longValue(Objects.equals(value, "") ? "0" : value);

        } else if (fieldType.getName().equals("double")) {
            o = doubleValue(Objects.equals(value, "") ? "0" : value);

        } else if (fieldType.getName().equals("float")) {
            o = floatValue(Objects.equals(value, "") ? "0" : value);

        } else if (fieldType.getName().equals("boolean")) {
            o = Boolean.valueOf(value);
        } else if (fieldType.getName().equals("java.util.Date")) {

            o = dateValue(value, options);
        } else
            o = value;
        return o;
    }
}
