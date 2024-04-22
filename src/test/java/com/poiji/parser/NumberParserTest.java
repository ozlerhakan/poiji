package com.poiji.parser;

import org.junit.Test;

import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;

import static org.junit.Assert.assertEquals;

import java.text.NumberFormat;

public class NumberParserTest {
    @Test
    public void parseNumber() {
        PoijiOptions options = PoijiOptionsBuilder.settings().build();
        NumberParser numParser = new NumberParser(NumberFormat.getInstance(options.getLocale()));
        Number expectedNumber = numParser.parse("1").doubleValue();
        assertEquals(expectedNumber.doubleValue(), 1.0, 0);
    }

    @Test(expected = NumberFormatException.class)
    public void parseNullNumber() {
        PoijiOptions options = PoijiOptionsBuilder.settings().build();
        NumberParser numParser = new NumberParser(NumberFormat.getInstance(options.getLocale()));
        numParser.parse(null).doubleValue();
    }
}
