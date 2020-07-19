package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;

import java.math.BigDecimal;

public class TestInfoXLSX {

    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String desc;

    @ExcelCell(1)
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }
}
