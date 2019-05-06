package com.poiji.deserialize.model.byname;

import com.poiji.annotation.ExcelCellName;

/**
 * Created by hakan on 17/01/2017.
 */
public class EmployeeByName {

    @ExcelCellName("ID")
    protected long employeeId;

    @ExcelCellName("NAME")
    protected String name = "";

    @ExcelCellName("SURNAME")
    protected String surname;

    @ExcelCellName("AGE")
    protected int age;

    @ExcelCellName("SINGLE")
    protected boolean single;

    @ExcelCellName("BIRTHDAY")
    protected String birthday;

    /*
        We normally don't need getters and setters to map excel cells to fields
     */

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", single=" + single +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
