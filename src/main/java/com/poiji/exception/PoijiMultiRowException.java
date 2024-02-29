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

        private final String columnName;
        private final String fieldName;
        private final Integer rowNum;

        public PoijiRowSpecificException(String columnName, String fieldName, Integer rowNum) {
            super("Cell value of column '" + columnName + "' is null,"
                    + " so cannot be applied to mandatory field '" + fieldName + "'. ;Row " + rowNum);
            this.columnName = columnName;
            this.fieldName = fieldName;
            this.rowNum = rowNum;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public Integer getRowNum() {
            return rowNum;
        }
    }
}
