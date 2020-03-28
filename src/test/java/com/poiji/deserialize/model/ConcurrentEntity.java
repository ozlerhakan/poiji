package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import java.util.Objects;
import java.util.StringJoiner;

@ExcelSheet("test")
public final class ConcurrentEntity {

    @ExcelCell(0)
    private long primitiveLong;
    @ExcelCellName(value = "TexT,other", columnNameDelimiter = ",")
    private String text;

    public long getPrimitiveLong() {
        return primitiveLong;
    }

    public ConcurrentEntity setPrimitiveLong(final long primitiveLong) {
        this.primitiveLong = primitiveLong;
        return this;
    }

    public String getText() {
        return text;
    }

    public ConcurrentEntity setText(final String text) {
        this.text = text;
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
        final ConcurrentEntity that = (ConcurrentEntity) o;
        return primitiveLong == that.primitiveLong && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primitiveLong, text);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ConcurrentEntity.class.getSimpleName() + "[", "]")
            .add("primitiveLong=" + primitiveLong)
            .add("text='" + text + "'")
            .toString();
    }
}
