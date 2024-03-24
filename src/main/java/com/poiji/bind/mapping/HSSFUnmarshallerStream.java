package com.poiji.bind.mapping;

import com.poiji.bind.PoijiInputStream;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;

/**
 * Created by hakan on 08/03/2018.
 */
final class HSSFUnmarshallerStream extends HSSFUnmarshaller {

    private final PoijiInputStream<?> poijiInputStream;

    HSSFUnmarshallerStream(PoijiInputStream<?> poijiInputStream, PoijiOptions options) {
        super(options);
        this.poijiInputStream = poijiInputStream;
    }

    @Override
    protected Workbook workbook() {
        try {
            Workbook workbook;
            if (options.getPassword() != null) {
                workbook = WorkbookFactory.create(poijiInputStream.stream(), options.getPassword());
            } else {
                workbook = WorkbookFactory.create(poijiInputStream.stream());
            }
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            return workbook;
        } catch (IOException e) {
            throw new PoijiException("Problem occurred while creating HSSFWorkbook", e);
        }
    }
}
