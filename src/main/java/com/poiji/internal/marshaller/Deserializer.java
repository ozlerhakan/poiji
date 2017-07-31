package com.poiji.internal.marshaller;

import com.poiji.internal.PoiWorkbook;
import com.poiji.internal.PoijiOptions;

import java.util.List;

/**
 * Created by hakan on 17/01/2017.
 */
public abstract class Deserializer {

    public abstract <T> List<T> deserialize(Class<T> type, PoijiOptions options);

    public static Deserializer instance(PoiWorkbook workbook) {
        return new Unmarshaller(workbook);
    }
}
