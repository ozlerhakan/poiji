package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelReadOnly;
import com.poiji.annotation.ExcelSheet;
import com.poiji.annotation.ExcelWriteOnly;
import java.util.Objects;
import java.util.StringJoiner;

@ExcelSheet("test")
public final class IgnoreEntity {

    @ExcelCell(0)
    private long primitiveLong;
    @ExcelCellName(value = "TexT", order = 5)
    @ExcelReadOnly
    private String readText;
    @ExcelCellName(value = "TexT", order = 5)
    @ExcelWriteOnly
    private String writeText;

    public long getPrimitiveLong() {
        return primitiveLong;
    }

    public IgnoreEntity setPrimitiveLong(final long primitiveLong) {
        this.primitiveLong = primitiveLong;
        return this;
    }

    public String getReadText() {
        return readText;
    }

    public IgnoreEntity setReadText(final String readText) {
        this.readText = readText;
        return this;
    }

    public String getWriteText() {
        return writeText;
    }

    public IgnoreEntity setWriteText(final String writeText) {
        this.writeText = writeText;
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
        final IgnoreEntity that = (IgnoreEntity) o;
        return primitiveLong == that.primitiveLong && Objects.equals(readText, that.readText) && Objects.equals(
            writeText,
            that.writeText
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(primitiveLong, readText, writeText);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", IgnoreEntity.class.getSimpleName() + "[", "]")
            .add("primitiveLong=" + primitiveLong)
            .add("readText='" + readText + "'")
            .add("writeText='" + writeText + "'")
            .toString();
    }
}
