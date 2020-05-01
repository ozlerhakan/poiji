package com.poiji.deserialize.model.byname;

import com.poiji.annotation.ExcelCellName;

/**
 * Created by hakan on 17/01/2017.
 */
public class DateExcelColumn {

    @ExcelCellName("date1")
    protected String date1;

    @ExcelCellName("date2")
    protected String date2;

    @ExcelCellName("date3")
    protected String date3;

    public String getDate1() {
        return date1;
    }

    public String getDate2() {
        return date2;
    }

    public String getDate3() {
        return date3;
    }

}
