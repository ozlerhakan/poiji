package com.poiji.deserialize.model;

import com.poiji.internal.annotation.Index;

/**
 * Created by hakan on 22/01/2017.
 */
public abstract class Vehicle {

    @Index(column = 1, cell = 1)
    protected String name;

    @Index(column = 2, cell = 2)
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
