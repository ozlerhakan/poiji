package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;

/**
 * Created by hakan on 22/01/2017.
 */
public abstract class Vehicle {

    @ExcelCellName("NAME")
    protected String name;

    @ExcelCell(2)
    protected int year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
