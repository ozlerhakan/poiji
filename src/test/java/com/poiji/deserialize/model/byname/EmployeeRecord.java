package com.poiji.deserialize.model.byname;

import com.poiji.annotation.ExcelCellName;

/**
 * A record class to test Java Records support in Poiji
 */
public record EmployeeRecord(
        @ExcelCellName("ID")
        long employeeId,
        
        @ExcelCellName("NAME")
        String name,
        
        @ExcelCellName("SURNAME")
        String surname,
        
        @ExcelCellName("AGE")
        int age,
        
        @ExcelCellName("SINGLE")
        boolean single,
        
        @ExcelCellName("BIRTHDAY")
        String birthday
) {
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", single=" + single +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
