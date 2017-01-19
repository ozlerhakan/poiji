package com.poiji.internal;

/**
 * Created by hakan on 17/01/2017.
 */
public final class PoijiOptions {

    private int skip;

    private PoijiOptions(){
        super();
    }

    private PoijiOptions setSkip(int skip) {
        this.skip = skip;
        return this;
    }

    /**
     * the number of skipped rows
     * @return n rows skipped
     */
    public int skip() {
        return skip;
    }

    public static class PoijiOptionsBuilder {

        private int skip = 1;

        private PoijiOptionsBuilder(){}

        private PoijiOptionsBuilder(int skip){
            this.skip = skip;
        }

        public PoijiOptions build(){
            return new PoijiOptions().setSkip(skip);
        }

        public static PoijiOptionsBuilder settings(){
            return new PoijiOptionsBuilder();
        }

        /**
         * Skip the n rows of the excel data. Default is 1
         * @param skip ignored row number
         * @return builder itself
         */
        public static PoijiOptionsBuilder settings(int skip){
            return new PoijiOptionsBuilder(skip);
        }

    }
}
