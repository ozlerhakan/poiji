package com.poiji.bind.mapping;

import com.poiji.annotation.ExcelCell;
import com.poiji.bind.Unmarshaller;
import com.poiji.annotation.ExcelRow;
import com.poiji.exception.IllegalCastException;
import com.poiji.exception.PoijiInstantiationException;
import com.poiji.option.PoijiOptions;
import com.poiji.util.Casting;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * This is the main class that converts the excel sheet fromExcel Java object
 * Created by hakan on 16/01/2017.
 */
abstract class HSSFUnmarshaller implements Unmarshaller {

    private final DataFormatter dataFormatter;
    private final PoijiOptions options;
    private final Casting casting;

    HSSFUnmarshaller(PoijiOptions options) {
        this.options = options;
        dataFormatter = new DataFormatter();
        casting = Casting.getInstance();
    }

    @Override
    public <T> List<T> unmarshal(Class<T> type) {
        Workbook workbook = workbook();
        Sheet sheet = workbook.getSheetAt(options.sheetIndex());

        int skip = options.skip();
        int maxPhysicalNumberOfRows = sheet.getPhysicalNumberOfRows() + 1 - skip;
        List<T> list = new ArrayList<>(maxPhysicalNumberOfRows);

        for (Row currentRow : sheet) {

            if (skip(currentRow, skip))
                continue;

            if (isRowEmpty(currentRow))
                continue;

            if (maxPhysicalNumberOfRows > list.size()) {
                T t = deserialize0(currentRow, type);
                list.add(t);
            }
        }

        return list;
    }

    private <T> T deserialize0(Row currentRow, Class<T> type) {
        T instance;
        try {
            instance = type.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new PoijiInstantiationException("Cannot create a new instance of " + type.getName());
        }

        return setFieldValue(currentRow, type, instance);
    }

    private <T> T tailSetFieldValue(Row currentRow, Class<? super T> type, T instance) {
        for (Field field : type.getDeclaredFields()) {
            ExcelRow excelRow = field.getAnnotation(ExcelRow.class);
            if (excelRow != null) {
                Object o;
                o = casting.castValue(field.getType(), valueOf(currentRow.getRowNum()), options);
                setFieldData(instance, field, o);
            }
            ExcelCell index = field.getAnnotation(ExcelCell.class);
            if (index != null) {
                Class<?> fieldType = field.getType();
                Cell cell = currentRow.getCell(index.value());

                Object o;
                if (cell != null) {
                    String value = dataFormatter.formatCellValue(cell);
                    o = casting.castValue(fieldType, value, options);
                } else {
                    o = casting.castValue(fieldType, "", options);
                }
                setFieldData(instance, field, o);
            }
        }
        return instance;
    }

    private <T> void setFieldData(T instance, Field field, Object o) {
        try {
            field.setAccessible(true);
            field.set(instance, o);
        } catch (IllegalAccessException e) {
            throw new IllegalCastException("Unexpected cast type {" + o + "} of field" + field.getName());
        }
    }

    private <T> T setFieldValue(Row currentRow, Class<? super T> subclass, T instance) {
        return subclass == null
                ? instance
                : tailSetFieldValue(currentRow, subclass, setFieldValue(currentRow, subclass.getSuperclass(), instance));
    }

    private boolean skip(final Row currentRow, int skip) {
        return currentRow.getRowNum() + 1 <= skip;
    }

    private boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cell != null && cell.getCellTypeEnum() != CellType.BLANK)
                return false;
        }
        return true;
    }

    protected abstract Workbook workbook();
}
