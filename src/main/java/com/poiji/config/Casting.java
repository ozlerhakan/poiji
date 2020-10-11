package com.poiji.config;

import com.poiji.option.PoijiOptions;

import java.lang.reflect.Field;

/**
 * A casting interface to build a custom poiji configuration.
 *
 * Created by hakan on 22/01/2017.
 */
public interface Casting {

    Object castValue(Field fieldType, String value, int row, int column, PoijiOptions options);
}
