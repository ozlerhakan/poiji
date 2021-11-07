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

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getReplace() {
        return replace;
    }

    public void setReplace(String replace) {
        this.replace = replace;
    }
}
