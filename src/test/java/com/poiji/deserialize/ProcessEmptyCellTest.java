package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.PersonWithGaps;
import com.poiji.deserialize.model.byid.PersonWithGapsRecord;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test for the processEmptyCell feature.
 * Verifies that empty cells are properly processed when the option is enabled,
 * including cells accessed via @ExcelCell, @ExcelCellName, and @ExcelCellsJoinedByName.
 */
@RunWith(Parameterized.class)
public class ProcessEmptyCellTest {

    private final String path;

    public ProcessEmptyCellTest(String path) {
        this.path = path;
    }

    @Parameterized.Parameters(name = "{index}: ({0}, {1}, fromStream={2})")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/persons_with_gaps.xlsx"},
                {"src/test/resources/persons_with_gaps.xlsx"},
                {"src/test/resources/persons_with_gaps.xls"},
                {"src/test/resources/persons_with_gaps.xls"},
        });
    }

    @Test
    public void shouldProcessEmptyCellsWhenOptionEnabled() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder
                .settings()
                .preferNullOverDefault(false) // default
                .processEmptyCell(true)
                .build();

        List<PersonWithGaps> persons = deserialize(options);

        assertThat(persons, notNullValue());
        assertThat(persons.size(), is(4));

        // Row 1: 1, John, (empty), Doe, john@example.com, 555-0100, (empty)
        PersonWithGaps person1 = persons.get(0);
        assertThat(person1.getId(), is("1"));
        assertThat(person1.getFirstName(), is("John"));
        assertThat(person1.getMiddleName(), is("")); // Empty cell should be processed as empty string
        assertThat(person1.getLastName(), is("Doe"));
        assertThat(person1.getEmail(), is("john@example.com"));
        assertThat(person1.getPhones(), notNullValue());
        assertThat(person1.getPhones().get("Phone1").size(), is(1));
        assertThat(person1.getPhones().get("Phone1").iterator().next(), is("555-0100"));
        // Phone2 should have empty string when processEmptyCell is true
        assertThat(person1.getPhones().containsKey("Phone2"), is(true));
        Collection<String> phone2Values = person1.getPhones().get("Phone2");
        assertThat(phone2Values.size(), is(1));
        assertThat(phone2Values.iterator().next(), is(""));

        // Row 2: 2, Jane, Marie, Smith, jane@example.com, 555-0200, 555-0201
        PersonWithGaps person2 = persons.get(1);
        assertThat(person2.getId(), is("2"));
        assertThat(person2.getFirstName(), is("Jane"));
        assertThat(person2.getMiddleName(), is("Marie"));
        assertThat(person2.getLastName(), is("Smith"));
        assertThat(person2.getEmail(), is("jane@example.com"));

        // Row 3: 3, Bob, (empty), (empty), bob@example.com, (empty), 555-0301
        PersonWithGaps person3 = persons.get(2);
        assertThat(person3.getId(), is("3"));
        assertThat(person3.getFirstName(), is("Bob"));
        assertThat(person3.getMiddleName(), is("")); // Empty cell processed
        assertThat(person3.getLastName(), is("")); // Empty cell processed
        assertThat(person3.getEmail(), is("bob@example.com"));
        // Phone1 should be empty string
        assertThat(person3.getPhones().containsKey("Phone1"), is(true));
        assertThat(person3.getPhones().get("Phone1").iterator().next(), is(""));

        // Row 4: (empty), Lee, Ann, Johnson, (empty), 555-0400, (empty)
        PersonWithGaps person4 = persons.get(3);
        assertThat(person4.getId(), is("")); // Empty cell processed
        assertThat(person4.getFirstName(), is("Lee"));
        assertThat(person4.getMiddleName(), is("Ann"));
        assertThat(person4.getLastName(), is("Johnson"));
        assertThat(person4.getEmail(), is("")); // Empty cell processed
    }

    @Test
    public void shouldNotProcessEmptyCellsWhenOptionDisabled() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder
                .settings()
                .preferNullOverDefault(false) // default
                .processEmptyCell(false)
                .build();

        List<PersonWithGaps> persons = deserialize(options);

        assertThat(persons, notNullValue());
        assertThat(persons.size(), is(4));

        // When processEmptyCell is false, empty cells should be null
        PersonWithGaps person1 = persons.get(0);
        assertThat(person1.getId(), is("1"));
        assertThat(person1.getFirstName(), is("John"));
        assertThat(person1.getMiddleName(), is(nullValue())); // Empty cell should be null
        assertThat(person1.getLastName(), is("Doe"));
        assertThat(person1.getEmail(), is("john@example.com"));
        assertThat(person1.getPhones(), notNullValue());
        assertThat(person1.getPhones().get("Phone1").size(), is(1));
        // Phone2 should not be present or should be null/empty when processEmptyCell is false
        assertThat(person1.getPhones().containsKey("Phone2"), is(false));

        PersonWithGaps person3 = persons.get(2);
        assertThat(person3.getId(), is("3"));
        assertThat(person3.getFirstName(), is("Bob"));
        assertThat(person3.getMiddleName(), is(nullValue())); // Empty cell should be null
        assertThat(person3.getLastName(), is(nullValue())); // Empty cell should be null
        assertThat(person3.getEmail(), is("bob@example.com"));
        // Phone1 empty cell should not be in the map
        assertThat(person3.getPhones().containsKey("Phone1"), is(false));

        PersonWithGaps person4 = persons.get(3);
        assertThat(person4.getId(), is(nullValue())); // Empty cell should be null
        assertThat(person4.getEmail(), is(nullValue())); // Empty cell should be null
    }

    @Test
    public void shouldNotProcessEmptyCellsByDefault() {
        // Default options should not process empty cells
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().build();

        List<PersonWithGaps> persons = deserialize(options);

        assertThat(persons, notNullValue());
        
        // Verify that empty cells are null by default
        PersonWithGaps person1 = persons.get(0);
        assertThat(person1.getMiddleName(), is(nullValue()));
    }

    @Test
    public void shouldProcessEmptyCellsAsNullWhenPreferNullOverDefaultIsEnabled() {
        // When both processEmptyCell and preferNullOverDefault are enabled,
        // empty cells should be processed and set to null instead of empty string
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder
                .settings()
                .processEmptyCell(true)
                .preferNullOverDefault(true)
                .build();

        List<PersonWithGaps> persons = deserialize(options);

        assertThat(persons, notNullValue());
        assertThat(persons.size(), is(4));

        // Row 1: 1, John, (empty), Doe, john@example.com, 555-0100, (empty)
        PersonWithGaps person1 = persons.get(0);
        assertThat(person1.getId(), is("1"));
        assertThat(person1.getFirstName(), is("John"));
        assertThat(person1.getMiddleName(), is(nullValue())); // Empty cell should be null with preferNullOverDefault
        assertThat(person1.getLastName(), is("Doe"));
        assertThat(person1.getEmail(), is("john@example.com"));
        assertThat(person1.getPhones(), notNullValue());
        assertThat(person1.getPhones().get("Phone1").size(), is(1));
        assertThat(person1.getPhones().get("Phone1").iterator().next(), is("555-0100"));
        // Phone2 should have null value when both options are enabled
        assertThat(person1.getPhones().containsKey("Phone2"), is(true));
        Collection<String> phone2Values = person1.getPhones().get("Phone2");
        assertThat(phone2Values.size(), is(1));
        assertThat(phone2Values.iterator().next(), is(nullValue())); // null instead of empty string

        // Row 3: 3, Bob, (empty), (empty), bob@example.com, (empty), 555-0301
        PersonWithGaps person3 = persons.get(2);
        assertThat(person3.getId(), is("3"));
        assertThat(person3.getFirstName(), is("Bob"));
        assertThat(person3.getMiddleName(), is(nullValue())); // null with preferNullOverDefault
        assertThat(person3.getLastName(), is(nullValue())); // null with preferNullOverDefault
        assertThat(person3.getEmail(), is("bob@example.com"));
        // Phone1 should be null
        assertThat(person3.getPhones().containsKey("Phone1"), is(true));
        assertThat(person3.getPhones().get("Phone1").iterator().next(), is(nullValue()));
    }

    private List<PersonWithGaps> deserialize(PoijiOptions options) {
        return Poiji.fromExcel(new File(path), PersonWithGaps.class, options);
    }

}
