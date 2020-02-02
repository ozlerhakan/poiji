package com.poiji.util;

import java.time.format.DateTimeFormatter;

/**
 * Created by hakan on 15/10/2017
 */
public final class PoijiConstants {

    public static final String DEFAULT_DATE_PATTERN = "dd/M/yyyy";
    public static final String DEFAULT_DATE_TIME_PATTERN = "dd/M/yyyy HH:mm:ss";
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);
    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN);
    public static final String XLS_EXTENSION = ".xls";
    public static final String XLSX_EXTENSION = ".xlsx";
    public static final String DATE_TIME_CELL_STYLE_INDEX_PROPERTY_NAME = "DateTimeCellStyleIndex";
    public static final String DATE_CELL_STYLE_INDEX_PROPERTY_NAME = "DateCellStyleIndex";

    private PoijiConstants() {
    }

}
