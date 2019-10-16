package com.poiji.parser;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class NumberParser implements Parser<Number> {

    private final NumberFormat numberFormat;

    NumberParser(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    public Number parse(String value) {
        return parseNumber(value, numberFormat);
    }

    private static Number parseNumber(String value, NumberFormat instance) {
        if (value == null || value.trim().isEmpty()) {
            throw new NumberFormatException(value);
        }
        ParsePosition pos = new ParsePosition(0);
        Number result = instance.parse(value, pos);
        if (isParsingError(pos, value)) {
            throw new NumberFormatException(value);
        }
        return result;
    }

    private static boolean isParsingError(ParsePosition pos, String value) {
        return pos.getErrorIndex() != -1 || pos.getIndex() != value.length();
    }

}
