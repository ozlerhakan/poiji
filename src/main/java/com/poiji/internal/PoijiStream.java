package com.poiji.internal;

import java.io.InputStream;

/**
 * Created by hakan on 16/01/2017.
 */
class PoijiStream<T extends InputStream> {

    private final T fis;

    PoijiStream(T fis) {
        this.fis = fis;
    }

    T getFis() {
        return fis;
    }
}
