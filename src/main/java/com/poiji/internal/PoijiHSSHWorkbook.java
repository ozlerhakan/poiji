package com.poiji.internal;

import com.poiji.exception.PoijiException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;

/**
 * Created by hakan on 22/10/2017
 */
final class PoijiHSSHWorkbook extends PoijiWorkbook {

    PoijiHSSHWorkbook(PoijiFile stream) {
        super(stream);
    }

    @Override
    public Workbook workbook() {
        try {
            return WorkbookFactory.create(poijiFile.file());
        } catch (InvalidFormatException | IOException e) {
            throw new PoijiException("Problem occurred while creating HSSFWorkbook", e);
        }
    }
}
