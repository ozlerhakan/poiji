package com.poiji.bind.mapping;

import com.poiji.option.PoijiOptions;

import java.io.File;
import java.io.InputStream;

/**
 * Created by hakan on 24.05.2020
 */
public final class PoijiPropertyHelper {

    public static HSSFPropertyFile createPoijiPropertyFile(File file, PoijiOptions options) {
        return new HSSFPropertyFile(file, options);
    }

    public static HSSFPropertyStream createPoijiPropertyStream(InputStream inputStream, PoijiOptions options) {
        return new HSSFPropertyStream(inputStream, options);
    }
}
