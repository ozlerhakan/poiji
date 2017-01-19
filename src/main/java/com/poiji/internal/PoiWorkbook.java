package com.poiji.internal;

import com.poiji.exception.InvalidExcelFileExtension;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

/**
 * Created by hakan on 17/01/2017.
 */
public abstract class PoiWorkbook {

    protected final PoijiStream poiParser;

    private PoiWorkbook(PoijiStream poiParser) {
        this.poiParser = poiParser;
    }

    public abstract Workbook workbook();

    static PoiWorkbook workbook(String fileExtension, PoijiStream poiParser) {
        switch (fileExtension) {
            case ".xls":
                return new PoiWorkbookHSSH(poiParser);
            case ".xlsx":
                return new PoiWorkbookXSSH(poiParser);
            default:
                throw new InvalidExcelFileExtension("Invalid file extension (" + fileExtension + "), excepted .xls or .xlsx");
        }
    }

    private static class PoiWorkbookXSSH extends PoiWorkbook {

        private PoiWorkbookXSSH(PoijiStream poiParser) {
            super(poiParser);
        }

        @Override
        public Workbook workbook() {
            try {
                return new XSSFWorkbook(poiParser.getFis());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static class PoiWorkbookHSSH extends PoiWorkbook {

        private PoiWorkbookHSSH(PoijiStream poiParser) {
            super(poiParser);
        }

        @Override
        public Workbook workbook() {
            try {
                return new HSSFWorkbook(poiParser.getFis());
            } catch (IOException e) {
                throw new RuntimeException("Problem occurred while creting ");
            } catch (NullPointerException e) {
                throw new RuntimeException("Problem occurred while cre");
            }
        }

    }
}
