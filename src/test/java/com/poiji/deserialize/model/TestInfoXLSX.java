package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;

import java.math.BigDecimal;

public class TestInfoXLSX {

    @ExcelCell(1)
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }
}
