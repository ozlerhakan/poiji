package com.poiji.deserialize.model;

import com.poiji.internal.annotation.Index;

/**
 * Created by hakan on 17/01/2017.
 */
public class Employee {

    @Index(column = 0, cell = 0)
    private long employeeId;

    @Index(column = 1, cell = 1)
    private String name;

    @Index(column = 2, cell = 2)
    private String surname;

    @Index(column = 3, cell = 3)
    private int age;

    @Index(column = 4, cell = 4)
    private boolean single;

    @Index(column = 5, cell = 5)
    private String birthday;

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
