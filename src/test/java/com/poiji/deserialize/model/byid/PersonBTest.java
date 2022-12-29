package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCellName;

public class PersonBTest {

    @ExcelCellName(value = "NameB", mandatoryCell = true)
    private String name;

    @ExcelCellName(value = "AgeB", mandatoryCell = false)
    private Integer age;

    @ExcelCellName("CityB")
    private String city;

    @ExcelCellName("StateB")
    private String state;

    @ExcelCellName("Zip CodeB")
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
