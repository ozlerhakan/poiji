package com.poiji.deserialize.model.byname;

import com.poiji.annotation.ExcelCellName;

/**
 * Created by hakan on 7.11.2021
 */
public class DataRowModel {

    @ExcelCellName("Original term")
    private String term;

    @ExcelCellName("Replace by")
    private String replace;
}
