package com.poiji.deserialize;

import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.apache.poi.util.LocaleUtil;
import org.junit.Test;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

    @Test
    public void shouldUseUsLocaleWhenNotSpecified() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().build();

        assertThat(options.getLocale(), equalTo(Locale.US));
        assertThat(LocaleUtil.getUserLocale(), equalTo(Locale.US));
    }

    @Test
    public void shouldUseProvidedLocaleWhenSpecified() {
        Locale userLocale = Locale.GERMAN;
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().setLocale(userLocale).build();

        assertThat(options.getLocale(), equalTo(userLocale));
        assertThat(LocaleUtil.getUserLocale(), equalTo(userLocale));
    }
}
