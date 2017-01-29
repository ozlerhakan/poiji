package com.poiji.exception;

/**
 * Created by hakan on 17/01/2017.
 */
public class InvalidExcelFileExtension extends PoijiException {

    public InvalidExcelFileExtension(String message) {
        super(message);
    }

    public InvalidExcelFileExtension(String message, Throwable cause) {
        super(message, cause);
    }

}
