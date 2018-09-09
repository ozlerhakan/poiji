package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCell;

import java.util.Date;

/**
 * Created by hakan on 17/01/2017.
 */
public class EmployeeExtended extends Employee {

    @ExcelCell(6)
    private double rate;

    @ExcelCell(7)
    private Date date;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Employee{"
                + "employeeId=" + employeeId
                + ", name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", age=" + age
                + ", single=" + single
                + ", birthday='" + birthday + '\''
                + ", rate=" + rate
                + ", date=" + date
                + '}';

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof EmployeeExtended)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        EmployeeExtended other = (EmployeeExtended) obj;
        return this.age.equals(other.age)
                && this.birthday.equals(other.birthday)
                && this.date.equals(other.date)
                && this.employeeId == other.employeeId
                && this.name.equals(other.name)
                && this.rate == other.rate
                && this.surname.equals(other.surname);
    }

    @Override
    public int hashCode() {
        int result = 13;
        result = 17 * result + age.hashCode();
        result = 17 * result + birthday.hashCode();
        result = 17 * result + date.hashCode();
        result = 17 * result + (int) employeeId;
        result = 17 * result + name.hashCode();
        result = 17 * result + (int) rate;
        result = 17 * result + surname.hashCode();
        return result;
    }

}
