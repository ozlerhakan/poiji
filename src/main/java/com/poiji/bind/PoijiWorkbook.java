package com.poiji.bind;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by hakan on 17/01/2017.
 */
public abstract class PoijiWorkbook {

    final PoijiFile poijiFile;

    PoijiWorkbook(PoijiFile poijiFile) {
        this.poijiFile = poijiFile;
    }

    public abstract Workbook workbook();

}
