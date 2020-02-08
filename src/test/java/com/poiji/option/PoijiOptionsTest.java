package com.poiji.option;

import com.poiji.save.CellCasting;
import java.time.format.DateTimeFormatter;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test for {@link PoijiOptions}.
 */
public final class PoijiOptionsTest {

    @Test
    public void getLocalDatePattern() {
        final PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().localDatePattern("custom").build();
        assertThat("custom", equalTo(options.getLocalDatePattern()));
    }

    @Test
    public void getLocalDateTimePattern() {
        final PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().localDateTimePattern("custom").build();
        assertThat("custom", equalTo(options.getLocalDateTimePattern()));
    }

    @Test
    public void dateTimeFormatter() {
        final DateTimeFormatter expected = DateTimeFormatter.BASIC_ISO_DATE;
        final PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().dateTimeFormatter(expected).build();
        assertThat(expected, equalTo(options.dateTimeFormatter()));
    }

    @Test
    public void getCellCasting() {
        final CellCasting expected = new CellCasting();
        final PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().withCellCasting(expected).build();
        assertThat(expected, equalTo(options.getCellCasting()));
    }

    @Test
    public void getCaseInsensitive() {
        final PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().caseInsensitive(true).build();
        assertThat(true, equalTo(options.getCaseInsensitive()));
    }
}
