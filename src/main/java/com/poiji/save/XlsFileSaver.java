package com.poiji.save;

import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Stream;
import org.apache.poi.hpsf.CustomProperties;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;

import static com.poiji.util.PoijiConstants.DATE_CELL_STYLE_INDEX_PROPERTY_NAME;
import static com.poiji.util.PoijiConstants.LOCAL_DATE_CELL_STYLE_INDEX_PROPERTY_NAME;
import static com.poiji.util.PoijiConstants.LOCAL_DATE_TIME_CELL_STYLE_INDEX_PROPERTY_NAME;

public final class XlsFileSaver implements FileSaver {

    private final WorkbookSaver workbookSaver;
    private final PoijiOptions options;

    public XlsFileSaver(final WorkbookSaver workbookSaver, final PoijiOptions options) {
        this.workbookSaver = workbookSaver;
        this.options = options;
    }

    @Override
    public <T> void save(final Collection<T> data) {
        try (final HSSFWorkbook workbook = new HSSFWorkbook()) {
            addStyles(workbook);
            workbookSaver.save(data, workbook);
        } catch (IOException e) {
            throw new PoijiException(e.getMessage(), e);
        }

    }

    @Override
    public <T> void save(final Stream<T> data) {
        try (final HSSFWorkbook workbook = new HSSFWorkbook()) {
            addStyles(workbook);
            workbookSaver.save(data, workbook);
        } catch (IOException e) {
            throw new PoijiException(e.getMessage(), e);
        }
    }

    private void addStyles(final HSSFWorkbook workbook) {
        final CellStyle dateCellStyle = workbook.createCellStyle();
        final DataFormat dataFormat = workbook.createDataFormat();
        dateCellStyle.setDataFormat(dataFormat.getFormat(options.datePattern()));
        final CellStyle localDateCellStyle = workbook.createCellStyle();
        localDateCellStyle.setDataFormat(dataFormat.getFormat(options.getLocalDatePattern()));
        final CellStyle localDateTimeCellStyle = workbook.createCellStyle();
        localDateTimeCellStyle.setDataFormat(dataFormat.getFormat(options.getLocalDateTimePattern()));
        workbook.createInformationProperties();
        final CustomProperties customProperties = new CustomProperties();
        customProperties.put(DATE_CELL_STYLE_INDEX_PROPERTY_NAME, dateCellStyle.getIndex());
        customProperties.put(LOCAL_DATE_CELL_STYLE_INDEX_PROPERTY_NAME, localDateCellStyle.getIndex());
        customProperties.put(LOCAL_DATE_TIME_CELL_STYLE_INDEX_PROPERTY_NAME, localDateTimeCellStyle.getIndex());
        workbook.getDocumentSummaryInformation().setCustomProperties(customProperties);
    }

}
