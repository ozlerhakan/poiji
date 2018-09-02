package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Person;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import static com.poiji.util.Data.unmarshallingPersons;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class DeserializersHiddenSheetLargeTest {

    private final String path;
    private final List<Person> expectedPersonList;
    private final Class<?> expectedException;

    public DeserializersHiddenSheetLargeTest(String path, List<Person> expectedPersonList, Class<?> expectedException) {
        this.path = path;
        this.expectedPersonList = expectedPersonList;
        this.expectedException = expectedException;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
            {"src/test/resources/hidden_very_large.xlsx", unmarshallingPersons(), null},
            {"src/test/resources/hidden_very_large.xls", unmarshallingPersons(), null}
        });
    }

    @Test
    public void testIgnoreHiddenSheets() {
        try {
            PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).build();

            List<Person> people = Poiji.fromExcel(new File(path), Person.class, poijiOptions);
            assertEquals(expectedPersonList.get(0).getRow(), people.get(0).getRow());
            assertEquals(expectedPersonList.get(1).getRow(), people.get(1).getRow());
            assertEquals(expectedPersonList.get(2).getRow(), people.get(2).getRow());
            assertEquals(expectedPersonList.get(3).getRow(), people.get(3).getRow());
            assertEquals(expectedPersonList.get(4).getRow(), people.get(4).getRow());
        } catch (Exception e) {
            if (expectedException == null) {
                fail(e.getMessage());
            } else {
                assertThat(e, instanceOf(expectedException));
            }
        }
    }

    @Test
    public void testProcessHiddenSheets() {
        PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings().ignoreHiddenSheets(false).build();
        List<Person> people = Poiji.fromExcel(new File(path), Person.class, poijiOptions);
        assertEquals(people.size(), 0);
    }
}
