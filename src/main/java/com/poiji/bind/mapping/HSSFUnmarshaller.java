package com.poiji.bind.mapping;

import com.poiji.annotation.DisableCellFormatXLS;
import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelCellRange;
import com.poiji.annotation.ExcelRow;
import com.poiji.annotation.ExcelUnknownCells;
import com.poiji.bind.Unmarshaller;
import com.poiji.config.Casting;
import com.poiji.exception.IllegalCastException;
import com.poiji.option.PoijiOptions;
import com.poiji.util.AnnotationUtil;
import com.poiji.util.ReflectUtil;
import com.poiji.util.Strings;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.BaseFormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.String.valueOf;

/**
 * This is the main class that converts the excel sheet fromExcel Java object
 * Created by hakan on 16/01/2017.
 */
abstract class HSSFUnmarshaller extends PoijiWorkBook implements Unmarshaller {

    private final DataFormatter dataFormatter;
    protected final PoijiOptions options;
    private final Casting casting;
    final Map<String, Integer> columnIndexPerTitle;
    private final Map<Integer, String> titlePerColumnIndex;
    private final Map<Integer, String> caseSensitiveTitlePerColumnIndex;
    final int limit;
    BaseFormulaEvaluator baseFormulaEvaluator;
    int internalCount;

    HSSFUnmarshaller(PoijiOptions options) {
        this.options = options;
        this.limit = options.getLimit();
        dataFormatter = new DataFormatter();
        columnIndexPerTitle = new HashMap<>();
        titlePerColumnIndex = new HashMap<>();
        caseSensitiveTitlePerColumnIndex = new HashMap<>();
        casting = options.getCasting();
    }

    @Override
    public <T> void unmarshal(Class<T> type, Consumer<? super T> consumer) {
        HSSFWorkbook workbook = (HSSFWorkbook) workbook();
        Optional<String> maybeSheetName = this.getSheetName(type, options);

        baseFormulaEvaluator = HSSFFormulaEvaluator.create(workbook, null, null);
        Sheet sheet = this.getSheetToProcess(workbook, options, maybeSheetName.orElse(null));

        processRowsToObjects(sheet, type, consumer);
    }

    <T> void processRowsToObjects(Sheet sheet, Class<T> type, Consumer<? super T> consumer) {
        int skip = options.skip();
        int maxPhysicalNumberOfRows = sheet.getPhysicalNumberOfRows() + 1 - skip;

        loadColumnTitles(sheet, maxPhysicalNumberOfRows);
        AnnotationUtil.validateMandatoryNameColumns(options, type, columnIndexPerTitle.keySet());

        for (Row currentRow : sheet) {
            if (!skip(currentRow, skip) && !isRowEmpty(currentRow)) {
                internalCount += 1;

                if (limit != 0 && internalCount > limit)
                    return;

                T instance = deserializeRowToInstance(currentRow, type);
                consumer.accept(instance);
            }
        }
    }


