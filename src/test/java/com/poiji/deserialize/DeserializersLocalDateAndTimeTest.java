package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.config.DefaultCasting;
import com.poiji.config.DefaultCastingError;
import com.poiji.deserialize.model.Event;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import static com.poiji.util.Data.unmarshallingEvents;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class DeserializersLocalDateAndTimeTest {

    private final String path;

    public DeserializersLocalDateAndTimeTest(String path) {
        this.path = path;
    }

    @Parameterized.Parameters(name = "{index}: ({0})")
    public static Iterable<Object[]> queries() throws Exception {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/events.xlsx"},
                {"src/test/resources/events.xls"}});
    }

    @Test
    public void successfulMapping() {
        final var errorLoggingCasting = new DefaultCasting(true);
        final var options = PoijiOptionsBuilder.settings()
                .dateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
                .withCasting(errorLoggingCasting)
                .build();
        final var actualEvents = Poiji.fromExcel(new File(path), Event.class, options);
        final var expectedEvents = unmarshallingEvents();
        final var castingErrors = errorLoggingCasting.getErrors();


        assertNotNull(actualEvents);
        assertEquals("Expected to read 3 events from the file", 3, actualEvents.size());
        assertEquals(expectedEvents, actualEvents);
        assertTrue("No casting errors expected", castingErrors.isEmpty());
    }

    @Test
    public void successfulDateRegex() {
        final var errorLoggingCasting = new DefaultCasting(true);
        final var options = PoijiOptionsBuilder.settings()
                .dateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
                .dateRegex("\\d{4}-\\d{2}-\\d{2}")
                .withCasting(errorLoggingCasting)
                .build();
        final var actualEvents = Poiji.fromExcel(new File(path), Event.class, options);
        final var expectedEvents = unmarshallingEvents();
        final var castingErrors = errorLoggingCasting.getErrors();


        assertNotNull(actualEvents);
        assertEquals("Expected to read 3 events from the file", 3, actualEvents.size());
        assertEquals(expectedEvents, actualEvents);
        assertTrue("No casting errors expected", castingErrors.isEmpty());
    }

    @Test
    public void failureDateRegex() {
        final var errorLoggingCasting = new DefaultCasting(true);
        final var options = PoijiOptionsBuilder.settings()
                .dateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
                .dateRegex("\\d{2}-\\d{2}-\\d{4}")
                .withCasting(errorLoggingCasting)
                .build();
        final var actualEvents = Poiji.fromExcel(new File(path), Event.class, options);
        final var unexpectedEvents = unmarshallingEvents();
        final var castingErrors = errorLoggingCasting.getErrors();


        assertNotNull(actualEvents);
        assertEquals("Expected to read 3 events from the file", 3, actualEvents.size());

        assertNotEquals(unexpectedEvents.get(0), actualEvents.get(0));
        assertNotEquals(unexpectedEvents.get(1), actualEvents.get(1));
        assertNotEquals(unexpectedEvents.get(2), actualEvents.get(2));
        assertNotEquals(unexpectedEvents, actualEvents);
        assertTrue("No casting errors expected", castingErrors.isEmpty());
    }

    @Test
    public void successfulTimeRegex() {
        final var errorLoggingCasting = new DefaultCasting(true);
        final var options = PoijiOptionsBuilder.settings()
                .dateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
                .timeRegex("\\d{2}:\\d{2}:\\d{2}")
                .withCasting(errorLoggingCasting)
                .build();
        final var actualEvents = Poiji.fromExcel(new File(path), Event.class, options);
        final var expectedEvents = unmarshallingEvents();
        final var castingErrors = errorLoggingCasting.getErrors();


        assertNotNull(actualEvents);
        assertEquals("Expected to read 3 events from the file", 3, actualEvents.size());
        assertEquals(expectedEvents, actualEvents);
        assertTrue("No casting errors expected", castingErrors.isEmpty());
    }

    @Test
    public void failureTimeRegex() {
        final var errorLoggingCasting = new DefaultCasting(true);
        final var options = PoijiOptionsBuilder.settings()
                .dateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
                .timeRegex("\\d{2}:\\d{2}")
                .withCasting(errorLoggingCasting)
                .build();
        final var actualEvents = Poiji.fromExcel(new File(path), Event.class, options);
        final var unexpectedEvents = unmarshallingEvents();
        final var castingErrors = errorLoggingCasting.getErrors();


        assertNotNull(actualEvents);
        assertEquals("Expected to read 3 events from the file", 3, actualEvents.size());

        assertNotEquals(unexpectedEvents.get(0), actualEvents.get(0));
        assertNotEquals(unexpectedEvents.get(1), actualEvents.get(1));
        assertNotEquals(unexpectedEvents.get(2), actualEvents.get(2));
        assertNotEquals(unexpectedEvents, actualEvents);
        assertTrue("No casting errors expected", castingErrors.isEmpty());
    }

    @Test
    public void successfulDateFormatter() {
        final var errorLoggingCasting = new DefaultCasting(true);
        final var options = PoijiOptionsBuilder.settings()
                .dateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .withCasting(errorLoggingCasting)
                .build();
        final var actualEvents = Poiji.fromExcel(new File(path), Event.class, options);
        final var expectedEvents = unmarshallingEvents();
        final var castingErrors = errorLoggingCasting.getErrors();


        assertNotNull(actualEvents);
        assertEquals("Expected to read 3 events from the file", 3, actualEvents.size());
        assertEquals(expectedEvents, actualEvents);
        assertTrue("No casting errors expected", castingErrors.isEmpty());
    }

    @Test
    public void failureDateFormatter() {
        final var errorLoggingCasting = new DefaultCasting(true);
        final var options = PoijiOptionsBuilder.settings()
                .dateFormatter(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                .withCasting(errorLoggingCasting)
                .build();
        final var actualEvents = Poiji.fromExcel(new File(path), Event.class, options);
        final var unexpectedEvents = unmarshallingEvents();
        final var castingErrors = errorLoggingCasting.getErrors();


        assertNotNull(actualEvents);
        assertEquals("Expected to read 3 events from the file", 3, actualEvents.size());

        assertNotEquals(unexpectedEvents.get(0), actualEvents.get(0));
        assertNotEquals(unexpectedEvents.get(1), actualEvents.get(1));
        assertNotEquals(unexpectedEvents.get(2), actualEvents.get(2));
        assertNotEquals(unexpectedEvents, actualEvents);

        assertEquals("Expect 3 casting errors", 3, castingErrors.size());
        for (DefaultCastingError error : castingErrors) {
            assertThat("DateTimeParseException is expected due to wrong formatter provided",
                    error.getException(),
                    instanceOf(DateTimeParseException.class));
        }
    }

    @Test
    public void successfulTimeFormatter() {
        final var errorLoggingCasting = new DefaultCasting(true);
        final var options = PoijiOptionsBuilder.settings()
                .dateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
                .timeFormatter(DateTimeFormatter.ofPattern("HH:mm:ss"))
                .withCasting(errorLoggingCasting)
                .build();
        final var actualEvents = Poiji.fromExcel(new File(path), Event.class, options);
        final var expectedEvents = unmarshallingEvents();
        final var castingErrors = errorLoggingCasting.getErrors();


        assertNotNull(actualEvents);
        assertEquals("Expected to read 3 events from the file", 3, actualEvents.size());
        assertEquals(expectedEvents, actualEvents);
        assertTrue("No casting errors expected", castingErrors.isEmpty());
    }

    @Test
    public void failureTimeFormatter() {
        final var errorLoggingCasting = new DefaultCasting(true);
        final var options = PoijiOptionsBuilder.settings()
                .dateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
                .timeFormatter(DateTimeFormatter.ofPattern("h:mm a"))
                .withCasting(errorLoggingCasting)
                .build();
        final var actualEvents = Poiji.fromExcel(new File(path), Event.class, options);
        final var unexpectedEvents = unmarshallingEvents();
        final var castingErrors = errorLoggingCasting.getErrors();

        assertNotNull(actualEvents);
        assertEquals("Expected to read 3 events from the file", 3, actualEvents.size());

        assertNotEquals(unexpectedEvents.get(0), actualEvents.get(0));
        assertNotEquals(unexpectedEvents.get(1), actualEvents.get(1));
        assertNotEquals(unexpectedEvents.get(2), actualEvents.get(2));
        assertNotEquals(unexpectedEvents, actualEvents);

        assertEquals("Expect 3 casting errors", 3, castingErrors.size());
        for (DefaultCastingError error : castingErrors) {
            assertThat("DateTimeParseException is expected due to wrong formatter provided",
                    error.getException(),
                    instanceOf(DateTimeParseException.class));
        }
    }

}
