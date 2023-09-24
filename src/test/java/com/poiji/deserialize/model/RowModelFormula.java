package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;

/**
 * Created by jmorgan on 12.09.2023
 */
public class RowModelFormula {

    @ExcelCell(0)
    private double currencyValue;

    @ExcelCell(1)
    private double formulaValue;

    public double getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(double currencyValue) {
        this.currencyValue = currencyValue;
    }

    public double getFormulaValue() {
        return formulaValue;
    }

    public void setFormulaValue(double formulaValue) {
        this.formulaValue = formulaValue;
    }
}
