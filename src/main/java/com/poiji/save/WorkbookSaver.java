package com.poiji.save;

import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class WorkbookSaver {

    protected final PoijiOptions options;
    private final MappedFields mappedFields;
    private final CellDataCasting cellDataCasting;

    public WorkbookSaver(
        final MappedFields mappedFields, final PoijiOptions options
    ) {
        this.mappedFields = mappedFields;
        this.options = options;
        cellDataCasting = new CellDataCasting();
    }

    protected <T> void save(final List<T> data, final Workbook workbook, final OutputStream outputStream) {
        try {
            mappedFields.addUnknownColumnNamesFromData(data);
            final Sheet sheet = mappedFields.getSheetName() == null
                ? workbook.createSheet()
                : workbook.createSheet(mappedFields.getSheetName());
            createColumnNames(sheet);

            int rowIndex = 1;
            for (final T instance : data) {
                final Row row = sheet.createRow(rowIndex++);
                setValuesFromKnownFields(row, instance);
                setValuesFromUnknownCellsMap(row, instance);
            }
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workbook.close();
        } catch (IllegalAccessException | IOException e) {
            throw new PoijiException(e.getMessage(), e);
        }
    }

    private <T> void setValuesFromKnownFields(final Row row, final T instance) throws IllegalAccessException {
        for (Map.Entry<Field, Integer> orders : mappedFields.getOrders().entrySet()) {
            final Cell cell = row.createCell(orders.getValue());
            final Field field = orders.getKey();
            cellDataCasting.forType(field.getType()).accept(cell, field.get(instance));
        }
    }

    private <T> void setValuesFromUnknownCellsMap(final Row row, final T instance) throws IllegalAccessException {
        final Map<String, Integer> unknownOrders = mappedFields.getUnknownOrders();
        for (final Field unknownCell : mappedFields.getUnknownCells()) {
            final Map<String, String> unknownValues = (Map<String, String>) unknownCell.get(instance);
            if (unknownValues != null) {
                unknownValues.forEach((name, value) -> {
                    final Cell cell = row.createCell(unknownOrders.get(name));
                    cell.setCellValue(value);
                });
            }
        }
    }

    private void createColumnNames(final Sheet sheet) {
        final Row row = sheet.createRow(0);
        for (final Map.Entry<Field, Integer> entry : mappedFields.getOrders().entrySet()) {
            final Cell cell = row.createCell(entry.getValue());
            cell.setCellValue(mappedFields.getNames().get(entry.getKey()));
        }
        for (final Map.Entry<String, Integer> entry : mappedFields.getUnknownOrders().entrySet()) {
            final Cell cell = row.createCell(entry.getValue());
            cell.setCellValue(entry.getKey());
        }
    }
}
