package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;

import java.time.LocalDate;

public class AutoImport {

    @ExcelCellName(value = "fabricacion", mandatory = false)
    private LocalDate fecha;

    @ExcelCell(0)
    private String clase;

    @ExcelCell(1)
    private String tipo;

    @ExcelCell(2)
    private String subtipo;

    @ExcelCell(3)
    private String marca;

    @ExcelCell(4)
    private String modelo;

    @ExcelCell(5)
    private String submodelo;

    @ExcelCell(6)
    private String dsc;

    @ExcelCell(7)
    private String pmercado;

    @ExcelCell(8)
    private Double ptoma;

    @ExcelCell(9)
    private String anio;

    @ExcelCell(10)
    private String cil;

    @ExcelCell(11)
    private String com;

    @ExcelCell(12)
    private String tp;

    @ExcelCell(13)
    private Integer pasajeros;

    @ExcelCell(14)
    private Integer puertas;

}
