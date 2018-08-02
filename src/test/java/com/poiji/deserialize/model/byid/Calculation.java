package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCell;

import java.time.LocalDate;

/**
 * Created by hakan on 02/08/2018
 */
public class Calculation {

    @ExcelCell(0)
    private LocalDate fromDate;

    @ExcelCell(1)
    private LocalDate toDate;

    @ExcelCell(2)
    private String is;

    @ExcelCell(3)
    private Float total;

    @ExcelCell(4)
    private Float turnover;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public String getIs() {
        return is;
    }

    public Float getTotal() {
        return total;
    }

    public Float getTurnover() {
        return turnover;
    }
}
