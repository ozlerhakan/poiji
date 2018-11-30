package com.poiji.util;

import com.poiji.option.PoijiOptions;

/**
 * Created by hakan on 22/01/2017.
 */
public interface Casting {

    Object castValue(Class<?> fieldType, String value, PoijiOptions options);
}
