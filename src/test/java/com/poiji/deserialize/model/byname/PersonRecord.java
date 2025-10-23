package com.poiji.deserialize.model.byname;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;

/**
 * A record class to test Java Records support in Poiji
 */
public record PersonRecord(
        @ExcelCellName("Name")
        String name,
        
        @ExcelCellName("Address")
        String address,
        
        @ExcelCellName("Mobile")
        String mobile,
        
        @ExcelCellName("Email")
        String email,
        
        @ExcelCellName("Insurance")
        float insurance,
        
        @ExcelRow
        int row
) {
}
