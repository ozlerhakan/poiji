package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;

public class AutoImport {

    @ExcelCell(7)
    private Double pmercado;

    public Double getPmercado() {
        return pmercado;
    }
}
