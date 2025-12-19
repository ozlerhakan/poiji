package com.poiji.bind.mapping;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelCellRange;
import com.poiji.annotation.ExcelRow;
import com.poiji.annotation.ExcelUnknownCells;
import com.poiji.annotation.ExcelCellsJoinedByName;
import com.poiji.config.Casting;
import com.poiji.config.Formatting;
import com.poiji.exception.IllegalCastException;
import com.poiji.option.PoijiOptions;
import com.poiji.util.AnnotationUtil;
import com.poiji.util.ReflectUtil;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;
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
    private final Consumer<? super T> consumer;
    private int internalRow;
    private int internalCount;
    private final int limit;
    private final Class<T> type;
    private final PoijiOptions options;
    private final Casting casting;
    private final Formatting formatting;
    private final Map<String, Integer> titleToIndex;
    private final Map<Integer, String> indexToTitle;
    // New maps used to speed up computing and handle inner objects
    private Map<String, Object> fieldInstances;
    private final Map<Integer, Field> columnToField;
    private final Map<Integer, Field> columnToSuperClassField;
    private final Set<ExcelCellName> excelCellNameAnnotations;
    // Record support
    private final boolean isRecord;
    private Map<String, Object> recordValues;
    private boolean rowHasCells;

    PoijiHandler(Class<T> type, PoijiOptions options, Consumer<? super T> consumer) {
        this.type = type;
        this.options = options;
        this.consumer = consumer;
        this.limit = options.getLimit();
        this.casting = options.getCasting();
        this.formatting = options.getFormatting();
        titleToIndex = new HashMap<>();
        indexToTitle = new HashMap<>();
        columnToField = new HashMap<>();
        columnToSuperClassField = new HashMap<>();
        excelCellNameAnnotations = new HashSet<>();
        this.isRecord = ReflectUtil.isRecord(type);
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
        Object ins;
        if (isRecord) {
            // For records, check recordValues for nested objects
            if (recordValues.containsKey(field.getName())) {
                ins = recordValues.get(field.getName());
            } else {
                ins = ReflectUtil.newInstanceOf(field.getType());
                recordValues.put(field.getName(), ins);
            }
        } else {
            if (fieldInstances.containsKey(field.getName())) {
                ins = fieldInstances.get(field.getName());
            } else {
                ins = ReflectUtil.newInstanceOf(field.getType());
                fieldInstances.put(field.getName(), ins);
            }
        }
        return ins;
    }

    @SuppressWarnings("unchecked")
    private boolean setValue(String content, Class<? super T> type, int column) {
        Stream.of(type.getDeclaredFields())
                .filter(field -> field.getAnnotation(ExcelUnknownCells.class) == null)
                .forEach(field -> {
                    ExcelRow excelRow = field.getAnnotation(ExcelRow.class);
                    if (excelRow != null) {
                        Object o = casting.castValue(field, valueOf(internalRow), internalRow, column, options);
                        if (isRecord) {
                            recordValues.put(field.getName(), o);
                        } else {
                            ReflectUtil.setFieldData(field, o, instance);
                        }
                        columnToField.put(-1, field);
                    }
                    ExcelCellRange range = field.getAnnotation(ExcelCellRange.class);
                    if (range != null) {
                        Object ins;
                        ins = getInstance(field);
                        for (Field f : field.getType().getDeclaredFields()) {
                            if (setValue(f, column, content, ins)) {
                                if (isRecord) {
                                    recordValues.put(field.getName(), ins);
                                } else {
                                    ReflectUtil.setFieldData(field, ins, instance);
                                }
                                columnToField.put(column, f);
                                columnToSuperClassField.put(column, field);
                            }
                        }
                    } else {
                        if (setValue(field, column, content, isRecord ? null : instance)) {
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
                            if (isRecord) {
                                // For records, we need to get or create the map from recordValues
                                if (recordValues.containsKey(field.getName())) {
                                    excelUnknownCellsMap = (Map<String, String>) recordValues.get(field.getName());
                                } else {
                                    excelUnknownCellsMap = new HashMap<>();
                                    recordValues.put(field.getName(), excelUnknownCellsMap);
                                }
                            } else {
                                field.setAccessible(true);
                                if (field.get(instance) == null) {
                                    excelUnknownCellsMap = new HashMap<>();
                                    ReflectUtil.setFieldData(field, excelUnknownCellsMap, instance);
                                } else {
                                    excelUnknownCellsMap = (Map<String, String>) field.get(instance);
                                }
                            }
                            String index = indexToTitle.get(column);
                            if (index == null) {
                                excelUnknownCellsMap.put(valueOf(column), content);
                            } else {
                                excelUnknownCellsMap.put(indexToTitle.get(column), content);
                            }
                        } catch (IllegalAccessException e) {
                            throw new IllegalCastException("Could not read content of field " + field.getName()
                                    + " on Object {" + instance + "}");
                        }
                    }
                });
        // For ExcelRow annotation
        if (columnToField.containsKey(-1)) {
            Field field = columnToField.get(-1);
            Object o = casting.castValue(field, valueOf(internalRow), internalRow, column, options);
            if (isRecord) {
                recordValues.put(field.getName(), o);
            } else {
                ReflectUtil.setFieldData(field, o, instance);
            }
        }
        if (columnToField.containsKey(column) && columnToSuperClassField.containsKey(column)) {
            Field field = columnToField.get(column);
            Object ins;
            ins = getInstance(columnToSuperClassField.get(column));
            if (setValue(field, column, content, ins)) {
                if (isRecord) {
                    recordValues.put(columnToSuperClassField.get(column).getName(), ins);
                } else {
                    ReflectUtil.setFieldData(columnToSuperClassField.get(column), ins, instance);
                }
                return true;
            }
            return setValue(field, column, content, isRecord ? null : instance);
        }
        return false;
    }

    private void setFieldValue(Field field, Object value, Object ins) {
        if (isRecord && ins == null) {
            // For records, store value in recordValues map
            recordValues.put(field.getName(), value);
        } else {
            // For regular classes, set field directly
            ReflectUtil.setFieldData(field, value, ins);
        }
    }

    private boolean setValue(Field field, int column, String content, Object ins) {
        ExcelCell index = field.getAnnotation(ExcelCell.class);

        if (index != null) {
            if (column == index.value()) {
                Object o = casting.castValue(field, content, internalRow, column, options);
                setFieldValue(field, o, ins);
                return true;
            }
        }

        ExcelCellName excelCellName = field.getAnnotation(ExcelCellName.class);
        if (excelCellName != null) {
            excelCellNameAnnotations.add(excelCellName);
            final Integer titleColumn = findTitleColumn(excelCellName);
            // Fix both columns mapped to name passing this condition below
            if (titleColumn != null && titleColumn == column) {
                Object o = casting.castValue(field, content, internalRow, column, options);
                setFieldValue(field, o, ins);
                return true;
            }
        }

        ExcelCellsJoinedByName excelCellsJoinedByName = field.getAnnotation(ExcelCellsJoinedByName.class);
        if (excelCellsJoinedByName != null) {
            String titleColumn = indexToTitle.get(column).replaceAll("@[0-9]+", "");

            String expression = excelCellsJoinedByName.expression();
            Pattern pattern = Pattern.compile(expression);
            if (pattern.matcher(titleColumn).matches()) {
                Object o = casting.castValue(field, content, internalRow, column, options);
                if (isRecord && ins == null) {
                    // For records, we need to collect values into a MultiValuedMap
                    MultiValuedMap<String, Object> fieldMap = (MultiValuedMap<String, Object>) recordValues.get(field.getName());
                    if (fieldMap == null) {
                        fieldMap = new org.apache.commons.collections4.multimap.ArrayListValuedHashMap<>();
                        recordValues.put(field.getName(), fieldMap);
                    }
                    fieldMap.put(titleColumn, o);
                } else {
                    ReflectUtil.putFieldMultiValueMapData(field, titleColumn, o, ins);
                }
                return true;
            }
        }

        return false;
    }

    public Integer findTitleColumn(ExcelCellName excelCellName) {
        if (!StringUtil.isBlank(excelCellName.value())) {
            final String titleName = formatting.transform(options, excelCellName.value());
            return titleToIndex.get(titleName);
        }

        if (!StringUtil.isBlank(excelCellName.expression())) {
            final String titleName = formatting.transform(options, excelCellName.expression());
            Pattern pattern = Pattern.compile(titleName);
            return titleToIndex.entrySet().stream()
                    .filter(entry -> pattern.matcher(entry.getKey()).matches())
                    .findFirst()
                    .map(Map.Entry::getValue)
                    .orElse(null);
        }

        return null;
    }

    @Override
    public void startRow(int rowNum) {
        if (rowNum + 1 > options.skip()) {
            internalCount += 1;
            rowHasCells = false;
            if (isRecord) {
                recordValues = new HashMap<>();
            } else {
                instance = ReflectUtil.newInstanceOf(type);
                fieldInstances = new HashMap<>();
            }
        }
    }

    @Override
    public void endRow(int rowNum) {
        if (rowNum + 1 <= options.skip())
            return;

        boolean processEmptyCell = options.isProcessEmptyCell();
        if (!rowHasCells && !processEmptyCell)
            return;

        if (isRecord) {
            instance = ReflectUtil.newRecordInstance(type, recordValues);
        }
        consumer.accept(instance);
    }

    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        if (cellReference == null) {
            // TODO hidden log required; a cell reference could return null
            return;
        }
        final CellAddress cellAddress = new CellAddress(cellReference);
        int row = cellAddress.getRow();
        int headerStart = options.getHeaderStart();
        int headerCount = options.getHeaderCount();
        int column = cellAddress.getColumn();
        if (row >= headerStart && row < headerStart + headerCount) {
            String transformedValue = formatting.transform(options, formattedValue);
            titleToIndex.put(transformedValue, column);
            indexToTitle.put(column, getTitleNameForMap(transformedValue, column));
        }
        if (row + 1 <= options.skip()) {
            return;
        }
        if (limit != 0 && internalCount > limit) {
            return;
        }
        internalRow = row;
        rowHasCells = true;
        setFieldValue(formattedValue, type, column);
    }

    private String getTitleNameForMap(String cellContent, int columnIndex) {
        if (indexToTitle.containsValue(cellContent)
                || cellContent.isEmpty()) {
            return cellContent + "@" + columnIndex;
        } else {
            return cellContent;
        }
    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
        // no-op
    }

    @Override
    public void endSheet() {
        AnnotationUtil.validateMandatoryNameColumns(options, formatting, type, titleToIndex, indexToTitle);
    }
}
