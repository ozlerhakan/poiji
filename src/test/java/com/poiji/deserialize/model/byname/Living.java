package com.poiji.deserialize.model.byname;

import com.poiji.annotation.ExcelCellName;

public class Living {

    @ExcelCellName(value = "Absent column", mandatoryHeader = true)
    private String birthPlace;

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
}
