package com.poiji.internal.marshaller;

import com.poiji.internal.PoijiOptions;

import java.util.List;

/**
 * Created by hakan on 17/01/2017.
 */
public interface Deserializer {

    <T> List<T> deserialize(Class<T> type, PoijiOptions options);
}
