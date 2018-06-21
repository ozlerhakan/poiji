package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCell;

/**
 * Created by hakan on 17/01/2017.
 */
public class Employee {

    @ExcelCell(0)
    protected long employeeId;

    @ExcelCell(1)
    protected String name = "";

    @ExcelCell(2)
    protected String surname;

    @ExcelCell(3)
    protected Integer age;

    @ExcelCell(4)
    protected boolean single;

    @ExcelCell(5)
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
                ", age=" + (age == null ? 0 : age)  +
                ", single=" + single +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
