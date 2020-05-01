package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;

/**
 * Created by hakan on 1.05.2020
 */
public class TeamMember {


    @ExcelCell(0)
    private String function;
    @ExcelCell(1)
    private String lastName;
    @ExcelCell(2)
    private String firstName;

    @ExcelCellName("Team Name")
    private String teamName;

    public String getFunction() {
        return function;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getTeamName() {
        return teamName;
    }
}
