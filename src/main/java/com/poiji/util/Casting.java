package com.poiji.util;

import com.poiji.option.PoijiOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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

    private int primitiveIntegerValue(String value) {
        try {
            return new Integer(value);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    private Integer integerValue(String value, PoijiOptions options) {
        try {
            return new Integer(value);
        } catch (NumberFormatException nfe) {
            if (Boolean.TRUE.equals(options.preferNullOverDefault())) {
                return null;
            }
            return 0;
        }
    }

    private long primitiveLongValue(String value) {
        try {
            return new Long(value);
        } catch (NumberFormatException nfe) {
            return 0L;
        }
    }

    private Long longValue(String value, PoijiOptions options) {
        try {
            return new Long(value);
        } catch (NumberFormatException nfe) {
            if (Boolean.TRUE.equals(options.preferNullOverDefault())) {
                return null;
            }
            return 0L;
        }
    }

    private double primitiveDoubleValue(String value) {
        try {
            return new Double(value);
        } catch (NumberFormatException nfe) {
            return 0d;
        }
    }

    private Double doubleValue(String value, PoijiOptions options) {
        try {
            return new Double(value);
        } catch (NumberFormatException nfe) {
            if (Boolean.TRUE.equals(options.preferNullOverDefault())) {
                return null;
            }
            return 0d;
        }
    }

    private float primitiveFloatValue(String value) {
        try {
            return new Float(value);
        } catch (NumberFormatException nfe) {
            return 0f;
        }
    }

    private Float floatValue(String value, PoijiOptions options) {
        try {
            return new Float(value);
        } catch (NumberFormatException nfe) {
            if (Boolean.TRUE.equals(options.preferNullOverDefault())) {
                return null;
            }
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
        try {
            return LocalDate.parse(value, options.dateTimeFormatter());
        } catch (DateTimeParseException e) {
            return null;
        }
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
        if (fieldType.getName().equals("int")) {
            o = primitiveIntegerValue(value);

        } else if (fieldType.getName().equals("java.lang.Integer")) {
            o = integerValue(value, options);

        } else if (fieldType.getName().equals("long")) {
            o = primitiveLongValue(value);

        } else if (fieldType.getName().equals("java.lang.Long")) {
            o = longValue(value, options);

        } else if (fieldType.getName().equals("double")) {
            o = primitiveDoubleValue(value);

        } else if (fieldType.getName().equals("java.lang.Double")) {
            o = doubleValue(value, options);

        } else if (fieldType.getName().equals("float")) {
            o = primitiveFloatValue(value);

        } else if (fieldType.getName().equals("java.lang.Float")) {
            o = floatValue(value, options);

        } else if (fieldType.getName().equals("boolean") || fieldType.getName().equals("java.lang.Boolean")) {
            o = Boolean.valueOf(value);

        } else if (fieldType.getName().equals("java.util.Date")) {
            o = dateValue(value, options);

        } else if (fieldType.getName().equals("java.time.LocalDate")) {
            o = localDateValue(value, options);

        } else if (fieldType.isEnum()) {
            o = enumValue(value, fieldType);

        } else {
            if (value.length() == 0) {
                if (Boolean.TRUE.equals(options.preferNullOverDefault())) {
                    o = null;
                } else {
                    o = value;
                }
            } else {
                o = value;
            }
        }
        return o;
    }
}
