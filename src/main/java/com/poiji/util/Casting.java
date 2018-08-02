package com.poiji.util;

import com.poiji.option.PoijiOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
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
            if (Boolean.TRUE.equals(options.preferNullOverDefault())) {
                return null;
            } else {
                Calendar calendar = Calendar.getInstance();
                return calendar.getTime();
            }
        }
    }

    private LocalDate localDateValue(String value, PoijiOptions options) {
        return LocalDate.parse(value, options.dateTimeFormatter());
    }

    private Object enumValue(String value, Class type) {
        return Arrays
                .stream(type.getEnumConstants())
                .filter(o -> ((Enum) o).name().equals(value))
                .findFirst()
                .orElse(null);
    }

    public Object castValue(Class<?> fieldType, String value, PoijiOptions options) {
        Object o;
        if (fieldType.getName().equals("int") || fieldType.getName().equals("java.lang.Integer")) {
            o = integerValue(Objects.equals(value, "") ? "0" : value);

        } else if (fieldType.getName().equals("long") || fieldType.getName().equals("java.lang.Long")) {
            o = longValue(Objects.equals(value, "") ? "0" : value);

        } else if (fieldType.getName().equals("double") || fieldType.getName().equals("java.lang.Double")) {
            o = doubleValue(Objects.equals(value, "") ? "0" : value);

        } else if (fieldType.getName().equals("float") || fieldType.getName().equals("java.lang.Float")) {
            o = floatValue(Objects.equals(value, "") ? "0" : value);

        } else if (fieldType.getName().equals("boolean") || fieldType.getName().equals("java.lang.Boolean")) {
            o = Boolean.valueOf(value);

        } else if (fieldType.getName().equals("java.util.Date")) {
            o = dateValue(value, options);

        } else if (fieldType.getName().equals("java.time.LocalDate")) {
            o = localDateValue(value, options);

        } else if (fieldType.isEnum()) {
            o = enumValue(value, fieldType);

        } else
            o = value;
        return o;
    }
}
