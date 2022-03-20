package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import com.poiji.annotation.ExcelUnknownCells;

import java.util.Map;

public class OrgWithUnknownCells {

    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String id;

    @ExcelCell(1)
    private String externalId;

    @ExcelUnknownCells
    private Map<String, String> unknownCells;

    @ExcelCell(2)
    private String name;

    @ExcelCell(3)
    private String customerExternalId;

    public Map<String, String> getUnknownCells() {
        return unknownCells;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerExternalId() {
        return customerExternalId;
    }

    public void setCustomerExternalId(String customerExternalId) {
        this.customerExternalId = customerExternalId;
    }
}
