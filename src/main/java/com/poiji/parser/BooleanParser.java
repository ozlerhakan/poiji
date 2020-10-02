package com.poiji.parser;

public class BooleanParser implements Parser<Boolean> {
    @Override
    public Boolean parse(String value) {
        if ("true".equalsIgnoreCase(value.trim())) {
            return true;
        }

        if ("false".equalsIgnoreCase(value.trim())) {
            return false;
        }

        /* LibreOffice compatibility:
         *
         * LibreOffice booleans are stored as formula =TRUE() or =FALSE() which is parsed by POI to 1 or 0.
         */
        if ("1".equals(value.trim())) {
            return true;
        }

        if ("0".equals(value.trim())) {
            return false;
        }

        throw new BooleanParseException(value);
    }
    @SuppressWarnings("serial")
    public static class BooleanParseException extends RuntimeException {
        public BooleanParseException(String value) {
            super("Can't parse value to Boolean: " + value);
        }
    }
}
