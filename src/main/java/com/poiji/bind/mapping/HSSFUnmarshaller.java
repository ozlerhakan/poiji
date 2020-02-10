package com.poiji.bind.mapping;

import com.poiji.bind.Unmarshaller;
import com.poiji.option.PoijiOptions;
import com.poiji.util.ReflectUtil;
import java.util.Optional;
import java.util.function.Consumer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * This is the main class that converts the excel sheet fromExcel Java object
 * Created by hakan on 16/01/2017.
 */
abstract class HSSFUnmarshaller implements Unmarshaller {

    protected final PoijiOptions options;
    private final int limit;
    private int internalCount;

    HSSFUnmarshaller(PoijiOptions options) {
        this.options = options;
        this.limit = options.getLimit();
    }

    @Override
    public <T> void unmarshal(Class<T> type, Consumer<? super T> consumer) {
        Workbook workbook = workbook();
        Optional<String> maybeSheetName = SheetNameExtractor.getSheetName(type, options);

        Sheet sheet = this.getSheetToProcess(workbook, options, maybeSheetName.orElse(null));

        int skip = options.skip();
        int maxPhysicalNumberOfRows = sheet.getPhysicalNumberOfRows() + 1 - skip;

        final HSSFReadMappedFields readMappedFields = loadColumnTitles(sheet, maxPhysicalNumberOfRows, type);

        for (Row currentRow : sheet) {
            if (!skip(currentRow, skip) && !isRowEmpty(currentRow)) {
                internalCount += 1;

                if (limit != 0 && internalCount > limit)
                    return;

                T instance = readMappedFields.parseRow(currentRow, ReflectUtil.newInstanceOf(type));
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

    private HSSFReadMappedFields loadColumnTitles(Sheet sheet, int maxPhysicalNumberOfRows, final Class<?> type) {
        final HSSFReadMappedFields readMappedFields = new HSSFReadMappedFields(type, options).parseEntity();
        if (maxPhysicalNumberOfRows > 0) {
            readMappedFields.parseColumnNames(sheet.getRow(options.getHeaderStart()));
        }
        return readMappedFields;
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
