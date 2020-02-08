package com.poiji.option;

import com.poiji.annotation.ExcelCellName;
import com.poiji.config.Casting;
import com.poiji.config.DefaultCasting;
import com.poiji.exception.PoijiException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.poiji.util.PoijiConstants.DEFAULT_DATE_FORMATTER;
import static com.poiji.util.PoijiConstants.DEFAULT_DATE_PATTERN;
import static com.poiji.util.PoijiConstants.DEFAULT_DATE_TIME_FORMATTER;
import static com.poiji.util.PoijiConstants.DEFAULT_DATE_TIME_PATTERN;

/**
 * Created by hakan on 17/01/2017.
 */
public final class PoijiOptions {

    private int skip;
    private int limit;
    private int sheetIndex;
    private String password;
    private String dateRegex;
    private String dateTimeRegex;
    private String datePattern;
    private String localDatePattern;
    private String localDateTimePattern;
    private boolean dateLenient;
    private boolean trimCellValue;
    private boolean ignoreHiddenSheets;
    private boolean preferNullOverDefault;
    private DateTimeFormatter dateFormatter;
    private DateTimeFormatter dateTimeFormatter;
    private Casting casting;
    private int headerStart;
    private String sheetName;
    private boolean caseInsensitive;

    private PoijiOptions() {
        super();
    }

