package com.poiji.parser;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BigDecimalParser implements Parser<BigDecimal> {

    private final NumberParser delegate;

    BigDecimalParser(NumberFormat numberFormat) {
        this.delegate = new NumberParser(getDecimalFormatInstance(numberFormat));
    }

    private DecimalFormat getDecimalFormatInstance(NumberFormat numberFormat) {
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.setParseBigDecimal(true);
        return decimalFormat;
    }

    @Override
    public BigDecimal parse(String value) {
        return (BigDecimal) delegate.parse(value);
    }
}
