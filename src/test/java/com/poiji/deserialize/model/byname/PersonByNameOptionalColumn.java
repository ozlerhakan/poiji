package com.poiji.deserialize.model.byname;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;

/**
 * Created by ar on 9/03/2018.
 */
public class PersonByNameOptionalColumn {

    @ExcelCellName("Name")
    protected String name;

    // This column can be either required or not.
    @ExcelCellName("Address")
    protected String address;

    // This column can be either required or not.
    @ExcelCellName(value = "Mobile", mandatoryHeader = false, mandatoryCell = false)
    protected String mobile;

    // This header along with values belonging to its cells will be required.
    @ExcelCellName(value = "Email", mandatoryHeader = true, mandatoryCell = true)
    protected String email;

    @ExcelCellName(value = "This column will be missing. We ignore it.", mandatoryHeader = false)
    protected String missingColumn;

    @ExcelCellName(value = "This column will be missing. We ignore it.", mandatoryHeader = false)
    protected String missingColumn6;

    // This column will be missing. We ignore it.
    @ExcelCell(value = 7, mandatoryHeader = false)
    protected String missingColumn7;

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
