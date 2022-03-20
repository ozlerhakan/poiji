package com.poiji.deserialize.model.byname;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
import com.poiji.annotation.ExcelUnknownCells;

import java.util.Map;

public class OrgWithUnknownCellsByName {

    public static final String HEADER_ORGANISATION_ID = "Organisation ID";
    public static final String HEADER_CUSTOMER_EXTERNAL_ID = "Customer External ID";
    public static final String HEADER_ORGANISATION_EXTERNAL_ID = "Organisation External ID";
    public static final String HEADER_ORGANISATION_NAME = "Organisation Name";

    @ExcelRow
    private int rowIndex;

    @ExcelCellName(HEADER_ORGANISATION_ID)
    private String id;

    @ExcelCellName(HEADER_ORGANISATION_EXTERNAL_ID)
    private String externalId;

    @ExcelUnknownCells
    private Map<String, String> unknownCells;

    @ExcelCellName(HEADER_ORGANISATION_NAME)
    private String name;

    @ExcelCellName(HEADER_CUSTOMER_EXTERNAL_ID)
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
