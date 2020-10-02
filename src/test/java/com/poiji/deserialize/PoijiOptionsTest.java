package com.poiji.deserialize;

import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.junit.Test;

import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by hakan on 19.07.2020
 */
@SuppressWarnings("unused")
public class PoijiOptionsTest {

    @Test(expected = PoijiException.class)
    public void shouldThrowExceptionWhenLimitLessThanZero() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings(-1).build();
    }

    @Test(expected = PoijiException.class)
    public void shouldThrowExceptionWhenSheetIndexLessThanZero() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(-1).build();
    }

    @Test(expected = PoijiException.class)
    public void shouldThrowExceptionWhenSkipLessThanZero() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().skip(-1).build();
    }

    @Test(expected = PoijiException.class)
    public void shouldThrowExceptionWhenHeaderLessThanZero() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().headerStart(-2).build();
    }

    @Test
    public void shouldPrintDateTimeFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().dateFormatter(formatter).build();
        assertThat(options.dateFormatter().toString(), equalTo(formatter.toString()));
    }
}
