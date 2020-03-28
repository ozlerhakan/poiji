package com.poiji.save;

import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractWorkbookSaver {

    private final PoijiOptions options;
    private final MappedFields mappedFields;

    public AbstractWorkbookSaver(
        final MappedFields mappedFields, final PoijiOptions options
    ) {
        this.mappedFields = mappedFields;
        this.options = options;
    }

    protected <T> void save(final Stream<T> data, final Workbook workbook, final OutputStream outputStream) {
        try {
            final Sheet sheet = prepareSheet(workbook);

            final int[] rowIndex = {1};
            data.sequential().forEach(instance -> {
                final Row row = sheet.createRow(rowIndex[0]++);
                try {
                    setValuesFromKnownFields(row, instance);
                } catch (IllegalAccessException e) {
                    throw new PoijiException(e.getMessage(), e);
                }
            });
            write(workbook, outputStream);
        } catch (IOException e) {
            throw new PoijiException(e.getMessage(), e);
        }
    }

    protected <T> void save(final Collection<T> data, final Workbook workbook, final OutputStream outputStream) {
        try {
            addUnknownColumnNamesFromData(data);
            final Sheet sheet = prepareSheet(workbook);

            int rowIndex = 1;
            for (final T instance : data) {
                final Row row = sheet.createRow(rowIndex++);
                setValuesFromKnownFields(row, instance);
                setValuesFromUnknownCellsMap(row, instance);
            }
            write(workbook, outputStream);
        } catch (IllegalAccessException | IOException e) {
            throw new PoijiException(e.getMessage(), e);
        }
    }

    private  <T> void addUnknownColumnNamesFromData(final Collection<T> data) {
        mappedFields.addUnknownColumnNamesFromData(data);
    }

    private void write(final Workbook workbook, final OutputStream outputStream) throws IOException {
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        workbook.close();
    }

    private Sheet prepareSheet(final Workbook workbook) {
        final Sheet sheet = mappedFields.getSheetName() == null
            ? workbook.createSheet()
            : workbook.createSheet(mappedFields.getSheetName());
        createColumnNames(sheet);
        return sheet;
    }

    private <T> void setValuesFromKnownFields(final Row row, final T instance) throws IllegalAccessException {
        final ToCellCasting toCellCasting = options.getToCellCasting();
        for (Map.Entry<Field, Integer> orders : mappedFields.getOrders().entrySet()) {
            final Cell cell = row.createCell(orders.getValue());
            final Field field = orders.getKey();
            final Class<?> type = field.getType();
            if (type.isPrimitive()){
                toCellCasting.forType(type).accept(cell, field.get(instance));
            } else {
                toCellCasting.forType(type).accept(cell, type.cast(field.get(instance)));
            }
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
