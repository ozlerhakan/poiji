package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCellName;

/**
 * Created by hakan on 22/01/2017.
 */
public class Car extends Vehicle {

    @ExcelCellName("SEATS")
    private int nOfSeats;

    public int getnOfSeats() {
        return nOfSeats;
    }

    public void setnOfSeats(int nOfSeats) {
        this.nOfSeats = nOfSeats;
    }
}
