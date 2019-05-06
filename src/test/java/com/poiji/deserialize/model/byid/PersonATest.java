package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCellName;

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
