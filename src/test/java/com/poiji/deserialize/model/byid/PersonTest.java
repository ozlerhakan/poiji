package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCellName;

public class PersonTest {

    @ExcelCellName("Name")
    private String name;

    @ExcelCellName("Age")
    private Integer age;

    @ExcelCellName("City")
    private String city;

    @ExcelCellName("State")
    private String state;

    @ExcelCellName("Zip Code")
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
