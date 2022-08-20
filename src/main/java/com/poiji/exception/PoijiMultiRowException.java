package com.poiji.exception;

import java.util.List;

/**
 * PoijiMultiRowException is the superclass of RuntimeException that
 * can be thrown during the mapping process of excel service.
 *
 * @since Poiji 3.1.9
 */
@SuppressWarnings("serial")
public class PoijiMultiRowException extends PoijiException {

    private final List<PoijiRowSpecificException> errors;

    public PoijiMultiRowException(String message, List<PoijiRowSpecificException> errors) {
        super(message);
        this.errors = errors;
    }

    public List<PoijiRowSpecificException> getErrors() {
        return errors;
    }

    public static class PoijiRowSpecificException extends RuntimeException {

        private final Integer rowNum;

        public PoijiRowSpecificException(String message, Integer rowNum) {
            super(message);
            this.rowNum = rowNum;
        }

        public Integer getRowNum() {
            return rowNum;
        }
    }
}
