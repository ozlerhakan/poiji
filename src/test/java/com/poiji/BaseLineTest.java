package com.poiji;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Person;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class BaseLineTest {
    @Test
    public void printBaseline() {
        List<Person> people = Poiji.fromExcel(new File("src/test/resources/person.xlsx"), Person.class);
        for (Person p : people) {
            System.out.println(p);
        }
    }
}