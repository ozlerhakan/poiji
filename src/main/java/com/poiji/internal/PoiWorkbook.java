package com.poiji.internal;

import com.poiji.exception.InvalidExcelFileExtension;
import com.poiji.exception.PoijiException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

import static com.poiji.util.PoijiConstants.XLSX_EXTENSION;
import static com.poiji.util.PoijiConstants.XLS_EXTENSION;

/**
 * Created by hakan on 17/01/2017.
 */
public abstract class PoiWorkbook {

    final PoijiStream stream;

    private PoiWorkbook(PoijiStream stream) {
        this.stream = stream;
    }

    public abstract Workbook workbook();

    static PoiWorkbook workbook(String fileExtension, PoijiStream stream) {
        switch (fileExtension) {
            case XLS_EXTENSION:
                return new PoiWorkbookHSSH(stream);
            case XLSX_EXTENSION:
                return new PoiWorkbookXSSH(stream);
            default:
                throw new InvalidExcelFileExtension("Invalid file extension (" + fileExtension + "), excepted .xls or .xlsx");
        }
    }

    private static class PoiWorkbookXSSH extends PoiWorkbook {

        private PoiWorkbookXSSH(PoijiStream stream) {
            super(stream);
        }

        @Override
        public Workbook workbook() {
            try (OPCPackage open = OPCPackage.open(stream.file())) {
                return new XSSFWorkbook(open);
            } catch (IOException | InvalidFormatException e) {
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
                return WorkbookFactory.create(stream.file());
            } catch (InvalidFormatException | IOException e) {
                throw new PoijiException("Problem occurred while creating HSSFWorkbook", e);
            }
        }

    }
}
