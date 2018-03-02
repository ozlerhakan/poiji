package com.poiji.bind.mapping;

import com.poiji.annotation.ExcelCell;
import com.poiji.exception.IllegalCastException;
import com.poiji.option.PoijiOptions;
import com.poiji.util.Casting;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;

/**
 * This class handles the processing of a .xlsx file,
 * and generates a list of instances of a given type
 *
 * Created by hakan on 22/10/2017
 */
final class PoijiHandler<T> implements SheetContentsHandler {

    private T instance;
    private List<T> dataset;
    private int internalCount;

    private Class<T> type;
    private PoijiOptions options;

    private final Casting casting;

    PoijiHandler(Class<T> type, PoijiOptions options) {
        this.type = type;
        this.options = options;

        dataset = new ArrayList<>();
        casting = Casting.getInstance();
    }

    List<T> getDataset() {
        return dataset;
    }

    private <T> T newInstanceOf(Class<T> type) {
        T newInstance;
        try {
            newInstance = type.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException| InvocationTargetException | IllegalAccessException | InstantiationException e) {
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

            ExcelCell index = field.getAnnotation(ExcelCell.class);
            if (index != null) {
                Class<?> fieldType = field.getType();

                if (column == index.value()) {
                    Object o = casting.castValue(fieldType, content, options);

                    try {
                        field.setAccessible(true);
                        field.set(instance, o);
                    } catch (IllegalAccessException e) {
                        throw new IllegalCastException("Unexpected cast type {" + o + "} of field" + field.getName());
                    }
                }
            }
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
            dataset.add(instance);
        }
    }

    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {

        CellAddress cellAddress = new CellAddress(cellReference);
        int row = cellAddress.getRow();

        if (row + 1 <= options.skip()) {
            return;
        }

        internalCount = row;
        int column = cellAddress.getColumn();
        setFieldValue(formattedValue, type, column);
    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
        //no-op
    }
}
