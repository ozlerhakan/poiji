package com.poiji.deserialize.model;

import com.poiji.internal.annotation.ExcelCell;

/**
 * Created by hakan on 22/01/2017.
 */
public abstract class Vehicle {

    @ExcelCell(1)
    protected String name;

    @ExcelCell(2)
    protected int year;

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }
}
