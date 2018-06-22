package com.poiji.option;

import static com.poiji.util.PoijiConstants.DEFAULT_DATE_PATTERN;

/**
 * Created by hakan on 17/01/2017.
 */
public final class PoijiOptions {

    private int skip;
    private String datePattern;
    private boolean preferNullOverDefault;
    private int sheetIndex;
    private String password;

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

    private PoijiOptions setPreferNullOverDefault(boolean preferNullOverDefault) {
        this.preferNullOverDefault = preferNullOverDefault;
        return this;
    }

    private PoijiOptions setSheetIndex(int sheetIndex) {
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

    public int sheetIndex() {
        return sheetIndex;
    }

    public String datePattern() {
        return datePattern;
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

    public static class PoijiOptionsBuilder {

        private int skip = 1;
        private String datePattern = DEFAULT_DATE_PATTERN;
        private boolean preferNullOverDefault = false;
        private int sheetIndex;
        private String password;

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
                    .setSheetIndex(sheetIndex);
        }

        public static PoijiOptionsBuilder settings() {
            return new PoijiOptionsBuilder();
        }

        /**
         * set date pattern, default pattern is dd/M/yyyy
         *
         * @param datePattern date pattern
         * @return this
         */
        public PoijiOptionsBuilder datePattern(String datePattern) {
            this.datePattern = datePattern;
            return this;
        }

        /**
         * set whether or not to use null instead of default object
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
        public PoijiOptionsBuilder sheetIndex(int sheetIndex) {
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

    }
}
