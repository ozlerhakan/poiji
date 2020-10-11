package com.poiji.bind.mapping;

import com.poiji.bind.PoijiFile;
import com.poiji.bind.PoijiInputStream;
import com.poiji.bind.Unmarshaller;
import com.poiji.option.PoijiOptions;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Created by hakan on 17/01/2017.
 */
public final class UnmarshallerHelper {

    public static Unmarshaller HSSFInstance(PoijiFile<?> poijiFile, PoijiOptions options) {
        return new HSSFUnmarshallerFile(poijiFile, options);
    }

    public static Unmarshaller HSSFInstance(PoijiInputStream<?> poijiInputStream, PoijiOptions options) {
        return new HSSFUnmarshallerStream(poijiInputStream, options);
    }

    public static Unmarshaller XSSFInstance(PoijiFile<?> poijiFile, PoijiOptions options) {
        return new XSSFUnmarshallerFile(poijiFile, options);
    }

    public static Unmarshaller XSSFInstance(PoijiInputStream<?> poijiInputStream, PoijiOptions options) {
        return new XSSFUnmarshallerStream(poijiInputStream, options);
    }

    public static Unmarshaller SheetInstance(Sheet sheet, PoijiOptions options) {
        return new SheetUnmarshaller(sheet, options);
    }
}
