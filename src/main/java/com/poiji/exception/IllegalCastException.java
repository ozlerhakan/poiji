package com.poiji.exception;

/**
 * Created by hakan on 17/01/2017.
 */
public class IllegalCastException extends PoijiException {

    public IllegalCastException() {
        super();
    }

    public IllegalCastException(String message) {
        super(message);
    }

    public IllegalCastException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCastException(Throwable cause) {
        super(cause);
    }

    public IllegalCastException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
