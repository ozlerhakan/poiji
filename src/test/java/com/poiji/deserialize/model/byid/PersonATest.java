package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCellName;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersonATest {

    @ExcelCellName("NameA")
    private String name;

    @ExcelCellName("AgeA")
    private Integer age;

    @ExcelCellName("CityA")
    private String city;

    @ExcelCellName("StateA")
    private String state;

    @ExcelCellName("Zip CodeA")
    private Integer zip;

    @Test
    public void testGettersAndSetters(){
        PersonATest person = new PersonATest();
        person.setName("Test Name");
        assertEquals("Test Name", person.getName());
        assertEquals(null, person.getAge());
        assertEquals(null, person.getCity());
        assertEquals(null, person.getState());
        assertEquals(null, person.getZip());

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public Integer getZip() {
        return zip;
    }
}
