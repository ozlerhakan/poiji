package com.poiji.internal;

import com.poiji.exception.InvalidExcelFileExtension;
import com.poiji.exception.PoijiException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

/**
 * Created by hakan on 17/01/2017.
 */
public abstract class PoiWorkbook {

    private final PoijiStream stream;

    private PoiWorkbook(PoijiStream stream) {
        this.stream = stream;
    }

    public abstract Workbook workbook();

    public static PoiWorkbook workbook(String fileExtension, PoijiStream stream) {
        switch (fileExtension) {
            case ".xls":
                return new PoiWorkbookHSSH(stream);
            case ".xlsx":
                return new PoiWorkbookXSSH(stream);
            default:
                throw new InvalidExcelFileExtension("Invalid file extension (" + fileExtension + "), excepted .xls or .xlsx");
        }
    }

    protected PoijiStream stream() {
        return stream;
    }

    private static class PoiWorkbookXSSH extends PoiWorkbook {

        private PoiWorkbookXSSH(PoijiStream stream) {
            super(stream);
        }

        @Override
        public Workbook workbook() {
            try {
                return new XSSFWorkbook(stream().get());
            } catch (IOException e) {
                throw new PoijiException("Problem occurred while creating XSSFWorkbook", e);
            }
        }

    }

    private static class PoiWorkbookHSSH extends PoiWorkbook {

        private PoiWorkbookHSSH(PoijiStream stream) {
            super(stream);
        }

        @Override
        public Workbook workbook() {
            try {
                return new HSSFWorkbook(stream().get());
            } catch (IOException e) {
                throw new PoijiException("Problem occurred while creating HSSFWorkbook", e);
            }
        }

    }
}
