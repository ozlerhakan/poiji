package com.poiji.option;

import java.time.format.DateTimeFormatter;

import static com.poiji.util.PoijiConstants.DEFAULT_DATE_PATTERN;
import static com.poiji.util.PoijiConstants.DEFAULT_DATE_TIME_FORMATTER;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by hakan on 17/01/2017.
 */
public final class PoijiOptions {

    private int skip;
    //ISSUE #55 changed form int to integer, in order to check if null when trying to work out which sheet to process in getSheetIndexToProcess below
    private Integer sheetIndex;
    private String password;
    private String datePattern;
    private boolean preferNullOverDefault;
    private DateTimeFormatter dateTimeFormatter;
    //ISSUE #55
    //whether or not to ignore any hidden sheets in the work book
    //default to false so not to break existing, and so must be explicitly set to true
    private boolean ignoreHiddenSheets;
    //if set to true will trim(remove leading and trailing) white spaces from cell value
    private boolean trimCellValue;
    //ISSUE #57
    //specify regex pattern for date converstion, if set and does not match date set to null
    private String dateRegex;
    //to set simple date format to Lenient if wanted
    private boolean dateLenient;

    private PoijiOptions() {
        super();
    }

    private PoijiOptions setSkip(int skip) {
        this.skip = skip;
        return this;
    }

    private PoijiOptions setDatePattern(String datePattern) {
        this.datePattern = datePattern;
        return this;
    }

