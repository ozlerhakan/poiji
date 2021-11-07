package com.poiji.deserialize.model.byname;

import com.poiji.annotation.ExcelCellName;

/**
 * Created by hakan on 7.11.2021
 */
public class HiddenModel {

    @ExcelCellName("HIDDEN 1")
    private String hiddenColumn;

    public String getHiddenColumn() {
        return hiddenColumn;
    }

    public void setHiddenColumn(String hiddenColumn) {
        this.hiddenColumn = hiddenColumn;
    }
}
