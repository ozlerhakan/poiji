package com.poiji.exception;

/**
 * Created by hakan on 17/01/2017.
 */
@SuppressWarnings("serial")
public final class InvalidExcelFileExtension extends PoijiException {

    public InvalidExcelFileExtension(String message) {
        super(message);
    }

}
