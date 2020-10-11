package com.poiji.bind.mapping;

import com.poiji.bind.PoijiInputStream;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
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

            if (options.getPassword() != null) {
                return WorkbookFactory.create(poijiInputStream.stream(), options.getPassword());
            }

            return WorkbookFactory.create(poijiInputStream.stream());
        } catch (IOException e) {
            throw new PoijiException("Problem occurred while creating HSSFWorkbook", e);
        }
    }
}
