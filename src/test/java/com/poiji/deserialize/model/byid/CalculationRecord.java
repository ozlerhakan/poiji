package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCell;

import java.time.LocalDate;

/**
 * A record class to test Java Records support with @ExcelCell (by index)
 */
public record CalculationRecord(
        @ExcelCell(0)
        LocalDate fromDate,
        
        @ExcelCell(1)
        LocalDate toDate,
        
        @ExcelCell(2)
        String is,
        
        @ExcelCell(3)
        Float total,
        
        @ExcelCell(4)
        Float turnover
) {
}
