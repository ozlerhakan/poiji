package com.poiji.bind.mapping;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelCellRange;
import com.poiji.annotation.ExcelRow;
import com.poiji.annotation.ExcelUnknownCells;
import com.poiji.bind.Unmarshaller;
import com.poiji.config.Casting;
import com.poiji.exception.IllegalCastException;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import com.poiji.util.ReflectUtil;
import java.io.IOException;
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
abstract class HSSFUnmarshaller implements Unmarshaller {

    private final DataFormatter dataFormatter;
    protected final PoijiOptions options;
    private final Casting casting;
    private final Map<String, Integer> columnIndexPerTitle;
    private final Map<Integer, String> titlePerColumnIndex;
    private final Map<Integer, String> caseSensitiveTitlePerColumnIndex;
    private final int limit;
    private int internalCount;

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
    public <T> void unmarshal(final Class<T> type, final Consumer<? super T> consumer) {
        try(final Workbook workbook = workbook()) {
            final Optional<String> maybeSheetName = SheetNameExtractor.getSheetName(type, options);

            final Sheet sheet = this.getSheetToProcess(workbook, options, maybeSheetName.orElse(null));

            final int skip = options.skip();
            final int maxPhysicalNumberOfRows = sheet.getPhysicalNumberOfRows() + 1 - skip;

            loadColumnTitles(sheet, maxPhysicalNumberOfRows);

            for (final Row currentRow : sheet) {
                if (!skip(currentRow, skip) && !isRowEmpty(currentRow)) {
                    internalCount += 1;

                    if (limit != 0 && internalCount > limit) return;

                    consumer.accept(deserializeRowToInstance(currentRow, type));
                }
            }
        } catch (final IOException e) {
            throw new PoijiException("Problem occurred while closing HSSFWorkbook", e);
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
            Row firstRow = sheet.getRow(row);
            for (Cell cell : firstRow) {
                final int columnIndex = cell.getColumnIndex();
                caseSensitiveTitlePerColumnIndex.put(columnIndex, getTitleNameForMap(cell.getStringCellValue(), columnIndex));
                final String titleName = options.getCaseInsensitive()
                    ? cell.getStringCellValue().toLowerCase()
                    : cell.getStringCellValue();
                columnIndexPerTitle.put(titleName, columnIndex);
                titlePerColumnIndex.put(columnIndex, getTitleNameForMap(titleName, columnIndex));
            }
        }
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

    private <T> T deserializeRowToInstance(Row currentRow, Class<T> type) {
        T instance = ReflectUtil.newInstanceOf(type);
        return setFieldValuesFromRowIntoInstance(currentRow, type, instance);
    }

    private <T> T tailSetFieldValue(Row currentRow, Class<? super T> type, T instance) {
        List<Integer> mappedColumnIndices = new ArrayList<>();
        List<Field> unknownCells = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            if (field.getAnnotation(ExcelRow.class) != null) {
                final int rowNum = currentRow.getRowNum();
                final Object data = casting.castValue(field.getType(), valueOf(rowNum), rowNum, -1, options);
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
        final Integer column = getFieldColumn(field);
        if (column != null) {
            constructTypeValue(currentRow, instance, field, column);
        }
        return column;
    }

    private Integer getFieldColumn(final Field field) {
        ExcelCell index = field.getAnnotation(ExcelCell.class);
        Integer column = null;
        if (index != null) {
            column = index.value();
        } else {
            ExcelCellName excelCellName = field.getAnnotation(ExcelCellName.class);
            if (excelCellName != null) {
                final String titleName = options.getCaseInsensitive()
                    ? excelCellName.value().toLowerCase()
                    : excelCellName.value();
                column = columnIndexPerTitle.get(titleName);
            }
        }
        return column;
    }

    private <T> void constructTypeValue(Row currentRow, T instance, Field field, int column) {
        Cell cell = currentRow.getCell(column);

        if (cell != null) {
            String value = dataFormatter.formatCellValue(cell);
            Object data = casting.castValue(field.getType(), value, currentRow.getRowNum(), column, options);
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

    private boolean skip(final Row currentRow, int skip) {
        return currentRow.getRowNum() + 1 <= skip;
    }

    private boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    protected abstract Workbook workbook();
}
