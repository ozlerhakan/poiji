package com.poiji.config;

public final class DefaultCastingError {
    private String value;

    private Object defaultValue;

    private String sheetName;

    private int row;

    private int column;

    private Exception exception;

    DefaultCastingError(String value, Object defaultValue, String sheetName, int row, int column, Exception exception) {
        this.value = value;
        this.defaultValue = defaultValue;
        this.sheetName = sheetName;
        this.row = row;
        this.column = column;
        this.exception = exception;
    }

    public String getValue() {
        return value;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public String getSheetName() {
        return sheetName;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Exception getException() {
        return exception;
    }
}
