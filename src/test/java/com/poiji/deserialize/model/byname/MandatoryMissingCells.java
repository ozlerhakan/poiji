package com.poiji.deserialize.model.byname;

import com.poiji.annotation.ExcelCellName;

/**
 * Created by ar on 28/12/2022.
 */
public class MandatoryMissingCells {

    @ExcelCellName("Name")
    protected String name;

    @ExcelCellName(value = "Address", mandatoryCell = true)
    protected String address;

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
