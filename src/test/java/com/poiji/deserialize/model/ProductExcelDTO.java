package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;

/**
 * Created by hakan on 2019-01-10
 */
public class ProductExcelDTO {

    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String sku;

    @ExcelCell(1)
    private String description;

    @ExcelCell(2)
    private String price;

    @ExcelCellName("COLOR")
    private String color;

    @ExcelCellName("TALLE")
    private String size;

    @ExcelCellName("STOCK")
    private String stock;

    @Override
    public String toString() {
        return "ProductExcelDTO {" +
                " rowIndex=" + rowIndex +
                ", sku=" + sku + "'" +
                ", description='" + description + "'" +
                ", price='" + price + "'" +
                ", color='" + color + "'" +
                ", size='" + size + "'" +
                ", stock='" + stock + "'" +
                '}';
    }


    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descripcion) {
        this.description = descripcion;
    }

    public String getColors() {
        return color;
    }

    public void setColors(String colors) {
        this.color = colors;
    }

    public String getSizes() {
        return size;
    }

    public void setSizes(String sizes) {
        this.size = sizes;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
