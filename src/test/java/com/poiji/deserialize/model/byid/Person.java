package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;

/**
 * Created by ar on 9/03/2018.
 */
public class Person {

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
