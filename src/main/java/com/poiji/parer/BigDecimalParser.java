package com.poiji.parer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BigDecimalParser implements Parser<BigDecimal> {

    private final NumberParser delegate;

    BigDecimalParser() {
        this.delegate = new NumberParser(getDecimalFormatInstance());
    }

    private DecimalFormat getDecimalFormatInstance() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        if (numberFormat instanceof DecimalFormat) {
            DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
            decimalFormat.setParseBigDecimal(true);
            return decimalFormat;
        } else {
            throw new IllegalStateException(numberFormat.getClass().getName());
        }
    }

    @Override
    public BigDecimal parse(String value) throws NumberFormatException {
        return (BigDecimal) delegate.parse(value);
    }
}
