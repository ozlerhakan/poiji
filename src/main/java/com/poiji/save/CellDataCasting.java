package com.poiji.save;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import org.apache.poi.hpsf.CustomProperties;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import static com.poiji.util.PoijiConstants.DATE_CELL_STYLE_INDEX_PROPERTY_NAME;
import static com.poiji.util.PoijiConstants.LOCAL_DATE_CELL_STYLE_INDEX_PROPERTY_NAME;
import static com.poiji.util.PoijiConstants.LOCAL_DATE_TIME_CELL_STYLE_INDEX_PROPERTY_NAME;

public final class CellDataCasting {

    private final Map<Class<?>, BiConsumer<Cell, Object>> consumers;

    public CellDataCasting() {
        consumers = new ConcurrentHashMap<>();
        consumers.put(Boolean.class, (Cell cell, Object fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue((boolean) fieldValue);
            }
        });
        consumers.put(boolean.class, (Cell cell, Object fieldValue) -> cell.setCellValue((boolean) fieldValue));
        consumers.put(Double.class, (Cell cell, Object fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue((double) fieldValue);
            }
        });
        consumers.put(double.class, (Cell cell, Object fieldValue) -> cell.setCellValue((double) fieldValue));
        consumers.put(Long.class, (Cell cell, Object fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue((long) fieldValue);
            }
        });
        consumers.put(long.class, (Cell cell, Object fieldValue) -> cell.setCellValue((long) fieldValue));
        consumers.put(Integer.class, (Cell cell, Object fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue((int) fieldValue);
            }
        });
        consumers.put(int.class, (Cell cell, Object fieldValue) -> cell.setCellValue((int) fieldValue));
        consumers.put(Byte.class, (Cell cell, Object fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue((byte) fieldValue);
            }
        });
        consumers.put(byte.class, (Cell cell, Object fieldValue) -> cell.setCellValue((byte) fieldValue));
        consumers.put(Short.class, (Cell cell, Object fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue((short) fieldValue);
            }
        });
        consumers.put(short.class, (Cell cell, Object fieldValue) -> cell.setCellValue((short) fieldValue));
        consumers.put(Float.class, (Cell cell, Object fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue((float) fieldValue);
            }
        });
        consumers.put(float.class, (Cell cell, Object fieldValue) -> cell.setCellValue((float) fieldValue));
        consumers.put(java.util.Date.class, (Cell cell, Object fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue((java.util.Date) fieldValue);
                setStyleInCell(cell, DATE_CELL_STYLE_INDEX_PROPERTY_NAME);
            }
        });
        consumers.put(java.sql.Date.class, (Cell cell, Object fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue((java.util.Date) fieldValue);
                setStyleInCell(cell, DATE_CELL_STYLE_INDEX_PROPERTY_NAME);
            }
        });
        consumers.put(Calendar.class, (Cell cell, Object fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue((Calendar) fieldValue);
                setStyleInCell(cell, LOCAL_DATE_TIME_CELL_STYLE_INDEX_PROPERTY_NAME);
            }
        });
        consumers.put(LocalDateTime.class, (Cell cell, Object fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(java.sql.Timestamp.valueOf((LocalDateTime) fieldValue));
                setStyleInCell(cell, LOCAL_DATE_TIME_CELL_STYLE_INDEX_PROPERTY_NAME);
            }
        });
        consumers.put(LocalDate.class, (Cell cell, Object fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(java.sql.Date.valueOf((LocalDate) fieldValue));
                setStyleInCell(cell, LOCAL_DATE_CELL_STYLE_INDEX_PROPERTY_NAME);
            }
        });
        consumers.put(Object.class, (Cell cell, Object fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(fieldValue.toString());
            }
        });
    }

    private void setStyleInCell(final Cell cell, final String propertyName) {
        final Workbook workbook = cell.getSheet().getWorkbook();
        if (workbook instanceof SXSSFWorkbook) {
            final POIXMLProperties.CustomProperties properties = ((SXSSFWorkbook) workbook)
                .getXSSFWorkbook()
                .getProperties()
                .getCustomProperties();
            final int dateTimeCellStyleIndex = properties.getProperty(propertyName).getI4();
            cell.setCellStyle(workbook.getCellStyleAt(dateTimeCellStyleIndex));
        } else if (workbook instanceof HSSFWorkbook) {
            final CustomProperties properties = ((HSSFWorkbook) workbook)
                .getDocumentSummaryInformation()
                .getCustomProperties();
            final Short dateTimeCellStyleIndex = (Short) properties.get(propertyName);
            cell.setCellStyle(workbook.getCellStyleAt(dateTimeCellStyleIndex));
        } else {
            throw new UnsupportedOperationException(workbook.getClass() + " is not supported");
        }
    }

    public BiConsumer<Cell, Object> forType(final Class<?> type) {
        return consumers.getOrDefault(type, consumers.get(Object.class));
    }

}
