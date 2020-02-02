package com.poiji.save;

import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.poi.hpsf.CustomProperties;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;

import static com.poiji.util.PoijiConstants.DATE_CELL_STYLE_INDEX_PROPERTY_NAME;
import static com.poiji.util.PoijiConstants.DATE_TIME_CELL_STYLE_INDEX_PROPERTY_NAME;

public final class XlsFileSaver extends FileWorkbookSaver implements FileSaver {

    public XlsFileSaver(
        final File file, final MappedFields mappedFields, final PoijiOptions options
    ) {
        super(file, mappedFields, options);
    }

    @Override
    public <T> void save(final List<T> data) {
        try (final HSSFWorkbook workbook = new HSSFWorkbook()) {
            addStyles(workbook);
            save(data, workbook);
        } catch (IOException e) {
            throw new PoijiException(e.getMessage(), e);
        }

    }

    private void addStyles(final HSSFWorkbook workbook) {
        final CellStyle dateCellStyle = workbook.createCellStyle();
        final DataFormat dataFormat = workbook.createDataFormat();
        dateCellStyle.setDataFormat(dataFormat.getFormat(options.datePattern()));
        final CellStyle dateTimeCellStyle = workbook.createCellStyle();
        dateTimeCellStyle.setDataFormat(dataFormat.getFormat(options.getDateTimePattern()));
        workbook.createInformationProperties();
        final CustomProperties customProperties = new CustomProperties();
        customProperties.put(DATE_CELL_STYLE_INDEX_PROPERTY_NAME, dateCellStyle.getIndex());
        customProperties.put(DATE_TIME_CELL_STYLE_INDEX_PROPERTY_NAME, dateTimeCellStyle.getIndex());
        workbook.getDocumentSummaryInformation().setCustomProperties(customProperties);
    }

}
