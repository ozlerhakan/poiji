package com.poiji.exception;

/**
 * Created by hakan on 18/01/2017.
 */
public class PoijiException extends RuntimeException {

    public PoijiException(String message) {
        super(message);
    }

    public PoijiException(String message, Throwable cause) {
        super(message, cause);
    }

}
