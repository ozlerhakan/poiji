package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Person;
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

/**
 * Created by ar on 9/03/2018.
 */
@RunWith(Parameterized.class)
public class RowNumberByNameTest {

    private String path;
    private List<Person> expectedPersonList;
    private Class<?> expectedException;

    public RowNumberByNameTest(String path, List<Person> expectedPersonList, Class<?> expectedException) {
        this.path = path;
        this.expectedPersonList = expectedPersonList;
        this.expectedException = expectedException;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/person.xlsx", unmarshallingPersons(), null},
                {"src/test/resources/person.xls", unmarshallingPersons(), null}
        });
    }

    @Test
    public void testRowNumberForXLSXFormatFile() {
        try {
            List<Person> actualCars = Poiji.fromExcel(new File(path), Person.class);
            assertEquals(expectedPersonList.get(0).getRow(), actualCars.get(0).getRow());
            assertEquals(expectedPersonList.get(1).getRow(), actualCars.get(1).getRow());
            assertEquals(expectedPersonList.get(2).getRow(), actualCars.get(2).getRow());
            assertEquals(expectedPersonList.get(3).getRow(), actualCars.get(3).getRow());
            assertEquals(expectedPersonList.get(4).getRow(), actualCars.get(4).getRow());
        } catch (Exception e) {
            if (expectedException == null) {
                fail(e.getMessage());
            } else {
                assertThat(e, instanceOf(expectedException));
            }
        }
    }

}