    private PoijiOptions setSkip(int skip) {
        this.skip = skip;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public PoijiOptions setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    private PoijiOptions setDatePattern(String datePattern) {
        this.datePattern = datePattern;
        return this;
    }

    public String getLocalDatePattern() {
        return localDatePattern;
    }

    private PoijiOptions setLocalDatePattern(final String localDatePattern) {
        this.localDatePattern = localDatePattern;
        return this;
    }

    private PoijiOptions setDateFormatter(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
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

    public String getPassword() {
        return password;
    }

    private PoijiOptions setPassword(String password) {
        this.password = password;
        return this;
    }

    public String datePattern() {
        return datePattern;
    }

    public String getLocalDateTimePattern() {
        return localDateTimePattern;
    }

    private PoijiOptions setLocalDateTimePattern(final String localDateTimePattern) {
        this.localDateTimePattern = localDateTimePattern;
        return this;
    }

    public DateTimeFormatter dateFormatter() {
        return dateFormatter;
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

    public boolean ignoreHiddenSheets() {
        return ignoreHiddenSheets;
    }

    private PoijiOptions setIgnoreHiddenSheets(boolean ignoreHiddenSheets) {
        this.ignoreHiddenSheets = ignoreHiddenSheets;
        return this;
    }

    public boolean trimCellValue() {
        return trimCellValue;
    }

    public PoijiOptions setTrimCellValue(boolean trimCellValue) {
        this.trimCellValue = trimCellValue;
        return this;
    }

    public Casting getCasting() {
        return casting;
    }

    public PoijiOptions setCasting(Casting casting) {
        this.casting = casting;
        return this;
    }

    private PoijiOptions setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
        return this;
    }

    public int sheetIndex() {
        return sheetIndex;
    }

    public String getDateRegex() {
        return dateRegex;
    }

    private PoijiOptions setDateRegex(String dateRegex) {
        this.dateRegex = dateRegex;
        return this;
    }

    public String getDateTimeRegex() {
        return dateTimeRegex;
    }

    private PoijiOptions setDateTimeRegex(String dateTimeRegex) {
        this.dateTimeRegex = dateTimeRegex;
        return this;
    }

    public boolean getDateLenient() {
        return dateLenient;
    }

    private PoijiOptions setDateLenient(boolean dateLenient) {
        this.dateLenient = dateLenient;
        return this;
    }

    public int getHeaderStart() {
        return headerStart;
    }

    private PoijiOptions setHeaderStart(int headerStart) {
        this.headerStart = headerStart;
        return this;
    }

    private PoijiOptions setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public String getSheetName() {
        return sheetName;
    }

    public boolean getCaseInsensitive() {
        return caseInsensitive;
    }

    public PoijiOptions setCaseInsensitive(final boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
        return this;
    }

    public static class PoijiOptionsBuilder {

        private int sheetIndex;
        private String password;
        private String dateRegex;
        private String dateTimeRegex;
        private boolean dateLenient;
        private boolean trimCellValue;
        private boolean ignoreHiddenSheets;
        private boolean preferNullOverDefault;
        private String datePattern = DEFAULT_DATE_PATTERN;
        private DateTimeFormatter dateFormatter = DEFAULT_DATE_FORMATTER;
        private String localDatePattern = DEFAULT_DATE_PATTERN;
        private String localDateTimePattern = DEFAULT_DATE_TIME_PATTERN;
        private DateTimeFormatter dateTimeFormatter = DEFAULT_DATE_TIME_FORMATTER;
        private Casting casting = new DefaultCasting();
        private int headerStart = 0;
        private int skip = 0;
        private int limit = 0;
        private String sheetName;
        private boolean caseInsensitive;

        private PoijiOptionsBuilder() {
        }

        private PoijiOptionsBuilder(int skip) {
            this.skip = skip;
        }

        /**
         * Skip a number of rows after the header row. The header row is not counted.
         *
         * @param skip ignored row number after the header row
         * @return builder itself
         */
        public static PoijiOptionsBuilder settings(int skip) {
            if (skip < 0) {
                throw new PoijiException("Skip index must be greater than or equal to 0");
            }
            return new PoijiOptionsBuilder(skip);
        }

        public static PoijiOptionsBuilder settings() {
            return new PoijiOptionsBuilder();
        }

        /**
         * set a date formatter, default date time formatter is "dd/M/yyyy"
         * for java.time.LocalDate
         *
         * @param dateFormatter date time formatter
         * @return this
         */
        public PoijiOptionsBuilder dateFormatter(DateTimeFormatter dateFormatter) {
            this.dateFormatter = dateFormatter;
            return this;
        }

        /**
         * set a date time formatter, default date time formatter is "dd/M/yyyy HH:mm:ss"
         * for java.time.LocalDateTime
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
         * @param datePattern date pattern
         * @return this
         */
        public PoijiOptionsBuilder datePattern(String datePattern) {
            this.datePattern = datePattern;
            return this;
        }

        /**
         * set date time pattern, default date time format is "dd/M/yyyy HH:mm:ss" for
         * writing java.time.LocalDate into excel
         *
         * @param localDatePattern date time pattern
         * @return this
         */
        public PoijiOptionsBuilder localDatePattern(String localDatePattern) {
            this.localDatePattern = localDatePattern;
            return this;
        }

        /**
         * set date time pattern, default date time format is "dd/M/yyyy HH:mm:ss" for
         * writing java.time.LocalDateTime into excel
         *
         * @param localDateTimePattern date time pattern
         * @return this
         */
        public PoijiOptionsBuilder localDateTimePattern(String localDateTimePattern) {
            this.localDateTimePattern = localDateTimePattern;
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

        public PoijiOptions build() {
            return new PoijiOptions()
                .setSkip(skip + headerStart + 1)
                .setPassword(password)
                .setPreferNullOverDefault(preferNullOverDefault)
                .setDatePattern(datePattern)
                .setLocalDatePattern(localDatePattern)
                .setLocalDateTimePattern(localDateTimePattern)
                    .setDateFormatter(dateFormatter)
                    .setDateTimeFormatter(dateTimeFormatter)
                    .setSheetIndex(sheetIndex)
                    .setSheetName(sheetName)
                    .setIgnoreHiddenSheets(ignoreHiddenSheets)
                    .setTrimCellValue(trimCellValue)
                    .setDateRegex(dateRegex)
                    .setDateTimeRegex(dateTimeRegex)
                    .setDateLenient(dateLenient)
                    .setHeaderStart(headerStart)
                    .setCasting(casting)
                    .setLimit(limit)
                    .setCaseInsensitive(caseInsensitive);
        }

        /**
         * set sheet index, default is 0
         *
         * @param sheetIndex number
         * @return this
         */
        public PoijiOptionsBuilder sheetIndex(int sheetIndex) {
            if (sheetIndex < 0) {
                throw new PoijiException("Sheet index must be greater than or equal to 0");
            }
            this.sheetIndex = sheetIndex;
            return this;
        }

        /**
         * Set the sheet Name
         *
         * @param sheetName excel sheet name
         * @return this
         */
        public PoijiOptionsBuilder sheetName(String sheetName) {
            this.sheetName = sheetName;
            return this;
        }

        /**
         * skip a number of rows after the header row. The header row is not counted.
         *
         * @param skip number
         * @return this
         */
        public PoijiOptionsBuilder skip(int skip) {
            if (skip < 0) {
                throw new PoijiException("Skip index must be greater than or equal to 0");
            }
            this.skip = skip;
            return this;
        }

        /**
         * limit a number of rows after the header & skipped rows row. The header & skipped rows are not counted.
         *
         * @param limit number
         * @return this
         */

        public PoijiOptionsBuilder limit(int limit) {
            if (limit < 1) {
                throw new PoijiException("limit must be greater than 0");
            }
            this.limit = limit;
            return this;
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
         *                           in the work book.
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

        /**
         * Date regex, if would like to specify a regex patter the date must be
         * in, e.g.\\d{2}/\\d{1}/\\d{4}.
         *
         * @param dateRegex date regex pattern
         * @return this
         */
        public PoijiOptionsBuilder dateRegex(String dateRegex) {
            this.dateRegex = dateRegex;
            return this;
        }

        /**
         * DateTime regex, if would like to specify a regex patter the date time must be
         * in, e.g.\\d{2}/\\d{1}/\\d{4} \\d{2}:\\d{2}:\\d{2}.
         *
         * @param dateTimeRegex date regex pattern
         * @return this
         */
        public PoijiOptionsBuilder dateTimeRegex(String dateTimeRegex) {
            this.dateTimeRegex = dateTimeRegex;
            return this;
        }

        /**
         * If the simple date format is lenient, use to
         * set how strict the date formatting must be, defaults to lenient false.
         * It works only for java.util.Date.
         *
         * @param dateLenient true or false
         * @return this
         */
        public PoijiOptionsBuilder dateLenient(boolean dateLenient) {
            this.dateLenient = dateLenient;
            return this;
        }

        /**
         * Use a custom casting implementation
         *
         * @param casting custom casting implementation
         * @return this
         */
        public PoijiOptionsBuilder withCasting(Casting casting) {
            Objects.requireNonNull(casting);

            this.casting = casting;
            return this;
        }

        /**
         * This is to set the row which the unmarshall will
         * use to start reading header titles, incase the
         * header is not in row 0.
         *
         * @param headerStart an index number of the excel header to start reading header
         * @return this
         */
        public PoijiOptionsBuilder headerStart(int headerStart) {
            if (headerStart < 0) {
                throw new PoijiException("Header index must be greater than or equal to 0");
            }
            this.headerStart = headerStart;
            return this;
        }

        /**
         * Permits case insensitive column names mapping for annotation {@link ExcelCellName}.
         * Default - false.
         *
         * @param caseInsensitive true or false
         */
        public PoijiOptionsBuilder caseInsensitive(final boolean caseInsensitive) {
            this.caseInsensitive = caseInsensitive;
            return this;
        }
    }

}
