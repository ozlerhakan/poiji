package com.poiji.exception;

import java.util.Set;

/**
 * Exception thrown if namedHeaderMandatory is set in the options, but a header specified in an @ExcelCellName
 * is missing in the sheet.
 */
@SuppressWarnings("serial")
public class HeaderMissingException extends PoijiException {

    private final Set<Integer> missingExcelCellHeaders;
    private final Set<String> missingExcelCellNameHeaders;

    public HeaderMissingException(String message, Set<Integer> missingExcelCellHeaders,
                                  Set<String> missingExcelCellNameHeaders) {
        super(message);
        this.missingExcelCellHeaders = Set.copyOf(missingExcelCellHeaders);
        this.missingExcelCellNameHeaders = Set.copyOf(missingExcelCellNameHeaders);
    }

    public Set<Integer> getMissingExcelCellHeaders() {
        return missingExcelCellHeaders;
    }

    public Set<String> getMissingExcelCellNameHeaders() {
        return missingExcelCellNameHeaders;
    }
}
