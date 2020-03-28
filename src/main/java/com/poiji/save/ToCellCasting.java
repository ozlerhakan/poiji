package com.poiji.save;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.poi.hpsf.CustomProperties;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import static com.poiji.util.PoijiConstants.DATE_CELL_STYLE_INDEX_PROPERTY_NAME;
import static com.poiji.util.PoijiConstants.LOCAL_DATE_CELL_STYLE_INDEX_PROPERTY_NAME;
import static com.poiji.util.PoijiConstants.LOCAL_DATE_TIME_CELL_STYLE_INDEX_PROPERTY_NAME;

/**
 * Rules to convert java objects into cell content.
 */
public final class ToCellCasting {

    private final Map<Class<?>, ToCellCastingRule<?>> consumers;

    public ToCellCasting() {
        consumers = new ConcurrentHashMap<>();
        consumers.put(Boolean.class, (Cell cell, Boolean fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(fieldValue);
            }
        });
        consumers.put(boolean.class, (ToCellCastingRule<Boolean>) Cell::setCellValue);
        consumers.put(Double.class, (Cell cell, Double fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(fieldValue);
            }
        });
        consumers.put(double.class, (ToCellCastingRule<Double>) Cell::setCellValue);
        consumers.put(Long.class, (Cell cell, Long fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(fieldValue);
            }
        });
        consumers.put(long.class, (ToCellCastingRule<Long>) Cell::setCellValue);
        consumers.put(Integer.class, (Cell cell, Integer fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(fieldValue);
            }
        });
        consumers.put(int.class, (ToCellCastingRule<Integer>) Cell::setCellValue);
        consumers.put(Byte.class, (Cell cell, Byte fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(fieldValue);
            }
        });
        consumers.put(byte.class, (ToCellCastingRule<Byte>) Cell::setCellValue);
        consumers.put(Short.class, (Cell cell, Short fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(fieldValue);
            }
        });
        consumers.put(short.class, (ToCellCastingRule<Short>) Cell::setCellValue);
        consumers.put(Float.class, (Cell cell, Float fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(fieldValue);
            }
        });
        consumers.put(float.class, (ToCellCastingRule<Float>) Cell::setCellValue);
        consumers.put(java.util.Date.class, (Cell cell, java.util.Date fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(fieldValue);
                setStyleInCell(cell, DATE_CELL_STYLE_INDEX_PROPERTY_NAME);
            }
        });
        consumers.put(java.sql.Date.class, (Cell cell, java.util.Date fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(fieldValue);
                setStyleInCell(cell, DATE_CELL_STYLE_INDEX_PROPERTY_NAME);
            }
        });
        consumers.put(Calendar.class, (Cell cell, Calendar fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(fieldValue);
                setStyleInCell(cell, LOCAL_DATE_TIME_CELL_STYLE_INDEX_PROPERTY_NAME);
            }
        });
        consumers.put(LocalDateTime.class, (Cell cell, LocalDateTime fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(java.sql.Timestamp.valueOf(fieldValue));
                setStyleInCell(cell, LOCAL_DATE_TIME_CELL_STYLE_INDEX_PROPERTY_NAME);
            }
        });
        consumers.put(LocalDate.class, (Cell cell, LocalDate fieldValue) -> {
            if (fieldValue != null) {
                cell.setCellValue(java.sql.Date.valueOf(fieldValue));
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

    ToCellCastingRule forType(final Class<?> type) {
        final ToCellCastingRule<?> rule = consumers.get(type);
        if (rule == null){
            return consumers.get(Object.class);
        } else {
            return rule;
        }
    }

    /**
     * Adds or replaces rule for custom java type.
     *
     * @param type java class type
     * @param castingRule casting rule
     * @return this object ready to add next casting rule
     */
    public ToCellCasting putToCellCastingRule(final Class<?> type, final ToCellCastingRule<?> castingRule) {
        consumers.put(type, castingRule);
        return this;
    }

}
