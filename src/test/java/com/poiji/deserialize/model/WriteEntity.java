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
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

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

    public long getPrimitiveLong() {
        return primitiveLong;
    }

    public WriteEntity setPrimitiveLong(final long primitiveLong) {
        this.primitiveLong = primitiveLong;
        return this;
    }

    public String getText() {
        return text;
    }

    public WriteEntity setText(final String text) {
        this.text = text;
        return this;
    }

    public Float getWrappedFloat() {
        return wrappedFloat;
    }

    public WriteEntity setWrappedFloat(final Float wrappedFloat) {
        this.wrappedFloat = wrappedFloat;
        return this;
    }

    public float getPrimitiveFloat() {
        return primitiveFloat;
    }

    public WriteEntity setPrimitiveFloat(final float primitiveFloat) {
        this.primitiveFloat = primitiveFloat;
        return this;
    }

    public Map<String, String> getUnknown() {
        return unknown;
    }

    public WriteEntity setUnknown(final Map<String, String> unknown) {
        this.unknown = unknown;
        return this;
    }

    public Map<String, String> getAnotherUnknown() {
        return anotherUnknown;
    }

    public WriteEntity setAnotherUnknown(final Map<String, String> anotherUnknown) {
        this.anotherUnknown = anotherUnknown;
        return this;
    }

    public double getPrimitiveDouble() {
        return primitiveDouble;
    }

    public WriteEntity setPrimitiveDouble(final double primitiveDouble) {
        this.primitiveDouble = primitiveDouble;
        return this;
    }

    public Double getWrappedDouble() {
        return wrappedDouble;
    }

    public WriteEntity setWrappedDouble(final Double wrappedDouble) {
        this.wrappedDouble = wrappedDouble;
        return this;
    }

    public boolean isPrimitiveBoolean() {
        return primitiveBoolean;
    }

    public WriteEntity setPrimitiveBoolean(final boolean primitiveBoolean) {
        this.primitiveBoolean = primitiveBoolean;
        return this;
    }

    public Boolean getWrappedBoolean() {
        return wrappedBoolean;
    }

    public WriteEntity setWrappedBoolean(final Boolean wrappedBoolean) {
        this.wrappedBoolean = wrappedBoolean;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public WriteEntity setDate(final Date date) {
        this.date = date;
        return this;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public WriteEntity setLocalDate(final LocalDate localDate) {
        this.localDate = localDate;
        return this;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public WriteEntity setLocalDateTime(final LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public WriteEntity setBigDecimal(final BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
        return this;
    }

    public byte getPrimitiveByte() {
        return primitiveByte;
    }

    public WriteEntity setPrimitiveByte(final byte primitiveByte) {
        this.primitiveByte = primitiveByte;
        return this;
    }

    public Byte getWrappedByte() {
        return wrappedByte;
    }

    public WriteEntity setWrappedByte(final Byte wrappedByte) {
        this.wrappedByte = wrappedByte;
        return this;
    }

    public short getPrimitiveShort() {
        return primitiveShort;
    }

    public WriteEntity setPrimitiveShort(final short primitiveShort) {
        this.primitiveShort = primitiveShort;
        return this;
    }

    public Short getWrappedShort() {
        return wrappedShort;
    }

    public WriteEntity setWrappedShort(final Short wrappedShort) {
        this.wrappedShort = wrappedShort;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WriteEntity that = (WriteEntity) o;
        return primitiveLong == that.primitiveLong && Float.compare(that.primitiveFloat,
            primitiveFloat
        ) == 0 && Double.compare(that.primitiveDouble,
            primitiveDouble
        ) == 0 && primitiveBoolean == that.primitiveBoolean && primitiveByte == that.primitiveByte && primitiveShort == that.primitiveShort && Objects
            .equals(text, that.text) && Objects.equals(wrappedFloat, that.wrappedFloat) && Objects.equals(unknown,
            that.unknown
        ) && Objects.equals(anotherUnknown, that.anotherUnknown) && Objects.equals(wrappedDouble,
            that.wrappedDouble
        ) && Objects.equals(wrappedBoolean, that.wrappedBoolean) && Objects.equals(
            date,
            that.date
        ) && Objects.equals(localDate, that.localDate) && Objects.equals(
            localDateTime,
            that.localDateTime
        ) && Objects.equals(bigDecimal, that.bigDecimal) && Objects.equals(
            wrappedByte,
            that.wrappedByte
        ) && Objects.equals(wrappedShort, that.wrappedShort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primitiveLong,
            text,
            wrappedFloat,
            primitiveFloat,
            unknown,
            anotherUnknown,
            primitiveDouble,
            wrappedDouble,
            primitiveBoolean,
            wrappedBoolean,
            date,
            localDate,
            localDateTime,
            bigDecimal,
            primitiveByte,
            wrappedByte,
            primitiveShort,
            wrappedShort
        );
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WriteEntity.class.getSimpleName() + "[", "]")
            .add("primitiveLong=" + primitiveLong)
            .add("text='" + text + "'")
            .add("wrappedFloat=" + wrappedFloat)
            .add("primitiveFloat=" + primitiveFloat)
            .add("unknown=" + unknown)
            .add("anotherUnknown=" + anotherUnknown)
            .add("primitiveDouble=" + primitiveDouble)
            .add("wrappedDouble=" + wrappedDouble)
            .add("primitiveBoolean=" + primitiveBoolean)
            .add("wrappedBoolean=" + wrappedBoolean)
            .add("date=" + date)
            .add("localDate=" + localDate)
            .add("localDateTime=" + localDateTime)
            .add("bigDecimal=" + bigDecimal)
            .add("primitiveByte=" + primitiveByte)
            .add("wrappedByte=" + wrappedByte)
            .add("primitiveShort=" + primitiveShort)
            .add("wrappedShort=" + wrappedShort)
            .toString();
    }
}
