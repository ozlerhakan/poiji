package com.poiji.deserialize.metadata.model;

import com.poiji.annotation.ExcelProperty;

import java.util.Date;

public class CorePropertyEntity {

    @ExcelProperty
    private String title;

    @ExcelProperty
    private Date modified;

    public String getTitle() {
        return title;
    }
}
