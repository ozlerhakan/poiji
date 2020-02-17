package com.poiji.exception;

import static com.poiji.util.PoijiConstants.XLSX_EXTENSION;
import static com.poiji.util.PoijiConstants.XLS_EXTENSION;

/**
 * Created by hakan on 08/03/2018
 */
public enum PoijiExcelType {
    XLS, XLSX;

    public static PoijiExcelType fromFileName(final String fileName){
        if (fileName.endsWith(XLSX_EXTENSION)){
            return XLSX;
        } else if (fileName.endsWith(XLS_EXTENSION)) {
            return XLS;
        } else {
            throw new InvalidExcelFileExtension("Unsupported extension of file " + fileName);
        }
    }
}
