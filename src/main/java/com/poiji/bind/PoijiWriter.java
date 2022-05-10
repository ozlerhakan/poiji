package com.poiji.bind;

import com.poiji.annotation.ExcelCellName;
import com.poiji.config.DefaultCasting;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface PoijiWriter {

    static <T> void toExcel(final Sheet sheet,
                            final Collection<T> objects,
                            final Class<T> type,
                            final PoijiOptions options) {

        Field[] fields = type.getDeclaredFields();
        List<Field> headers = new ArrayList<>();

        int rowIndex = 0;
        Row header = sheet.createRow(rowIndex);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            ExcelCellName[] annotations = field.getAnnotationsByType(ExcelCellName.class);
            for (ExcelCellName annotation : annotations) {
                headers.add(field);
                Cell cell = header.createCell(i);
                cell.setCellValue(annotation.value());
                break;
            }
        }

        for (T dto : objects) {
            Row dataRow = sheet.createRow(++rowIndex);
            int cellIndex = 0;
            for (Field field : headers) {
                Cell cell = dataRow.createCell(cellIndex++);
                Object object;
                try {
                    object = field.get(dto);
                } catch (IllegalAccessException e) {
                    throw new PoijiException("Error during processing POJO to Excel", e);
                }
                DefaultCasting.setCellValueByClass(cell, object, field.getType(), options);
            }
        }
    }
}
