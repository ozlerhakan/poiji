package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import com.poiji.annotation.ExcelUnknownCells;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ExcelSheet("test")
public final class WriteEntity {

    @ExcelCell(0)
    private long primitiveLong;
    @ExcelCellName(value = "TexT", order = 5)
    private String text;
    @ExcelCell(4)
    private Float wrappedFloat;
    @ExcelCellName("float")
    private float primitiveFloat;
    @ExcelUnknownCells
    private Map<String, String> unknown = new ConcurrentHashMap<>();
    @ExcelUnknownCells
    private Map<String, String> anotherUnknown = new ConcurrentHashMap<>();
    @ExcelCellName("double")
    private double primitiveDouble;
    @ExcelCellName(value = "Double", order = 10)
    private Double wrappedDouble;
    @ExcelCellName("boolean")
    private boolean primitiveBoolean;
    @ExcelCellName("Boolean")
    private Boolean wrappedBoolean;
    @ExcelCellName("Date")
    private Date date;
    @ExcelCellName("LocalDate")
    private LocalDate localDate;
    @ExcelCellName("LocalDateTime")
    private LocalDateTime localDateTime;
    @ExcelCellName("BigDecimal")
    private BigDecimal bigDecimal;
    @ExcelCellName("byte")
    private byte primitiveByte;
    @ExcelCellName("Byte")
    private Byte wrappedByte;
    @ExcelCellName("short")
    private short primitiveShort;
    @ExcelCellName("Short")
    private Short wrappedShort;

}
