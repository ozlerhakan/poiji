package com.poiji.exception;

/**
 * Created by hakan on 17/01/2017.
 */
public class IllegalCastException extends PoijiException {

    public IllegalCastException(String message) {
        super(message);
    }

    public IllegalCastException(String message, Throwable cause) {
        super(message, cause);
    }

}
