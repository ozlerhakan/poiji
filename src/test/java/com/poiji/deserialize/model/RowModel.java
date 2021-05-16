package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;

/**
 * Created by hakan on 16.05.2021
 */
public class RowModel {

    @ExcelCell(0)
    private Long rowValue;

    public Long getRowValue() {
        return rowValue;
    }

    public void setRowValue(Long rowValue) {
        this.rowValue = rowValue;
    }
}
