package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;

/**
 * Created by hakan on 19.07.2020
 */
public class TeamMemberNoHeader {

    @ExcelCell(0)
    private String function;
    @ExcelCell(1)
    private String lastName;
    @ExcelCell(2)
    private String firstName;

    public String getFunction() {
        return function;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
}
