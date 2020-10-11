package com.poiji.deserialize.model.byname;

import com.poiji.annotation.ExcelCellName;

/**
 * Created by hakan on 11.10.2020
 */
public class CellFormatModel {


    @ExcelCellName("age2")
    protected Integer age;

    public Integer getAge() {
        return age;
    }
}
