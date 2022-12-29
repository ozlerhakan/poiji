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

        public PoijiRowSpecificException(String columnName, String fieldName, Integer rowNum) {
            super("Cell value of column '" + columnName + "' is null,"
                    + " so cannot be applied to mandatory field '" + fieldName + "'. ;Row " + rowNum);
        }
    }
}
