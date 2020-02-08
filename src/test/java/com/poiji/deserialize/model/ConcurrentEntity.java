package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ExcelSheet("test")
public final class ConcurrentEntity {

    @ExcelCell(0)
    private long primitiveLong;
    @ExcelCellName(value = "TexT,other", delimeter = ",")
    private String text;

}
