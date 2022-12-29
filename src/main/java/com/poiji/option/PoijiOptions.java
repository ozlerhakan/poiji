package com.poiji.option;

import com.poiji.annotation.ExcelCellName;
import com.poiji.bind.mapping.PoijiLogCellFormat;
import com.poiji.bind.mapping.PoijiNumberFormat;
import com.poiji.config.Casting;
import com.poiji.config.DefaultCasting;
import com.poiji.config.DefaultFormatting;
import com.poiji.config.Formatting;
import com.poiji.exception.PoijiException;
import org.apache.poi.util.LocaleUtil;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import static com.poiji.util.PoijiConstants.DEFAULT_DATE_FORMATTER;
import static com.poiji.util.PoijiConstants.DEFAULT_DATE_PATTERN;
import static com.poiji.util.PoijiConstants.DEFAULT_DATE_TIME_FORMATTER;

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
    private boolean dateLenient;
    private boolean trimCellValue;
    private boolean ignoreHiddenSheets;
    private boolean preferNullOverDefault;
    private DateTimeFormatter dateFormatter;
    private DateTimeFormatter dateTimeFormatter;
    private Casting casting;
    private int headerStart;
    private int headerCount;
    private String sheetName;
    private boolean caseInsensitive;
    private boolean ignoreWhitespaces;
    private PoijiLogCellFormat poijiLogCellFormat;
    private PoijiNumberFormat numberFormat;
    private boolean disableXLSXNumberCellFormat;
    private String listDelimiter;
    private Formatting formatting;
    private Locale locale;
    private boolean rawData;

    public PoijiNumberFormat getPoijiNumberFormat() {
        return numberFormat;
    }

    private PoijiOptions setPoijiNumberFormat(PoijiNumberFormat numberFormat) {
        this.numberFormat = numberFormat;
        return this;
    }

    public PoijiLogCellFormat getPoijiCellFormat() {
        return poijiLogCellFormat;
    }

    private PoijiOptions setPoijiLogCellFormat(PoijiLogCellFormat poijiLogCellFormat) {
        this.poijiLogCellFormat = poijiLogCellFormat;
        return this;
    }

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

    public int getHeaderCount() {
        return headerCount;
    }

    private PoijiOptions setHeaderStart(int headerStart) {
        this.headerStart = headerStart;
        return this;
    }

    private PoijiOptions setHeaderCount(int headerCount) {
        this.headerCount = headerCount;
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

    private PoijiOptions setCaseInsensitive(final boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
        return this;
    }

    public boolean getIgnoreWhitespaces() {
        return ignoreWhitespaces;
    }

    private PoijiOptions setIgnoreWhitespaces(final boolean ignoreWhitespaces) {
        this.ignoreWhitespaces = ignoreWhitespaces;
        return this;
    }

    private PoijiOptions disableXLSXNumberCellFormat(boolean disableXLSXNumberCellFormat) {
        this.disableXLSXNumberCellFormat = disableXLSXNumberCellFormat;
        return this;
    }

    public boolean isDisableXLSXNumberCellFormat() {
        return disableXLSXNumberCellFormat;
    }

    public String getListDelimiter() {
        return listDelimiter;
    }

    private PoijiOptions setListDelimiter(String listDelimiter) {
        this.listDelimiter = listDelimiter;
        return this;
    }

    public Formatting getFormatting() {
        return formatting;
    }

    private PoijiOptions setFormatting(Formatting formatting) {
        this.formatting = formatting;
        return this;
    }

    public Locale getLocale() {
        return this.locale;
    }

    private PoijiOptions setLocale(Locale locale) {
        if (!this.rawData) {
            this.locale = locale;
            LocaleUtil.setUserLocale(locale);
        }
        return this;
    }

    public boolean isRawData() {
        return rawData;
    }

    private PoijiOptions setRawData(boolean rawData) {
        if (rawData) {
            setLocale(Locale.US);
        }
        this.rawData = rawData;
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
        private DateTimeFormatter dateTimeFormatter = DEFAULT_DATE_TIME_FORMATTER;
        private Casting casting = new DefaultCasting();
        private Formatting formatting = new DefaultFormatting();
        private PoijiLogCellFormat cellFormat;
        private PoijiNumberFormat numberFormat = StylesFormatHelper.loadDefaultStyles();
        private int headerStart = 0;
        private int headerCount = 1;
        private int skip = 1;
        private int limit = 0;
        private String sheetName;
        private boolean caseInsensitive;
        private boolean ignoreWhitespaces;
        private boolean disabledXLSXNumberCellFormat;
        private String listDelimiter = "\\s*,\\s*";
        private Locale locale = Locale.US;
        private boolean rawData;

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
            if (skip <= 0) {
                throw new PoijiException("Poiji already skips the header. Skip index must be greater than 1");
            }
            return new PoijiOptionsBuilder(skip + 1);
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
         * set a date time formatter, default date time formatter is "dd/M/yyyy
         * HH:mm:ss"
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
         * Set the {@link Locale} used by Apache Poi and PoiJ. Default is
         * {@link Locale#ENGLISH}.
         * This setting is only used by Apache Poi thread and PoiJ. See
         * {@link org.apache.poi.util.LocaleUtil}
         * for more details.
         *
         * @param locale Locale
         * @return this
         */
        public PoijiOptionsBuilder setLocale(Locale locale) {
            this.locale = locale;
            return this;
        }

        public PoijiOptions build() {
            return new PoijiOptions()
                    .setSkip(skip + headerStart + headerCount - 1)
                    .setPassword(password)
                    .setPreferNullOverDefault(preferNullOverDefault)
                    .setDatePattern(datePattern)
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
                    .setHeaderCount(headerCount)
                    .setCasting(casting)
                    .setLimit(limit)
                    .setPoijiLogCellFormat(cellFormat)
                    .setPoijiNumberFormat(numberFormat)
                    .setCaseInsensitive(caseInsensitive)
                    .setIgnoreWhitespaces(ignoreWhitespaces)
                    .disableXLSXNumberCellFormat(disabledXLSXNumberCellFormat)
                    .setListDelimiter(listDelimiter)
                    .setFormatting(formatting)
                    .setLocale(locale)
                    .setRawData(rawData);

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
            if (skip <= 0) {
                throw new PoijiException("Poiji already skips the header. Skip index must be greater than 1");
            }
            this.skip = skip + 1;
            return this;
        }

        /**
         * limit a number of rows after the header &amp; skipped rows row. The header
         * &amp; skipped rows are not counted.
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
         * @param password excel password
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
         * @param headerStart an index number of the excel header to start reading
         *                    header
         * @return this
         */
        public PoijiOptionsBuilder headerStart(int headerStart) {
            if (headerStart < 0) {
                throw new PoijiException("Header index must be greater than 0");
            }
            this.headerStart = headerStart;
            return this;
        }

        /**
         * This is to set the number of row contains headers
         *
         * Set 0 to indicate that no header in the excel file.
         * Default - 1.
         *
         * @param headerCount an index number of the excel header to start reading
         *                    header
         * @return this
         */
        public PoijiOptionsBuilder headerCount(int headerCount) {
            if (headerCount < 0) {
                throw new PoijiException("Number of header row must be greater than 0");
            }
            this.headerCount = headerCount;
            return this;
        }

        /**
         * Permits case insensitive column names mapping for annotation
         * {@link ExcelCellName}.
         * Default - false.
         *
         * @param caseInsensitive true or false
         * @return this
         */
        public PoijiOptionsBuilder caseInsensitive(final boolean caseInsensitive) {
            this.caseInsensitive = caseInsensitive;
            return this;
        }

        /**
         * Ignore white space before and after column names for annotation
         * {@link ExcelCellName}.
         * Default - false.
         *
         * @param ignoreWhitespaces true or false
         * @return this
         */
        public PoijiOptionsBuilder ignoreWhitespaces(final boolean ignoreWhitespaces) {
            this.ignoreWhitespaces = ignoreWhitespaces;
            return this;
        }

        /**
         * Add cell format option to see each internal cell's excel format for files
         * ending with xlsx format.
         * This option should be enabled for debugging purpose.
         *
         * @param cellFormat poiji cell format instance
         * @return this
         */
        public PoijiOptionsBuilder poijiLogCellFormat(final PoijiLogCellFormat cellFormat) {
            this.cellFormat = cellFormat;
            return this;
        }

        /**
         * Change the default cell formats of a xlsx excel file by overriding
         *
         * @param numberFormat poiji number format instance
         * @return this
         */
        public PoijiOptionsBuilder poijiNumberFormat(final PoijiNumberFormat numberFormat) {
            this.numberFormat = numberFormat;
            return this;
        }

        /**
         * Disable the cell format of all the number cells of an excel file ending with
         * xlsx
         *
         * @return this
         */
        public PoijiOptionsBuilder disableXLSXNumberCellFormat() {
            this.disabledXLSXNumberCellFormat = true;
            return this;
        }

        /**
         * Use different delimiter to split the list of items of a cell
         *
         * @param delimiter by default delimiter is ','
         * @return this
         */
        public PoijiOptionsBuilder addListDelimiter(String delimiter) {
            this.listDelimiter = String.format("\\s*%s\\s*", delimiter);
            return this;
        }

        /**
         * Use a custom excel header format implementation
         *
         * @param formatting custom header format implementation
         * @return this
         */
        public PoijiOptionsBuilder withFormatting(Formatting formatting) {
            Objects.requireNonNull(formatting);

            this.formatting = formatting;
            return this;
        }

        /**
         * Use this option to get the underlying/original/non-visible/raw cell value.
         * The cell must be a numeric type.
         *
         * @param status set true to retrieve the underlying data in the excel file.
         * @return this
         */
        public PoijiOptionsBuilder rawData(boolean status) {
            this.rawData = status;
            return this;
        }
    }

}
