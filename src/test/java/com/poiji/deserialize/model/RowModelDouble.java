package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;

/**
 * Created by beckerdennis on 07.12.2021
 */
public class RowModelDouble {

    @ExcelCell(0)
    private double rowValue;

    public double getRowValue() {
        return rowValue;
    }

}