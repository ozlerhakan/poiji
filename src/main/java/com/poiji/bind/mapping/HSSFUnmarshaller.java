package com.poiji.bind.mapping;

import com.poiji.annotation.DisableCellFormatXLS;
import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelCellRange;
import com.poiji.annotation.ExcelRow;
import com.poiji.annotation.ExcelUnknownCells;
import com.poiji.annotation.ExcelCellsJoinedByName;
import com.poiji.bind.Unmarshaller;
import com.poiji.config.Casting;
import com.poiji.config.Formatting;
import com.poiji.exception.IllegalCastException;
import com.poiji.exception.PoijiMultiRowException;
import com.poiji.exception.PoijiMultiRowException.PoijiRowSpecificException;
import com.poiji.option.PoijiOptions;
import com.poiji.util.AnnotationUtil;
import com.poiji.util.ReflectUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.BaseFormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.String.valueOf;

/**
 * responsible for xls files
 * <p>
 * Created by hakan on 16/01/2017.
 */
abstract class HSSFUnmarshaller extends PoijiWorkBook implements Unmarshaller {

    private final DataFormatter dataFormatter;
    protected final PoijiOptions options;
    private final Casting casting;
    private final Formatting formatting;
    private final Map<String, Integer> titleToIndex;
    private final Map<Integer, String> indexToTitle;
    private final int limit;
    private int internalCount;
    BaseFormulaEvaluator baseFormulaEvaluator;

