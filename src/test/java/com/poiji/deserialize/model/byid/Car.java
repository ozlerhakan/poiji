package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCell;

/**
 * Created by hakan on 22/01/2017.
 */
public class Car extends Vehicle {

    @ExcelCell(3)
    private int nOfSeats;

    public int getnOfSeats() {
        return nOfSeats;
    }

    public void setnOfSeats(int nOfSeats) {
        this.nOfSeats = nOfSeats;
    }
}
