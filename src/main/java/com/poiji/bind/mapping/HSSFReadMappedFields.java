package com.poiji.bind.mapping;

import com.poiji.option.PoijiOptions;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

public final class HSSFReadMappedFields extends ReadMappedFields{

    private final DataFormatter dataFormatter;

    public HSSFReadMappedFields(final Class<?> entity, final PoijiOptions options) {
        super(entity, options);
        dataFormatter = new DataFormatter();
    }

    @Override
    public HSSFReadMappedFields parseEntity(){
        super.parseEntity();
        return this;
    }

    public <T> T parseRow(final Row row, final T instance) {
        setRowInInstance(row, instance);
        return instance;
    }

    private <T> void setRowInInstance(final Row row, final T instance) {
        for (short columnOrder = row.getFirstCellNum(); columnOrder < row.getLastCellNum(); columnOrder++) {
            setCellInInstance(row.getRowNum(), columnOrder, dataFormatter.formatCellValue(row.getCell(columnOrder)), instance);
        }
    }

    public void parseColumnNames(final Row row) {
        for (short columnOrder = row.getFirstCellNum(); columnOrder < row.getLastCellNum(); columnOrder++) {
            parseColumnName(columnOrder, row.getCell(columnOrder).getStringCellValue());
        }
    }

}
