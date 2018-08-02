package com.poiji.bind.mapping;

import com.poiji.bind.PoijiFile;
import com.poiji.bind.Unmarshaller;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;

/**
 * This is the main class that converts the excel sheet fromExcel Java object
 * Created by hakan on 16/01/2017.
 */
final class HSSFUnmarshallerFile extends HSSFUnmarshaller implements Unmarshaller {

    private final PoijiFile poijiFile;

    HSSFUnmarshallerFile(PoijiFile poijiFile, PoijiOptions options) {
        super(options);
        this.poijiFile = poijiFile;
    }

    @Override
    protected Workbook workbook() {
        try {

            if (options.getPassword() != null) {
                return WorkbookFactory.create(poijiFile.file(), options.getPassword());
            }

            return WorkbookFactory.create(poijiFile.file());
        } catch (IOException e) {
            throw new PoijiException("Problem occurred while creating HSSFWorkbook", e);
        }
    }
}
