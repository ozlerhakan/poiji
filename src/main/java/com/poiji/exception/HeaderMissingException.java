package com.poiji.exception;

/**
 * Exception thrown if namedHeaderMandatory is set in the options, but a header specified in an @ExcelCellName
 * is missing in the sheet.
 */
@SuppressWarnings("serial")
public class HeaderMissingException extends PoijiException {
    public HeaderMissingException(String message) {
        super(message);
    }
}
