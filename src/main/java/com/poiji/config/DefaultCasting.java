package com.poiji.config;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.poiji.option.PoijiOptions;
import com.poiji.parer.Parsers;

/**
 * Created by hakan on 22/01/2017.
 */
public final class DefaultCasting implements Casting {
    private final boolean errorLoggingEnabled;

    private final List<DefaultCastingError> errors = new ArrayList<>();

    public DefaultCasting() {
        this(false);
    }

    public DefaultCasting(boolean errorLoggingEnabled) {
        this.errorLoggingEnabled = errorLoggingEnabled;
    }

    private <T> T onError(String value, String sheetName, int row, int col, Exception exception, T defaultValue) {
        logError(value, defaultValue, sheetName, row, col, exception);
        return defaultValue;
    }

    private void logError(String value, Object defaultValue, String sheetName, int row, int col, Exception exception) {
        if (errorLoggingEnabled) {
            errors.add(new DefaultCastingError(value, defaultValue, sheetName, row, col, exception));
        }
    }

    private int primitiveIntegerValue(String value, String sheetName, int row, int col) {
        try {
            return Parsers.integers().parse(value).intValue();
        } catch (NumberFormatException nfe) {
            return onError(value, sheetName, row, col, nfe, 0);
        }
    }

    private Integer integerValue(String value, String sheetName, int row, int col, PoijiOptions options) {
        try {
            return Parsers.integers().parse(value).intValue();
        } catch (NumberFormatException nfe) {
            return onError(value, sheetName, row, col, nfe, options.preferNullOverDefault() ? null : 0);
        }
    }

    private long primitiveLongValue(String value, String sheetName, int row, int col) {
        try {
            return Parsers.longs().parse(value).longValue();
        } catch (NumberFormatException nfe) {
            return onError(value, sheetName, row, col, nfe, 0L);
        }
    }

    private Long longValue(String value, String sheetName, int row, int col, PoijiOptions options) {
        try {
            return Parsers.longs().parse(value).longValue();
        } catch (NumberFormatException nfe) {
            return onError(value, sheetName, row, col, nfe, options.preferNullOverDefault() ? null : 0L);
        }
    }

    private double primitiveDoubleValue(String value, String sheetName, int row, int col) {
        try {
            return Parsers.numbers().parse(value).doubleValue();
        } catch (NumberFormatException nfe) {
            return onError(value, sheetName, row, col, nfe, 0d);
        }
    }

    private Double doubleValue(String value, String sheetName, int row, int col, PoijiOptions options) {
        try {
            return Parsers.numbers().parse(value).doubleValue();
        } catch (NumberFormatException nfe) {
            return onError(value, sheetName, row, col, nfe, options.preferNullOverDefault() ? null : 0d);
        }
    }

    private float primitiveFloatValue(String value, String sheetName, int row, int col) {
        try {
            return Parsers.numbers().parse(value).floatValue();
        } catch (NumberFormatException nfe) {
            return onError(value, sheetName, row, col, nfe, 0f);
        }
    }

    private Float floatValue(String value, String sheetName, int row, int col, PoijiOptions options) {
        try {
            return Parsers.numbers().parse(value).floatValue();
        } catch (NumberFormatException nfe) {
            return onError(value, sheetName, row, col, nfe, options.preferNullOverDefault() ? null : 0f);
        }
    }

    private BigDecimal bigDecimalValue(String value, String sheetName, int row, int col, PoijiOptions options) {
        try {
            return Parsers.bigDecimals().parse(value);
        } catch (NumberFormatException | IllegalStateException e) {
            return onError(value, sheetName, row, col, e, options.preferNullOverDefault() ? null : BigDecimal.ZERO);
        }
    }

    private Date dateValue(String value, String sheetName, int row, int col, PoijiOptions options) {

        //ISSUE #57
        //if a date regex has been specified then it wont be null
        //so then make sure the string matches the pattern
        //if it doesn't, fall back to default
        //else continue to turn string into java date

        //the reason for this is sometime Java will manage to parse a string to a date object
        //without any exceptions but since the string was not an exact match you get a very strange date
        if (options.getDateRegex() != null && !value.matches(options.getDateRegex())) {
            return options.preferNullOverDefault() ? null : Calendar.getInstance().getTime();
        } else {
            try {
                final SimpleDateFormat sdf = new SimpleDateFormat(options.datePattern());
                sdf.setLenient(options.getDateLenient());
                return sdf.parse(value);
            } catch (ParseException e) {
                return onError(value, sheetName, row, col, e, options.preferNullOverDefault() ? null : Calendar.getInstance().getTime());
            }
        }
    }

