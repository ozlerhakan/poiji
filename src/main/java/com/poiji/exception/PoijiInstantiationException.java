package com.poiji.exception;

/**
 * Created by hakan on 17/01/2017.
 */
public class PoijiInstantiationException extends PoijiException {

    public PoijiInstantiationException() {
        super();
    }

    public PoijiInstantiationException(String message) {
        super(message);
    }

    public PoijiInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoijiInstantiationException(Throwable cause) {
        super(cause);
    }

    public PoijiInstantiationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
