package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import com.poiji.annotation.ExcelUnknownCells;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@ExcelSheet("test")
public final class WriteEntity {

    @ExcelCell(0)
    private long primitiveLong;
    @ExcelCellName(value = "TexT", order = 5)
    private String text;
    @ExcelCell(4)
    private Float wrappedFloat;
    @ExcelCellName("Afloat")
    private float primitiveFloat;
    @ExcelUnknownCells
    private Map<String, String> unknown;
    @ExcelUnknownCells
    private Map<String, String> anotherUnknown;
    @ExcelCellName("double")
    private double primitiveDouble;
    @ExcelCellName(value = "Double", order = 10)
    private Double wrappedDouble;
    @ExcelCellName("boolean")
    private boolean primitiveBoolean;
    @ExcelCellName("Boolean")
    private Boolean wrappedBoolean;
    @ExcelCellName("char")
    private char primitiveChar;
    @ExcelCellName("Character")
    private Character wrappedCharacter;
    @ExcelCellName("Date")
    private Date date;
    @ExcelCellName("LocalDate")
    private LocalDate localDate;
    @ExcelCellName("LocalDateTime")
    private LocalDateTime localDateTime;
    @ExcelCellName("ZonedDateTime")
    private ZonedDateTime zonedDateTime;

    public WriteEntity setPrimitiveLong(final long primitiveLong) {
        this.primitiveLong = primitiveLong;
        return this;
    }

    public WriteEntity setText(final String text) {
        this.text = text;
        return this;
    }

    public WriteEntity setWrappedFloat(final Float wrappedFloat) {
        this.wrappedFloat = wrappedFloat;
        return this;
    }

    public WriteEntity setPrimitiveFloat(final float primitiveFloat) {
        this.primitiveFloat = primitiveFloat;
        return this;
    }

    public WriteEntity setUnknown(final Map<String, String> unknown) {
        this.unknown = unknown;
        return this;
    }

    public WriteEntity setAnotherUnknown(final Map<String, String> anotherUnknown) {
        this.anotherUnknown = anotherUnknown;
        return this;
    }

    public WriteEntity setPrimitiveDouble(final double primitiveDouble) {
        this.primitiveDouble = primitiveDouble;
        return this;
    }

    public WriteEntity setWrappedDouble(final Double wrappedDouble) {
        this.wrappedDouble = wrappedDouble;
        return this;
    }

    public WriteEntity setPrimitiveBoolean(final boolean primitiveBoolean) {
        this.primitiveBoolean = primitiveBoolean;
        return this;
    }

    public WriteEntity setWrappedBoolean(final Boolean wrappedBoolean) {
        this.wrappedBoolean = wrappedBoolean;
        return this;
    }

    public WriteEntity setPrimitiveChar(final char primitiveChar) {
        this.primitiveChar = primitiveChar;
        return this;
    }

    public WriteEntity setWrappedCharacter(final Character wrappedCharacter) {
        this.wrappedCharacter = wrappedCharacter;
        return this;
    }

    public WriteEntity setDate(final Date date) {
        this.date = date;
        return this;
    }

    public WriteEntity setLocalDate(final LocalDate localDate) {
        this.localDate = localDate;
        return this;
    }

    public WriteEntity setLocalDateTime(final LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }

    public WriteEntity setZonedDateTime(final ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
        return this;
    }
}
