package com.poiji.bind.mapping;

import com.poiji.bind.PoijiFile;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;

/**
 *
 * Created by hakan on 16/01/2017.
 */
final class HSSFUnmarshallerFile extends HSSFUnmarshaller {

    private final PoijiFile<?> poijiFile;

    HSSFUnmarshallerFile(PoijiFile<?> poijiFile, PoijiOptions options) {
        super(options);
        this.poijiFile = poijiFile;
    }

    @Override
    protected Workbook workbook() {
        try {
            Workbook workbook = WorkbookFactory.create(poijiFile.file(), options.getPassword(), true);
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            return workbook;
        } catch (IOException e) {
            throw new PoijiException("Problem occurred while creating HSSFWorkbook", e);
        }
    }
}
