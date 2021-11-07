package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCell;

import java.math.BigDecimal;
import java.util.List;

public class ListAttributes {

    @ExcelCell(0)
    protected String employeeId;

    @ExcelCell(1)
    protected List<String> name;

    @ExcelCell(2)
    protected List<String> surname;

    @ExcelCell(3)
    protected List<Integer> age;

    @ExcelCell(4)
    protected List<BigDecimal> bigdecimal;

    @ExcelCell(5)
    protected List<Double> doubleAge;

    @ExcelCell(6)
    protected List<Long> longAge;

    @ExcelCell(7)
    protected List<Boolean> booleanSingle;

    @ExcelCell(8)
    protected List<Float> floatAge;


    public List<String> getName() {
        return name;
    }

    public List<String> getSurname() {
        return surname;
    }

    public List<Integer> getAge() {
        return age;
    }

    public List<BigDecimal> getBigdecimal() {
        return bigdecimal;
    }

    public List<Double> getDoubleAge() {
        return doubleAge;
    }

    public List<Long> getLongAge() {
        return longAge;
    }

    public List<Boolean> getBooleanSingle() {
        return booleanSingle;
    }

    public List<Float> getFloatAge() {
        return floatAge;
    }

    public void setFloatAge(List<Float> floatAge) {
        this.floatAge = floatAge;
    }
}
