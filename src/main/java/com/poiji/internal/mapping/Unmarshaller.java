package com.poiji.internal.mapping;

import com.poiji.internal.PoijiFile;
import com.poiji.internal.PoijiWorkbook;
import com.poiji.option.PoijiOptions;

import java.util.List;

/**
 * Created by hakan on 17/01/2017.
 */
public abstract class Unmarshaller {

    public abstract <T> List<T> unmarshal(Class<T> type);

    public static Unmarshaller instance(PoijiWorkbook workbook, PoijiOptions options) {
        return new HSSFUnmarshaller(workbook, options);
    }

    public static Unmarshaller instance(PoijiFile poijiFile, PoijiOptions options) {
        return new XSSFUnmarshaller(poijiFile, options);
    }
}