    private LocalDate localDateValue(String value, String sheetName, int row, int col, PoijiOptions options) {

        //ISSUE #57
        //if a date regex has been specified then it wont be null
        //so then make sure the string matches the pattern
        //if it doesn't, fall back to default
        //else continue to turn string into java date

        //the reason for this is sometime java will manage to parse a string to a date object
        //without any exceptions but since the string was not an exact match you get a very strange date
        if (options.getDateRegex() != null && !value.matches(options.getDateRegex())) {
            return options.preferNullOverDefault() ? null : LocalDate.now();
        } else {
            try {
                return LocalDate.parse(value, options.dateTimeFormatter());
            } catch (DateTimeParseException e) {
                return onError(value, sheetName, row, col, e, options.preferNullOverDefault() ? null : LocalDate.now());
            }
        }
    }

    private Object enumValue(String value, String sheetName, int row, int col, Class type) {
        return Arrays.stream(type.getEnumConstants())
                .filter(o -> ((Enum) o).name().equals(value))
                .findFirst()
                .orElseGet(() -> {
                    IllegalArgumentException e = new IllegalArgumentException("No enumeration " + type.getSimpleName() + "." + value);
                    return onError(value, sheetName, row, col, e, null);
                });
    }

    public Object castValue(Class<?> fieldType, String value, PoijiOptions options) {
        return castValue(fieldType, value, -1, -1, options);
    }

    public Object castValue(Class<?> fieldType, String rawValue, int row, int col, PoijiOptions options) {

        String sheetName = options.getSheetName();

        String value = options.trimCellValue() ? rawValue.trim() : rawValue;

        Object o;

        if (fieldType.getName().equals("int")) {
            o = primitiveIntegerValue(value, sheetName, row, col);

        } else if (fieldType.getName().equals("java.lang.Integer")) {
            o = integerValue(value, sheetName, row, col, options);

        } else if (fieldType.getName().equals("java.math.BigDecimal")) {
            o = bigDecimalValue(value, sheetName, row, col, options);

        } else if (fieldType.getName().equals("long")) {
            o = primitiveLongValue(value, sheetName, row, col);

        } else if (fieldType.getName().equals("java.lang.Long")) {
            o = longValue(value, sheetName, row, col, options);

        } else if (fieldType.getName().equals("double")) {
            o = primitiveDoubleValue(value, sheetName, row, col);

        } else if (fieldType.getName().equals("java.lang.Double")) {
            o = doubleValue(value, sheetName, row, col, options);

        } else if (fieldType.getName().equals("float")) {
            o = primitiveFloatValue(value, sheetName, row, col);

        } else if (fieldType.getName().equals("java.lang.Float")) {
            o = floatValue(value, sheetName, row, col, options);

        } else if (fieldType.getName().equals("boolean") || fieldType.getName().equals("java.lang.Boolean")) {
            o = Boolean.valueOf(value);

        } else if (fieldType.getName().equals("java.util.Date")) {
            o = dateValue(value, sheetName, row, col, options);

        } else if (fieldType.getName().equals("java.time.LocalDate")) {
            o = localDateValue(value, sheetName, row, col, options);

        } else if (fieldType.isEnum()) {
            o = enumValue(value, sheetName, row, col, fieldType);

        } else if (value.isEmpty()) {
            o = options.preferNullOverDefault() ? null : value;

        } else {
            o = value;

        }
        return o;
    }

    public boolean isErrorLoggingEnabled() {
        return errorLoggingEnabled;
    }

    public List<DefaultCastingError> getErrors() {
        if (errorLoggingEnabled) {
            return Collections.unmodifiableList(errors);
        } else {
            throw new IllegalStateException("logging not enabled");
        }
    }

}
