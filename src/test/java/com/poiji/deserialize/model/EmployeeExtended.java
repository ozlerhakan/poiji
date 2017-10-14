package com.poiji.deserialize.model;

import com.poiji.internal.annotation.ExcelCell;

import java.util.Date;

/**
 * Created by hakan on 17/01/2017.
 */
public class EmployeeExtended extends Employee {

    @ExcelCell(6)
    private double rate;

    @ExcelCell(7)
    private Date date;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
                "rate=" + rate +
                ", date=" + date +
                '}';

    }
}
