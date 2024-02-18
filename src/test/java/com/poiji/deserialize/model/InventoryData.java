package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCellName;

import java.util.Objects;

/**
 * An InventoryData POJO.
 */
public class InventoryData {
    @ExcelCellName(value = "Id")
    private Integer id;

    @ExcelCellName(value = "", expression = "Author|Composer")
    private String author;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "InventoryData{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryData that = (InventoryData) o;
        return Objects.equals(id, that.id) && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author);
    }
}
