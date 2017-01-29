package com.poiji.deserialize.model;

import com.poiji.internal.annotation.ExcelCell;

/**
 * Created by hakan on 22/01/2017.
 */
public class Car extends Vehicle {

    @ExcelCell(3)
    private int nOfSeats;

    public void setnOfSeats(int nOfSeats) {
        this.nOfSeats = nOfSeats;
    }

    public int getnOfSeats() {
        return nOfSeats;
    }
}
