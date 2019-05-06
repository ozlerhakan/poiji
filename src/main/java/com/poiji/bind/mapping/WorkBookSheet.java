package com.poiji.bind.mapping;

/**
 * @author Matthew 2018/09/01
 */
final class WorkBookSheet {

    private String name;
    private String sheetId;
    private String state = "visible";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