    private PoijiOptions setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
        return this;
    }

    private PoijiOptions setPreferNullOverDefault(boolean preferNullOverDefault) {
        this.preferNullOverDefault = preferNullOverDefault;
        return this;
    }

    private PoijiOptions setSheetIndex(Integer sheetIndex) {
        this.sheetIndex = sheetIndex;
        return this;
    }

    public String getPassword() {
        return password;
    }

    private PoijiOptions setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer sheetIndex() {
        return sheetIndex;
    }

    public String datePattern() {
        return datePattern;
    }

    public DateTimeFormatter dateTimeFormatter() {
        return dateTimeFormatter;
    }

    public boolean preferNullOverDefault() {
        return preferNullOverDefault;
    }

    /**
     * the number of skipped rows
     *
     * @return n rows skipped
     */
    public int skip() {
        return skip;
    }

    //ISSUE #55
    public boolean ignoreHiddenSheets() {
        return ignoreHiddenSheets;
    }

    //ISSUE #55
    public PoijiOptions setIgnoreHiddenSheets(boolean ignoreHiddenSheets) {
        this.ignoreHiddenSheets = ignoreHiddenSheets;
        return this;
    }

    //ISSUE #55 : additional
    public boolean trimCellValue() {
        return trimCellValue;
    }

    public PoijiOptions setTrimCellValue(boolean trimCellValue) {
        this.trimCellValue = trimCellValue;
        return this;
    }

    public static class PoijiOptionsBuilder {

        private int skip = 1;
        private Integer sheetIndex;
        private String password;
        private boolean preferNullOverDefault = false;
        private String datePattern = DEFAULT_DATE_PATTERN;
        private DateTimeFormatter dateTimeFormatter = DEFAULT_DATE_TIME_FORMATTER;
        //ISSUE #55
        private boolean ignoreHiddenSheets = false;
        private boolean trimCellValue = false;

        private PoijiOptionsBuilder() {
        }

        private PoijiOptionsBuilder(int skip) {
            this.skip = skip;
        }

        public PoijiOptions build() {
            return new PoijiOptions()
                    .setSkip(skip)
                    .setPassword(password)
                    .setPreferNullOverDefault(preferNullOverDefault)
                    .setDatePattern(datePattern)
                    .setDateTimeFormatter(dateTimeFormatter)
                    .setSheetIndex(sheetIndex)
                    .setIgnoreHiddenSheets(ignoreHiddenSheets)
                    .setTrimCellValue(trimCellValue);
        }

        public static PoijiOptionsBuilder settings() {
            return new PoijiOptionsBuilder();
        }

        /**
         * set a date time formatter, default date time formatter is "dd/M/yyyy"
         * for java.time.LocalDate
         *
         * @param dateTimeFormatter date time formatter
         * @return this
         */
        public PoijiOptionsBuilder dateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
            this.dateTimeFormatter = dateTimeFormatter;
            return this;
        }

        /**
         * set date pattern, default date format is "dd/M/yyyy" for
         * java.util.Date
         *
         * @param datePattern date time formatter
         * @return this
         */
        public PoijiOptionsBuilder datePattern(String datePattern) {
            this.datePattern = datePattern;
            return this;
        }

        /**
         * set whether or not to use null instead of default values for Integer,
         * Double, Float, Long, String and java.util.Date types.
         *
         * @param preferNullOverDefault boolean
         * @return this
         */
        public PoijiOptionsBuilder preferNullOverDefault(boolean preferNullOverDefault) {
            this.preferNullOverDefault = preferNullOverDefault;
            return this;
        }

        /**
         * skip number of row
         *
         * @param skip number
         * @return this
         */
        public PoijiOptionsBuilder skip(int skip) {
            this.skip = skip;
            return this;
        }

        /**
         * set sheet index, default is 0
         *
         * @param sheetIndex number
         * @return this
         */
        public PoijiOptionsBuilder sheetIndex(Integer sheetIndex) {
            this.sheetIndex = sheetIndex;
            return this;
        }

        /**
         * Skip the n rows of the excel data. Default is 1
         *
         * @param skip ignored row number
         * @return builder itself
         */
        public static PoijiOptionsBuilder settings(int skip) {
            return new PoijiOptionsBuilder(skip);
        }

        /**
         * set password for encrypted excel file, Default is null
         *
         * @param password
         * @return this
         */
        public PoijiOptionsBuilder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * Ignore hidden sheets
         *
         * @param ignoreHiddenSheets whether or not to ignore any hidden sheets
         * in the work book.
         * @return this
         */
        public PoijiOptionsBuilder ignoreHiddenSheets(boolean ignoreHiddenSheets) {
            this.ignoreHiddenSheets = ignoreHiddenSheets;
            return this;
        }

        /**
         * Trim cell value
         *
         * @param trimCellValue trim the cell value before processing work book.
         * @return this
         */
        public PoijiOptionsBuilder trimCellValue(boolean trimCellValue) {
            this.trimCellValue = trimCellValue;
            return this;
        }
    }

    //ISSUE #55
    //loop througth all sheets and check if visiable
    //if visiable return sheet index, else null so can fall back to default
    private static Integer findVisiableSheetAtIndex(Workbook workbook, int findIndex) {

        int visiableIndex = 0;

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            if (!workbook.isSheetHidden(i) && !workbook.isSheetVeryHidden(i)) {

                if (visiableIndex == findIndex) {
                    return i;
                } else {
                    visiableIndex++;
                }
            }
        }
        return null;
    }

    //ISSUE #55
    //from the given work book, using the oprions set work out which sheet to process
    //if given an sheet number, process that
    //else set to use the default first sheet
    //if ignore hidden sheets is true, then loop the nuber of hidden sheets and match requested index
    public static final int getSheetIndexToProcess(Workbook workbook, PoijiOptions options) {
        int findIndex;
        //if given sheet index to use, use that
        if (options.sheetIndex() != null && options.sheetIndex() > -1) {
            findIndex = options.sheetIndex();
        } else {
            //else default
            findIndex = 0;
        }

        int sheetIndex;
        //if set to hignore hidden find the visiable sheet that matches the index requested
        if (options.ignoreHiddenSheets()) {
            Integer visiableSheetIndex = findVisiableSheetAtIndex(workbook, findIndex);
            if (visiableSheetIndex != null) {
                sheetIndex = visiableSheetIndex;
            } else {
                //if no sheet found, default back
                sheetIndex = findIndex;
            }
        } else {
            //if dont want to ignore hidden sheets, use index given or default
            sheetIndex = findIndex;
        }
        return sheetIndex;
    }
}
