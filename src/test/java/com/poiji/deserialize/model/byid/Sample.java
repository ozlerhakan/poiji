package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCell;

/**
 * Created by hakan on 07/08/2018
 */
public class Sample {

    @ExcelCell(0)
    private String month;

    @ExcelCell(1)
    private Double amount;

    @ExcelCell(2)
    private String other;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "month='" + month + '\'' +
                ", amount=" + amount +
                ", other='" + other + '\'' +
                '}';
    }
}
