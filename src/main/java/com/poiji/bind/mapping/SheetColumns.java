package com.poiji.bind.mapping;

import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SheetColumns {

    public static Sheet sheetAdaptor(Sheet sheet, PoijiOptions options) {

        try {
            Workbook workbook = sheet.getWorkbook();
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            evaluator.setIgnoreMissingWorkbooks(true);
            Workbook result = new XSSFWorkbook();
            Sheet resultSheet = result.createSheet();
            List<String> headersDone = new ArrayList<>();
            List<Integer> dontAvoid = new ArrayList<>();
            for (Row row : sheet) {
                if (row.getRowNum() < options.getHeaderStart()) continue;
                Row newRow = resultSheet.createRow(row.getRowNum() - options.getHeaderStart());
                for (Cell cell : row) {
                    CellType type = null;
                    if (cell.getCellType() == CellType.FORMULA) type = evaluator.evaluateFormulaCell(cell);
                    else type = cell.getCellType();
                    if (row.getRowNum() == options.getHeaderStart()) {
                        if ((type == CellType.STRING
                                && !headersDone.contains(cell.getStringCellValue())
                                && options.getColumnsToKeep().contains(cell.getStringCellValue().replace(" ", "").replace("\n", " "))
                                && cell.getStringCellValue() != "")) {
                            dontAvoid.add(cell.getColumnIndex());
                            headersDone.add(cell.getStringCellValue());
                            newRow.createCell(cell.getColumnIndex()).setCellValue(cell.getStringCellValue());
                            continue;
                        }
                    } else if (!dontAvoid.contains(cell.getColumnIndex())) continue;
                    else{
                        switch (type) {
                            case BOOLEAN:
                                newRow.createCell(cell.getColumnIndex()).setCellValue(cell.getBooleanCellValue());
                                break;
                            case NUMERIC:
                                newRow.createCell(cell.getColumnIndex()).setCellValue(cell.getNumericCellValue());
                                break;
                            case STRING:
                                newRow
                                        .createCell(cell.getColumnIndex())
                                        .setCellValue(cell.getStringCellValue().replace(" ", "").replace("\n", " "));
                                break;
                            case BLANK:
                                newRow.createCell(cell.getColumnIndex()).setCellValue("");
                        }
                    }

                }
            }
            return resultSheet;
        } catch (Exception exception) {
            throw new PoijiException("Problem occurred while reading data", exception);
        }
    }

}
