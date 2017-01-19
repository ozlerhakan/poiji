package com.poiji.exception;

/**
 * Created by hakan on 17/01/2017.
 */
public class InvalidExcelFileExtension extends PoijiException {

    public InvalidExcelFileExtension() {
        super();
    }

    public InvalidExcelFileExtension(String message) {
        super(message);
    }

    public InvalidExcelFileExtension(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidExcelFileExtension(Throwable cause) {
        super(cause);
    }

    public InvalidExcelFileExtension(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
