package com.poiji.internal;

import java.io.File;
import java.io.InputStream;

/**
 * Created by hakan on 16/01/2017.
 */
class PoijiStream<T extends File> {

    private final T t;

    PoijiStream(T t) {
        this.t = t;
    }

    /**
     * the T derived from {@link InputStream}
     * @return T
     */
    public T file() {
        return t;
    }
}
