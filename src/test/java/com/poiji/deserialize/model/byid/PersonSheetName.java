package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import com.poiji.annotation.ExcelSheet;

/**
 * Created by ar on 9/03/2018.
 */
@ExcelSheet("Sheet3")
public class PersonSheetName {

    @ExcelRow
    protected int row;

    @ExcelCell(0)
    protected String name;

    @ExcelCell(1)
    protected String address;

    @ExcelCell(2)
    protected String mobile;

    @ExcelCell(3)
    protected String email;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
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

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" + "row=" + row + ", name=" + name + ", address=" + address + ", mobile=" + mobile + ", email=" + email + '}';
    }

}
