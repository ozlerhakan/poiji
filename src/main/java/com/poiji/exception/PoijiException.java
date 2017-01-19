package com.poiji.exception;

/**
 * Created by hakan on 18/01/2017.
 */
public class PoijiException extends RuntimeException {

    public PoijiException() {
    }

    public PoijiException(String message) {
        super(message);
    }

    public PoijiException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoijiException(Throwable cause) {
        super(cause);
    }

    public PoijiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
