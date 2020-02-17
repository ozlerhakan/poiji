package com.poiji.save;

import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import static com.poiji.util.PoijiConstants.DATE_CELL_STYLE_INDEX_PROPERTY_NAME;
import static com.poiji.util.PoijiConstants.LOCAL_DATE_CELL_STYLE_INDEX_PROPERTY_NAME;
import static com.poiji.util.PoijiConstants.LOCAL_DATE_TIME_CELL_STYLE_INDEX_PROPERTY_NAME;

public final class XlsxFileSaver implements FileSaver {

    private final WorkbookSaver workbookSaver;
    private final PoijiOptions options;

    public XlsxFileSaver(final WorkbookSaver workbookSaver, final PoijiOptions options) {
        this.workbookSaver = workbookSaver;
        this.options = options;
    }

    @Override
    public <T> void save(final List<T> data) {
        try (final SXSSFWorkbook workbook = new SXSSFWorkbook()) {
            workbook.setCompressTempFiles(true);
            addStyles(workbook);
            try {
                workbookSaver.save(data, workbook);
            } finally {
                if (!workbook.dispose()) {
                    System.out.println("Warning! SXSSFWorkbook wasn't disposed correctly. See " + System.getProperty(
                        "java.io.tmpdir") + File.separator + "poifiles");
                }
            }
        } catch (IOException e) {
            throw new PoijiException(e.getMessage(), e);
        }

    }

    private void addStyles(final SXSSFWorkbook workbook) {
        final CellStyle dateCellStyle = workbook.createCellStyle();
        final DataFormat dataFormat = workbook.createDataFormat();
        dateCellStyle.setDataFormat(dataFormat.getFormat(options.datePattern()));
        final CellStyle localDateCellStyle = workbook.createCellStyle();
        localDateCellStyle.setDataFormat(dataFormat.getFormat(options.getLocalDatePattern()));
        final CellStyle localDateTimeCellStyle = workbook.createCellStyle();
        localDateTimeCellStyle.setDataFormat(dataFormat.getFormat(options.getLocalDateTimePattern()));
        final POIXMLProperties.CustomProperties customProperties = workbook
            .getXSSFWorkbook()
            .getProperties()
            .getCustomProperties();
        customProperties.addProperty(DATE_CELL_STYLE_INDEX_PROPERTY_NAME, dateCellStyle.getIndex());
        customProperties.addProperty(LOCAL_DATE_CELL_STYLE_INDEX_PROPERTY_NAME, localDateCellStyle.getIndex());
        customProperties.addProperty(LOCAL_DATE_TIME_CELL_STYLE_INDEX_PROPERTY_NAME, localDateTimeCellStyle.getIndex());
    }

}
