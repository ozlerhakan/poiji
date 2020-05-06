package com.poiji.bind.mapping;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelCellRange;
import com.poiji.annotation.ExcelRow;
import com.poiji.annotation.ExcelUnknownCells;
import com.poiji.config.Casting;
import com.poiji.exception.IllegalCastException;
import com.poiji.option.PoijiOptions;
import com.poiji.util.AnnotationUtil;
import com.poiji.util.ReflectUtil;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.lang.String.valueOf;

/**
 * This class handles the processing of a .xlsx file,
 * and generates a list of instances of a given type
 * <p>
 * Created by hakan on 22/10/2017
 */
final class PoijiHandler<T> implements SheetContentsHandler {
    private T instance;
    private Consumer<? super T> consumer;
    private int internalRow;
    private int internalCount;
    private int limit;
    private Class<T> type;
    private PoijiOptions options;
    private final Casting casting;
    private Map<String, Integer> columnIndexPerTitle;
    private Map<Integer, String> titlePerColumnIndex;
    // New maps used to speed up computing and handle inner objects
    private Map<String, Object> fieldInstances;
    private Map<Integer, Field> columnToField;
    private Map<Integer, Field> columnToSuperClassField;
    private Set<ExcelCellName> excelCellNames;

    PoijiHandler(Class<T> type, PoijiOptions options, Consumer<? super T> consumer) {
        this.type = type;
        this.options = options;
        this.consumer = consumer;
        this.limit = options.getLimit();
        casting = options.getCasting();
        columnIndexPerTitle = new HashMap<>();
        titlePerColumnIndex = new HashMap<>();
        columnToField = new HashMap<>();
        columnToSuperClassField = new HashMap<>();
        excelCellNames = new HashSet<>();
    }

    private void setFieldValue(String content, Class<? super T> subclass, int column) {
        if (subclass != Object.class) {
            if (setValue(content, subclass, column)) {
                return;
            }
            setFieldValue(content, subclass.getSuperclass(), column);
        }
    }

    /**
     * Using this to hold inner objects that will be mapped to the main object
     **/
    private Object getInstance(Field field) {
        Object ins = null;
        if (fieldInstances.containsKey(field.getName())) {
            ins = fieldInstances.get(field.getName());
        } else {
            ins = ReflectUtil.newInstanceOf(field.getType());
            fieldInstances.put(field.getName(), ins);
        }
        return ins;
    }

    private boolean setValue(String content, Class<? super T> type, int column) {
        Stream.of(type.getDeclaredFields())
                .filter(field -> field.getAnnotation(ExcelUnknownCells.class) == null)
                .forEach(field -> {
                    ExcelRow excelRow = field.getAnnotation(ExcelRow.class);
                    if (excelRow != null) {
                        Object o = casting.castValue(field.getType(), valueOf(internalRow), internalRow, column, options);
                        ReflectUtil.setFieldData(field, o, instance);
                        columnToField.put(-1, field);
                    }
                    ExcelCellRange range = field.getAnnotation(ExcelCellRange.class);
                    if (range != null) {
                        Object ins = null;
                        ins = getInstance(field);
                        for (Field f : field.getType().getDeclaredFields()) {
                            if (setValue(f, column, content, ins)) {
                                ReflectUtil.setFieldData(field, ins, instance);
                                columnToField.put(column, f);
                                columnToSuperClassField.put(column, field);
                            }
                        }
                    } else {
                        if (setValue(field, column, content, instance)) {
                            columnToField.put(column, field);
                        }
                    }
                });
        Stream.of(type.getDeclaredFields())
                .filter(field -> field.getAnnotation(ExcelUnknownCells.class) != null)
                .forEach(field -> {
                    if (!columnToField.containsKey(column)) {
                        try {
                            Map<String, String> excelUnknownCellsMap;
                            field.setAccessible(true);
                            if (field.get(instance) == null) {
                                excelUnknownCellsMap = new HashMap<>();
                                ReflectUtil.setFieldData(field, excelUnknownCellsMap, instance);
                            } else {
                                excelUnknownCellsMap = (Map) field.get(instance);
                            }
                            excelUnknownCellsMap.put(titlePerColumnIndex.get(column), content);
                        } catch (IllegalAccessException e) {
                            throw new IllegalCastException("Could not read content of field " + field.getName() + " on Object {" + instance + "}");
                        }
                    }
                });
        // For ExcelRow annotation
        if (columnToField.containsKey(-1)) {
            Field field = columnToField.get(-1);
            Object o = casting.castValue(field.getType(), valueOf(internalRow), internalRow, column, options);
            ReflectUtil.setFieldData(field, o, instance);
        }
        if (columnToField.containsKey(column) && columnToSuperClassField.containsKey(column)) {
            Field field = columnToField.get(column);
            Object ins;
            ins = getInstance(columnToSuperClassField.get(column));
            if (setValue(field, column, content, ins)) {
                ReflectUtil.setFieldData(columnToSuperClassField.get(column), ins, instance);
                return true;
            }
            return setValue(field, column, content, instance);
        }
        return false;
    }

    private boolean setValue(Field field, int column, String content, Object ins) {
        ExcelCell index = field.getAnnotation(ExcelCell.class);
        if (index != null) {
            Class<?> fieldType = field.getType();
            if (column == index.value()) {
                Object o = casting.castValue(fieldType, content, internalRow, column, options);
                ReflectUtil.setFieldData(field, o, ins);
                return true;
            }
        } else {
            ExcelCellName excelCellName = field.getAnnotation(ExcelCellName.class);
            if (excelCellName != null) {
                excelCellNames.add(excelCellName);
                Class<?> fieldType = field.getType();
                final String titleName = options.getCaseInsensitive()
                        ? excelCellName.value().toLowerCase()
                        : excelCellName.value();
                final Integer titleColumn = columnIndexPerTitle.get(titleName);
                //Fix both columns mapped to name passing this condition below
                if (titleColumn != null && titleColumn == column) {
                    Object o = casting.castValue(fieldType, content, internalRow, column, options);
                    ReflectUtil.setFieldData(field, o, ins);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void startRow(int rowNum) {
        if (rowNum + 1 > options.skip()) {
            internalCount += 1;
            instance = ReflectUtil.newInstanceOf(type);
            fieldInstances = new HashMap<>();
        }
    }

    @Override
    public void endRow(int rowNum) {
        if (internalRow != rowNum)
            return;

        if (rowNum + 1 > options.skip()) {
            consumer.accept(instance);
        }
    }

    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        CellAddress cellAddress = new CellAddress(cellReference);
        int row = cellAddress.getRow();
        int headers = options.getHeaderStart();
        int column = cellAddress.getColumn();
        if (row <= headers) {
            columnIndexPerTitle.put(
                    options.getCaseInsensitive() ? formattedValue.toLowerCase() : formattedValue,
                    column
            );
            titlePerColumnIndex.put(column, getTitleNameForMap(formattedValue, column));
        }
        if (row + 1 <= options.skip()) {
            return;
        }
        if (limit != 0 && internalCount > limit) {
            return;
        }
        internalRow = row;
        setFieldValue(formattedValue, type, column);
    }

    private String getTitleNameForMap(String cellContent, int columnIndex) {
        String titleName;
        if (titlePerColumnIndex.containsValue(cellContent)
                || cellContent.isEmpty()) {
            titleName = cellContent + "@" + columnIndex;
        } else {
            titleName = cellContent;
        }
        return titleName;
    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
        //no-op
    }

    @Override
    public void endSheet() {
        AnnotationUtil.validateMandatoryNameColumns(options, type, columnIndexPerTitle.keySet());
    }
}
