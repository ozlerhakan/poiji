package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by ar on 9/03/2018.
 */
@RunWith(Parameterized.class)
public class RowNumberTest {

    private String path;
    private List<Person> expectedPersonList;
    private Class<?> expectedException;

    public RowNumberTest(String path, List<Person> expectedPersonList, Class<?> expectedException) {
        this.path = path;
        this.expectedPersonList = expectedPersonList;
        this.expectedException = expectedException;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() throws Exception {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/person.xlsx", unmarshalling(), null},
                {"src/test/resources/person.xls", unmarshalling(), null}
        });
    }

    @Test
    public void testRowNumberForXLSXFormatFile() {
        List<Person> actualCars = Poiji.fromExcel(new File(path), Person.class);
        assertEquals(expectedPersonList.get(0).getRow(), actualCars.get(0).getRow());
        assertEquals(expectedPersonList.get(1).getRow(), actualCars.get(1).getRow());
        assertEquals(expectedPersonList.get(2).getRow(), actualCars.get(2).getRow());
        assertEquals(expectedPersonList.get(3).getRow(), actualCars.get(3).getRow());
        assertEquals(expectedPersonList.get(4).getRow(), actualCars.get(4).getRow());
    }

    private static List<Person> unmarshalling() {
        List<Person> persons = new ArrayList<>(5);
        Person person1 = new Person();
        person1.setRow(1);
        person1.setName("Rafique");
        person1.setAddress("Melbourne");
        person1.setEmail("raf@abc.com");
        person1.setMobile("91701");
        persons.add(person1);
        Person person2 = new Person();
        person2.setRow(2);
        person2.setName("Sam");
        person2.setAddress("Melbourne");
        person2.setEmail("sam@abc.com");
        person2.setMobile("617019");
        persons.add(person2);
        Person person3 = new Person();
        person3.setRow(3);
        person3.setName("Tony");
        person3.setAddress("Melbourne");
        person3.setEmail("tony@abc.com");
        person3.setMobile("617019");
        persons.add(person3);
        Person person4 = new Person();
        person4.setRow(4);
        person4.setName("Michael");
        person4.setAddress("Melbourne");
        person4.setEmail("mic@abc.com");
        person4.setMobile("617019");
        persons.add(person4);
        Person person5 = new Person();
        person5.setRow(5);
        person5.setName("Terry");
        person5.setAddress("Melbourne");
        person5.setEmail("terry@abc.com");
        person5.setMobile("617019");
        persons.add(person5);
        return persons;
    }
}
