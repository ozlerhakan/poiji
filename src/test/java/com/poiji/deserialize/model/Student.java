package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelSheet;

/**
 * A Student POJO.
 */
@ExcelSheet("Sheet2")
public class Student {
    @ExcelCell(0)
    private String name;

    @ExcelCell(1)
    private String id;

    @ExcelCell(2)
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "Student {" +
                " name=" + name +
                ", id=" + id + "'" +
                ", phone='" + phone + "'" +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Student)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Student other = (Student) obj;
        return this.name.equals(other.name)
                && this.id.equals(other.id)
                && this.phone.equals(other.phone);
    }

    @Override
    public int hashCode() {
        int result = 13;
        result = 17 * result + name.hashCode();
        result = 17 * result + id.hashCode();
        result = 17 * result + phone.hashCode();
        return result;
    }
}
