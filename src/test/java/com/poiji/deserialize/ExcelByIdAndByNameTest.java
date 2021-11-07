package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Person;
import com.poiji.deserialize.model.byname.PersonByName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.poiji.util.Data.unmarshallingPersons;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by ar on 9/03/2018.
 */
@RunWith(Parameterized.class)
public class ExcelByIdAndByNameTest {

    private String path;
    private List<Person> expectedPersonList;
    private Class<?> clazz;

    public ExcelByIdAndByNameTest(String path, List<Person> expectedPersonList, Class<?> clazz) {
        this.path = path;
        this.expectedPersonList = expectedPersonList;
        this.clazz = clazz;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/person.xlsx", unmarshallingPersons(), Person.class},
                {"src/test/resources/person.xls", unmarshallingPersons(), Person.class},
                {"src/test/resources/person.xlsx", unmarshallingPersons(), PersonByName.class},
                {"src/test/resources/person.xls", unmarshallingPersons(), PersonByName.class}
        });
    }

    @Test
    public void testExcel() {
        try {
            if (clazz == Person.class) {
                List<Person> actualCars = Poiji.fromExcel(new File(path), Person.class);
                assertEquals(expectedPersonList.get(0).toString(), actualCars.get(0).toString());
                assertEquals(expectedPersonList.get(1).toString(), actualCars.get(1).toString());
                assertEquals(expectedPersonList.get(2).toString(), actualCars.get(2).toString());
                assertEquals(expectedPersonList.get(3).toString(), actualCars.get(3).toString());
                assertEquals(expectedPersonList.get(4).toString(), actualCars.get(4).toString());
            } else if (clazz == PersonByName.class) {
                List<PersonByName> actualCars = Poiji.fromExcel(new File(path), PersonByName.class);
                assertEquals(expectedPersonList.get(0).toString(), actualCars.get(0).toString());
                assertEquals(expectedPersonList.get(1).toString(), actualCars.get(1).toString());
                assertEquals(expectedPersonList.get(2).toString(), actualCars.get(2).toString());
                assertEquals(expectedPersonList.get(3).toString(), actualCars.get(3).toString());
                assertEquals(expectedPersonList.get(4).toString(), actualCars.get(4).toString());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
