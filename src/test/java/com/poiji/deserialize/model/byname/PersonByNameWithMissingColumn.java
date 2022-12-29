package com.poiji.deserialize.model.byname;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;

/**
 * Created by ar on 9/03/2018.
 */
public class PersonByNameWithMissingColumn {

    @ExcelCellName("Name")
    protected String name;

    @ExcelCellName("Address")
    protected String address;

    @ExcelCellName("Mobile")
    protected String mobile;

    @ExcelCellName("Email")
    protected String email;

    @ExcelCellName(value = "This column will be missing", mandatoryHeader = true)
    protected String missingColumn;

    @ExcelCell(value = 6, mandatoryHeader = true)
    protected String missingColumn2;

    @ExcelRow
    protected int row;

    public int getRow() {
        return row;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
