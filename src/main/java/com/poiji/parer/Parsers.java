package com.poiji.parer;

import java.text.NumberFormat;

/**
 * @see <a href="https://www.ibm.com/developerworks/library/j-numberformat/index.html">Resolving NumberFormat's parsing issues</a>
 */
public class Parsers {

    private Parsers() {
        super();
    }

    public static NumberParser longs() {
        return integers();
    }

    public static NumberParser integers() {
        NumberFormat format = NumberFormat.getInstance();
        format.setParseIntegerOnly(true);
        return new NumberParser(format);
    }

    public static BigDecimalParser bigDecimals() {
        return new BigDecimalParser();
    }

    public static NumberParser numbers() {
        return new NumberParser(NumberFormat.getInstance());
    }

}
