package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCell;

/**
 * Created by hakan on 2018-12-01
 */
public class ConfigPerson {

    @ExcelCell(0)
    protected String employeeId;

    @ExcelCell(1)
    protected String name;

    @ExcelCell(2)
    protected String surname;

    @ExcelCell(3)
    protected String age;

    @ExcelCell(4)
    protected String single;

    @ExcelCell(5)
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

    public String getSingle() {
        return single;
    }

    public String getBirthday() {
        return birthday;
    }

}
