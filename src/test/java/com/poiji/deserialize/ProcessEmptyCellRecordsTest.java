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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test for the processEmptyCell feature.
 * Verifies that empty cells are properly processed when the option is enabled,
 * including cells accessed via @ExcelCell, @ExcelCellName, and @ExcelCellsJoinedByName.
 */
@RunWith(Parameterized.class)
public class ProcessEmptyCellRecordsTest {

    private final String path;

    public ProcessEmptyCellRecordsTest(String path) {
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
    public void shouldProcessEmptyCellsWhenOptionEnabledWithRecords() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder
                .settings()
                .preferNullOverDefault(false) // default
                .processEmptyCell(true)
                .build();

        List<PersonWithGapsRecord> persons = deserialize(options);

        assertThat(persons, notNullValue());
        assertThat(persons.size(), is(4));

        // Row 1: 1, John, (empty), Doe, john@example.com, 555-0100, (empty)
        PersonWithGapsRecord person1 = persons.get(0);
        assertThat(person1.id(), is("1"));
        assertThat(person1.firstName(), is("John"));
        assertThat(person1.middleName(), is("")); // Empty cell should be processed as empty string
        assertThat(person1.lastName(), is("Doe"));
        assertThat(person1.email(), is("john@example.com"));
        assertThat(person1.phones(), notNullValue());
        assertThat(person1.phones().get("Phone1").size(), is(1));
        assertThat(person1.phones().get("Phone1").iterator().next(), is("555-0100"));
        // Phone2 should have empty string when processEmptyCell is true
        assertThat(person1.phones().containsKey("Phone2"), is(true));
        Collection<String> phone2Values = person1.phones().get("Phone2");
        assertThat(phone2Values.size(), is(1));
        assertThat(phone2Values.iterator().next(), is(""));

        // Row 2: 2, Jane, Marie, Smith, jane@example.com, 555-0200, 555-0201
        PersonWithGapsRecord person2 = persons.get(1);
        assertThat(person2.id(), is("2"));
        assertThat(person2.firstName(), is("Jane"));
        assertThat(person2.middleName(), is("Marie"));
        assertThat(person2.lastName(), is("Smith"));
        assertThat(person2.email(), is("jane@example.com"));

        // Row 3: 3, Bob, (empty), (empty), bob@example.com, (empty), 555-0301
        PersonWithGapsRecord person3 = persons.get(2);
        assertThat(person3.id(), is("3"));
        assertThat(person3.firstName(), is("Bob"));
        assertThat(person3.middleName(), is("")); // Empty cell processed
        assertThat(person3.lastName(), is("")); // Empty cell processed
        assertThat(person3.email(), is("bob@example.com"));
        // Phone1 should be empty string
        assertThat(person3.phones().containsKey("Phone1"), is(true));
        assertThat(person3.phones().get("Phone1").iterator().next(), is(""));

        // Row 4: (empty), Lee, Ann, Johnson, (empty), 555-0400, (empty)
        PersonWithGapsRecord person4 = persons.get(3);
        assertThat(person4.id(), is("")); // Empty cell processed
        assertThat(person4.firstName(), is("Lee"));
        assertThat(person4.middleName(), is("Ann"));
        assertThat(person4.lastName(), is("Johnson"));
        assertThat(person4.email(), is("")); // Empty cell processed
    }

    @Test
    public void shouldNotProcessEmptyCellsWhenOptionDisabledWithRecords() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder
                .settings()
                .preferNullOverDefault(false) // default
                .processEmptyCell(false)
                .build();

        List<PersonWithGapsRecord> persons = deserialize(options);

        assertThat(persons, notNullValue());
        assertThat(persons.size(), is(4));

        // When processEmptyCell is false, empty cells should be null
        PersonWithGapsRecord person1 = persons.get(0);
        assertThat(person1.id(), is("1"));
        assertThat(person1.firstName(), is("John"));
        assertThat(person1.middleName(), is(nullValue())); // Empty cell should be null
        assertThat(person1.lastName(), is("Doe"));
        assertThat(person1.email(), is("john@example.com"));
        assertThat(person1.phones(), notNullValue());
        assertThat(person1.phones().get("Phone1").size(), is(1));
        // Phone2 should not be present or should be null/empty when processEmptyCell is false
        assertThat(person1.phones().containsKey("Phone2"), is(false));

        PersonWithGapsRecord person3 = persons.get(2);
        assertThat(person3.id(), is("3"));
        assertThat(person3.firstName(), is("Bob"));
        assertThat(person3.middleName(), is(nullValue())); // Empty cell should be null
        assertThat(person3.lastName(), is(nullValue())); // Empty cell should be null
        assertThat(person3.email(), is("bob@example.com"));
        // Phone1 empty cell should not be in the map
        assertThat(person3.phones().containsKey("Phone1"), is(false));

        PersonWithGapsRecord person4 = persons.get(3);
        assertThat(person4.id(), is(nullValue())); // Empty cell should be null
        assertThat(person4.email(), is(nullValue())); // Empty cell should be null
    }

    private List<PersonWithGapsRecord> deserialize(PoijiOptions options) {
        return Poiji.fromExcel(new File(path), PersonWithGapsRecord.class, options);
    }
}
