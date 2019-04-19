package com.poiji.bind.mapping;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
import com.poiji.exception.IllegalCastException;
import com.poiji.option.PoijiOptions;
import com.poiji.util.Casting;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static java.lang.String.valueOf;
import static org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;

/**
 * This class handles the processing of a .xlsx file,
 * and generates a list of instances of a given type
 * <p>
 * Created by hakan on 22/10/2017
 */
final class PoijiHandler<T> implements SheetContentsHandler {

    private T instance;
    private Consumer<? super T> consumer;
    private int internalCount;

    private Class<T> type;
    private PoijiOptions options;

    private final Casting casting;
    private Map<String, Integer> titles;

    PoijiHandler(Class<T> type, PoijiOptions options, Consumer<T> consumer) {
        this.type = type;
        this.options = options;
        this.consumer = consumer;

        casting = Casting.getInstance();
        titles = new HashMap<String, Integer>();
    }

    private <T> T newInstanceOf(Class<T> type) {
        T newInstance;
        try {
            newInstance = type.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new IllegalCastException("Cannot create a new instance of " + type.getName());
        }

        return newInstance;
    }

    private void setFieldValue(String content, Class<? super T> subclass, int column) {
        if (subclass != Object.class) {
            setValue(content, subclass, column);

            setFieldValue(content, subclass.getSuperclass(), column);
        }
    }

    private void setValue(String content, Class<? super T> type, int column) {
        for (Field field : type.getDeclaredFields()) {
            ExcelRow excelRow = field.getAnnotation(ExcelRow.class);
            if (excelRow != null) {
                Object o = casting.castValue(field.getType(), valueOf(internalCount), options);
                setFieldData(field, o);
            }
            ExcelCell index = field.getAnnotation(ExcelCell.class);
            if (index != null) {
                Class<?> fieldType = field.getType();

                if (column == index.value()) {
                    Object o = casting.castValue(fieldType, content, options);

                    setFieldData(field, o);
                }

            } else {

                ExcelCellName excelCellName = field.getAnnotation(ExcelCellName.class);
                if (excelCellName != null) {
                    Class<?> fieldType = field.getType();
                    Integer titleColumn = titles.get(excelCellName.value());
                    if (titleColumn != null && column == titleColumn) {

                        Object o = casting.castValue(fieldType, content, options);

                        setFieldData(field, o);
                    }
                }
            }
        }
    }

    private void setFieldData(Field field, Object o) {
        try {
            field.setAccessible(true);
            field.set(instance, o);
        } catch (IllegalAccessException e) {
            throw new IllegalCastException("Unexpected cast type {" + o + "} of field" + field.getName());
        }
    }

    @Override
    public void startRow(int rowNum) {
        if (rowNum + 1 > options.skip()) {
            instance = newInstanceOf(type);
        }
    }

    @Override
    public void endRow(int rowNum) {

        if (internalCount != rowNum)
            return;

        if (rowNum + 1 > options.skip()) {
            consumer.accept(instance);
        }
    }

    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {

        CellAddress cellAddress = new CellAddress(cellReference);
        int row = cellAddress.getRow();

        internalCount = row;
        int column = cellAddress.getColumn();

        if (row == 0) {
        	if(options.trimTagName()) {
                formattedValue = formattedValue.trim();
            }
        	
        	titles.put(formattedValue, column);
        }

        if (row + 1 <= options.skip()) {
            return;
        }

        setFieldValue(formattedValue, type, column);
    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
        //no-op
    }
}
