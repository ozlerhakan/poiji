package com.poiji.exception;

/**
 * PoijiException is the superclass of RuntimeException that
 * can be thrown during the mapping process of excel service.
 * Created by hakan on 18/01/2017.
 * @since   Poiji 1.0
 */
@SuppressWarnings("serial")
public class PoijiException extends RuntimeException {

    public PoijiException(String message) {
        super(message);
    }

    public PoijiException(String message, Throwable cause) {
        super(message, cause);
    }

}