    private Sheet getSheetToProcess(Workbook workbook, PoijiOptions options, String sheetName) {
        int nonHiddenSheetIndex = 0;
        int requestedIndex = options.sheetIndex();
        Sheet sheet = null;
        if (options.ignoreHiddenSheets()) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                if (!workbook.isSheetHidden(i) && !workbook.isSheetVeryHidden(i)) {
                    if (sheetName == null) {
                        if (nonHiddenSheetIndex == requestedIndex) {
                            return workbook.getSheetAt(i);
                        }
                    } else {
                        if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
                            return workbook.getSheetAt(i);
                        }
                    }
                    nonHiddenSheetIndex++;
                }
            }
        } else {
            if (sheetName == null) {
                sheet = workbook.getSheetAt(requestedIndex);
            } else {
                sheet = workbook.getSheet(sheetName);
            }
        }
        return sheet;
    }

    void loadColumnTitles(Sheet sheet, int maxPhysicalNumberOfRows) {
        if (maxPhysicalNumberOfRows > 0) {
            int row = options.getHeaderStart();
            if (row == -1) {
                return;
            }
            Row firstRow = sheet.getRow(row);
            for (Cell cell : firstRow) {
                final int columnIndex = cell.getColumnIndex();
                caseSensitiveTitlePerColumnIndex.put(columnIndex, getTitleNameForMap(cell.getStringCellValue(), columnIndex));
                final String titleName = Strings.getTitleName(options, cell.getStringCellValue());
                columnIndexPerTitle.put(titleName, columnIndex);
                titlePerColumnIndex.put(columnIndex, getTitleNameForMap(titleName, columnIndex));
            }
        }
    }

    private String getTitleNameForMap(String cellContent, int columnIndex) {
        String titleName;
        if (options.getIgnoreWhitespaces()) {
            cellContent = cellContent.trim();
        }
        if (titlePerColumnIndex.containsValue(cellContent)
                || cellContent.isEmpty()) {
            titleName = cellContent + "@" + columnIndex;
        } else {
            titleName = cellContent;
        }
        return titleName;
    }

    <T> T deserializeRowToInstance(Row currentRow, Class<T> type) {
        T instance = ReflectUtil.newInstanceOf(type);
        return setFieldValuesFromRowIntoInstance(currentRow, type, instance);
    }

    private <T> T tailSetFieldValue(Row currentRow, Class<? super T> type, T instance) {
        List<Integer> mappedColumnIndices = new ArrayList<>();
        List<Field> unknownCells = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            if (field.getAnnotation(ExcelRow.class) != null) {
                final int rowNum = currentRow.getRowNum();
                final Object data = casting.castValue(field, valueOf(rowNum), rowNum, -1, options);
                setFieldData(instance, field, data);
            } else if (field.getAnnotation(ExcelCellRange.class) != null) {

                Class<?> fieldType = field.getType();
                Object fieldInstance = ReflectUtil.newInstanceOf(fieldType);
                for (Field fieldField : fieldType.getDeclaredFields()) {
                    mappedColumnIndices.add(tailSetFieldValue(currentRow, fieldInstance, fieldField));
                }
                setFieldData(instance, field, fieldInstance);
            } else if (field.getAnnotation(ExcelUnknownCells.class) != null) {
                unknownCells.add(field);
            } else {
                mappedColumnIndices.add(tailSetFieldValue(currentRow, instance, field));
            }
        }

        Map<String, String> excelUnknownCellsMap = StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(currentRow.cellIterator(), Spliterator.ORDERED), false)
                .filter(cell -> caseSensitiveTitlePerColumnIndex.size() != 0)
                .filter(cell -> !mappedColumnIndices.contains(cell.getColumnIndex()))
                .filter(cell -> !cell.toString().isEmpty())
                .collect(Collectors.toMap(
                        cell -> caseSensitiveTitlePerColumnIndex.get(cell.getColumnIndex()),
                        Object::toString
                ));

        unknownCells.forEach(field -> setFieldData(instance, field, excelUnknownCellsMap));

        return instance;
    }

    private <T> Integer tailSetFieldValue(Row currentRow, T instance, Field field) {
        final FieldAnnotationDetail annotationDetail = getFieldColumn(field);
        if (annotationDetail.getColumn() != null) {
            constructTypeValue(currentRow, instance, field, annotationDetail);
        }
        return annotationDetail.getColumn();
    }

    private FieldAnnotationDetail getFieldColumn(final Field field) {
        ExcelCell index = field.getAnnotation(ExcelCell.class);
        DisableCellFormatXLS disableCellFormat = field.getAnnotation(DisableCellFormatXLS.class);
        final FieldAnnotationDetail annotationDetail = new FieldAnnotationDetail();

        if (disableCellFormat != null) {
            annotationDetail.setDisabledCellFormat(disableCellFormat.value());
        }

        if (index != null) {
            annotationDetail.setColumn(index.value());
        } else {
            ExcelCellName excelCellName = field.getAnnotation(ExcelCellName.class);
            if (excelCellName != null) {
                final String titleName = Strings.getTitleName(options, excelCellName.value());
                Integer column = columnIndexPerTitle.get(titleName);
                annotationDetail.setColumn(column);
            }
        }
        return annotationDetail;
    }

    private <T> void constructTypeValue(Row currentRow, T instance, Field field, FieldAnnotationDetail annotationDetail) {
        Cell cell = currentRow.getCell(annotationDetail.getColumn());

        if (cell != null) {
            if (annotationDetail.isDisabledCellFormat()) {
                cell.setCellStyle(null);
            }
            String value = dataFormatter.formatCellValue(cell, baseFormulaEvaluator);
            Object data = casting.castValue(field, value, currentRow.getRowNum(), annotationDetail.getColumn(), options);
            setFieldData(instance, field, data);
        }
    }

    private <T> void setFieldData(T instance, Field field, Object data) {
        try {
            field.setAccessible(true);
            field.set(instance, data);
        } catch (IllegalAccessException e) {
            throw new IllegalCastException("Unexpected cast type {" + data + "} of field" + field.getName());
        }
    }

    private <T> T setFieldValuesFromRowIntoInstance(Row currentRow, Class<? super T> subclass, T instance) {
        return subclass == null
                ? instance
                : tailSetFieldValue(currentRow, subclass, setFieldValuesFromRowIntoInstance(currentRow, subclass.getSuperclass(), instance));
    }

    boolean skip(final Row currentRow, int skip) {
        return currentRow.getRowNum() + 1 <= skip;
    }

    boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    private static class FieldAnnotationDetail {
        private Integer column;
        private boolean disabledCellFormat;

        Integer getColumn() {
            return column;
        }

        boolean isDisabledCellFormat() {
            return disabledCellFormat;
        }

        void setColumn(Integer column) {
            this.column = column;
        }

        void setDisabledCellFormat(boolean disabledCellFormat) {
            this.disabledCellFormat = disabledCellFormat;
        }
    }

}
