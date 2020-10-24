package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCellName;

/**
 * Created by hakan on 2018-12-01
 */
public class FormattingPerson {

    @ExcelCellName("id")
    protected String employeeId;

    @ExcelCellName("name")
    protected String name;

    @ExcelCellName("surname")
    protected String surname;

    @ExcelCellName("age")
    protected String age;

    @ExcelCellName("single")
    protected String single;

    @ExcelCellName("birthday")
    protected String birthday;

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAge() {
        return age;
    }

    public String getBirthday() {
        return birthday;
    }

}