    HSSFUnmarshaller(PoijiOptions options) {
        this.options = options;
        this.limit = options.getLimit();
        dataFormatter = new DataFormatter();
        titleToIndex = new HashMap<>();
        indexToTitle = new HashMap<>();
        this.casting = options.getCasting();
        this.formatting = options.getFormatting();
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
        List<PoijiMultiRowException> errors = new ArrayList<>();

        loadColumnTitles(sheet, maxPhysicalNumberOfRows);
        AnnotationUtil.validateMandatoryNameColumns(options, formatting, type, titleToIndex, indexToTitle);

        for (Row currentRow : sheet) {
            if (!skip(currentRow, skip) && !isRowEmpty(currentRow)) {
                internalCount += 1;

                if (limit != 0 && internalCount > limit)
                    return;
                try {
                    T instance = deserializeRowToInstance(currentRow, type);
                    consumer.accept(instance);
                } catch (PoijiMultiRowException poijiRowException) {
                    errors.add(poijiRowException);
                }
            }
        }
        if (!errors.isEmpty()) {
            List<PoijiRowSpecificException> allErrors = errors.stream()
                    .flatMap((PoijiMultiRowException e) -> e.getErrors().stream())
                    .collect(Collectors.toList());
            throw new PoijiMultiRowException("Problem(s) occurred while reading data", allErrors);
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

    private void loadColumnTitles(Sheet sheet, int maxPhysicalNumberOfRows) {
        if (maxPhysicalNumberOfRows > 0) {
            int row = options.getHeaderStart();
            int headerCount = options.getHeaderCount();
            if (headerCount == 0) {
                return;
            }

            for (short i = 0; i < headerCount; i++) {
                Row firstRow = sheet.getRow(row + i);
                for (Cell cell : firstRow) {
                    final int columnIndex = cell.getColumnIndex();
                    final String titleName = formatting.transform(options, cell.getStringCellValue());
                    indexToTitle.put(columnIndex, getTitleNameForMap(titleName, columnIndex));
                    titleToIndex.put(titleName, columnIndex);
                }
            }
        }
    }

    private String getTitleNameForMap(String cellContent, int columnIndex) {
        if (indexToTitle.containsValue(cellContent)
                || cellContent.isEmpty()) {
            return cellContent + "@" + columnIndex;
        } else {
            return cellContent;
        }
    }

    <T> T deserializeRowToInstance(Row currentRow, Class<T> type) {
        T instance = ReflectUtil.newInstanceOf(type);
        return setFieldValuesFromRowIntoInstance(currentRow, type, instance);
    }

    private <T> T tailSetFieldValue(Row currentRow, Class<? super T> type, T instance) {
        List<Integer> mappedColumnIndices = new ArrayList<>();
        List<Field> unknownCells = new ArrayList<>();
        List<PoijiRowSpecificException> errors = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            if (field.getModifiers() == 25) {
                continue;
            }
            if (field.getAnnotation(ExcelRow.class) != null) {
                final int rowNum = currentRow.getRowNum();
                final Object data = casting.castValue(field, valueOf(rowNum), rowNum, -1, options);
                setFieldData(instance, field, data);
            } else if (field.getAnnotation(ExcelCellRange.class) != null) {

                Class<?> fieldType = field.getType();
                Object fieldInstance = ReflectUtil.newInstanceOf(fieldType);
                for (Field fieldField : fieldType.getDeclaredFields()) {
                    mapColumns(currentRow, fieldInstance, mappedColumnIndices, errors, fieldField);
                }
                setFieldData(instance, field, fieldInstance);
            } else if (field.getAnnotation(ExcelUnknownCells.class) != null) {
                unknownCells.add(field);
            } else {
                mapColumns(currentRow, instance, mappedColumnIndices, errors, field);
            }
        }
        if (!errors.isEmpty()) {
            throw new PoijiMultiRowException("Problem(s) occurred while reading data", errors);
        }

        if (unknownCells.isEmpty()) {
            return instance;
        }

        if (!indexToTitle.isEmpty()) {
            Map<String, String> excelUnknownCellsMap = StreamSupport
                    .stream(Spliterators.spliteratorUnknownSize(currentRow.cellIterator(), Spliterator.ORDERED), false)
                    .filter(cell -> !mappedColumnIndices.contains(cell.getColumnIndex()))
                    .collect(Collectors.toMap(
                            cell -> indexToTitle.get(cell.getColumnIndex()),
                            Object::toString));
            unknownCells.forEach(field -> setFieldData(instance, field, excelUnknownCellsMap));
        } else {
            Map<String, String> excelUnknownCellsMap = StreamSupport
                    .stream(Spliterators.spliteratorUnknownSize(currentRow.cellIterator(), Spliterator.ORDERED), false)
                    .filter(cell -> !mappedColumnIndices.contains(cell.getColumnIndex()))
                    .collect(Collectors.toMap(
                            cell -> valueOf(cell.getColumnIndex()),
                            Object::toString));
            unknownCells.forEach(field -> setFieldData(instance, field, excelUnknownCellsMap));
        }

        return instance;
    }

    private <T> void mapColumns(
            Row currentRow,
            T instance,
            List<Integer> mappedColumnIndices,
            List<PoijiRowSpecificException> errors,
            Field field) {
        try {
            mappedColumnIndices.add(tailSetFieldValue(currentRow, instance, field));
        } catch (PoijiRowSpecificException poijiRowException) {
            errors.add(poijiRowException);
        }
    }

    private <T> Integer tailSetFieldValue(Row currentRow, T instance, Field field) {
        final FieldAnnotationDetail annotationDetail = getFieldColumn(field);
        if (annotationDetail.getColumn() != null) {
            constructTypeValue(currentRow, instance, field, annotationDetail);
        }

        if (CollectionUtils.isNotEmpty(annotationDetail.getColumns())) {
            for (Integer column : annotationDetail.getColumns()) {
                annotationDetail.setColumn(column);
                constructTypeValue(currentRow, instance, field, annotationDetail);
            }
        }

        return annotationDetail.getColumn();
    }

    private FieldAnnotationDetail getFieldColumn(final Field field) {
        DisableCellFormatXLS disableCellFormat = field.getAnnotation(DisableCellFormatXLS.class);
        final FieldAnnotationDetail annotationDetail = new FieldAnnotationDetail();

        if (disableCellFormat != null) {
            annotationDetail.setDisabledCellFormat(disableCellFormat.value());
        }

        ExcelCell index = field.getAnnotation(ExcelCell.class);
        if (index != null) {
            annotationDetail.setColumn(index.value());
            annotationDetail.setMandatoryCell(index.mandatoryCell());
        }

        ExcelCellName excelCellName = field.getAnnotation(ExcelCellName.class);
        if (excelCellName != null) {
            annotationDetail.setMandatoryCell(excelCellName.mandatoryCell());
            annotationDetail.setColumnName(excelCellName.value());
            Integer column = findTitleColumn(excelCellName);
            annotationDetail.setColumn(column);
        }

        ExcelCellsJoinedByName excelCellsJoinedByName = field.getAnnotation(ExcelCellsJoinedByName.class);
        if (excelCellsJoinedByName != null) {
            String expression = excelCellsJoinedByName.expression();
            Pattern pattern = Pattern.compile(expression);

            List<Integer> columns = indexToTitle.entrySet().stream()
                    .filter(entry -> pattern.matcher(
                            entry.getValue().replaceAll("@[0-9]+", ""))
                            .matches())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            annotationDetail.setColumns(columns);
            annotationDetail.setMultiValueMap(CollectionUtils.isNotEmpty(columns));
        }

        return annotationDetail;
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

    private <T> void constructTypeValue(Row currentRow, T instance, Field field,
            FieldAnnotationDetail annotationDetail) {
        Cell cell = currentRow.getCell(annotationDetail.getColumn());

        if (cell != null && cell.getCellType() != CellType.BLANK) {
            if (annotationDetail.isDisabledCellFormat()) {
                cell.setCellStyle(null);
            }
            String value;
            if (options.isRawData() && isCellNumeric(cell)) {
                value = NumberToTextConverter.toText(cell.getNumericCellValue());
            } else {
                value = dataFormatter.formatCellValue(cell, baseFormulaEvaluator);
            }
            Object data = casting.castValue(field, value, currentRow.getRowNum(), annotationDetail.getColumn(),
                    options);

            if (!annotationDetail.isMultiValueMap()) {
                setFieldData(instance, field, data);
            } else {
                String titleColumn = indexToTitle.get(annotationDetail.getColumn());
                titleColumn = titleColumn.replaceAll("@[0-9]+", "");
                putFieldMultiValueMapData(instance, field, titleColumn, data);
            }
        } else if (annotationDetail.isMandatoryCell()) {
            throw new PoijiRowSpecificException(annotationDetail.getColumnName(), field.getName(),
                    currentRow.getRowNum());
        }
    }

    private boolean isCellNumeric(Cell cell) {
        return (cell.getCellType() == CellType.NUMERIC ||
                (cell.getCellType() == CellType.FORMULA &&
                        cell.getCachedFormulaResultType() == CellType.NUMERIC));
    }

    private <T> void setFieldData(T instance, Field field, Object data) {
        try {
            field.setAccessible(true);
            field.set(instance, data);
        } catch (IllegalAccessException e) {
            throw new IllegalCastException("Unexpected cast type {" + data + "} of field" + field.getName());
        }
    }

    public void putFieldMultiValueMapData(Object instance, Field field, String columnName, Object o) {
        try {
            field.setAccessible(true);
            MultiValuedMap<String, Object> multiValuedMap = (MultiValuedMap<String, Object>) field.get(instance);
            multiValuedMap.put(columnName, o);
        } catch (ClassCastException | IllegalAccessException e) {
            throw new IllegalCastException("Unexpected cast type {" + o + "} of field" + field.getName());
        }
    }

    private <T> T setFieldValuesFromRowIntoInstance(Row currentRow, Class<? super T> subclass, T instance) {
        return subclass == null
                ? instance
                : tailSetFieldValue(currentRow, subclass,
                        setFieldValuesFromRowIntoInstance(currentRow, subclass.getSuperclass(), instance));
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
        private String columnName;
        private boolean disabledCellFormat;
        private boolean mandatoryCell;

        private List<Integer> columns;

        private boolean multiValueMap;

        Integer getColumn() {
            return column;
        }

        void setColumn(Integer column) {
            this.column = column;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        boolean isDisabledCellFormat() {
            return disabledCellFormat;
        }

        void setDisabledCellFormat(boolean disabledCellFormat) {
            this.disabledCellFormat = disabledCellFormat;
        }

        public boolean isMandatoryCell() {
            return mandatoryCell;
        }

        public void setMandatoryCell(boolean mandatoryCell) {
            this.mandatoryCell = mandatoryCell;
        }

        public List<Integer> getColumns() {
            return columns;
        }

        public void setColumns(List<Integer> columns) {
            this.columns = columns;
        }

        public boolean isMultiValueMap() {
            return multiValueMap;
        }

        public void setMultiValueMap(boolean multiValueMap) {
            this.multiValueMap = multiValueMap;
        }
    }

}
