package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCellRange;

/**
 * Record class to test @ExcelCellRange support
 */
public record ClassesRecord(
        @ExcelCellRange
        PersonATest classA,
        
        @ExcelCellRange
        PersonBTest classB
) {
}
