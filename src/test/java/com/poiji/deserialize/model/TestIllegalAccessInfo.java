package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;

import java.math.BigDecimal;

public class TestIllegalAccessInfo {

    @ExcelCell(1)
    private final static BigDecimal amount = BigDecimal.valueOf(0);
}
