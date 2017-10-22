package com.poiji.internal;

import com.poiji.exception.InvalidExcelFileExtension;
import com.poiji.exception.PoijiException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;

import static com.poiji.util.PoijiConstants.XLSX_EXTENSION;
import static com.poiji.util.PoijiConstants.XLS_EXTENSION;

/**
 * Created by hakan on 17/01/2017.
 */
public abstract class PoijiWorkbook {

    final PoijiStream stream;

    private PoijiWorkbook(PoijiStream stream) {
        this.stream = stream;
    }

    public abstract Workbook workbook();

    static PoijiWorkbook workbook(String fileExtension, PoijiStream stream) {
        switch (fileExtension) {
            case XLS_EXTENSION:
                return new PoiWorkbookHSSH(stream);
            case XLSX_EXTENSION:
                return new PoiWorkbookXSSH(stream);
            default:
                throw new InvalidExcelFileExtension("Invalid file extension (" + fileExtension + "), excepted .xls or .xlsx");
        }
    }

    private static class PoiWorkbookXSSH extends PoijiWorkbook {

        private PoiWorkbookXSSH(PoijiStream stream) {
            super(stream);
        }

        @Override
        public Workbook workbook() {
            try (OPCPackage open = OPCPackage.open(stream.file())) {
                XSSFReader reader = new XSSFReader(open);
                SharedStringsTable sst = reader.getSharedStringsTable();

                XMLReader parser = fetchSheetParser(sst);
                // process the first sheet
                InputStream sheet2 = reader.getSheetsData().next();
                InputSource sheetSource = new InputSource(sheet2);
                parser.parse(sheetSource);
                sheet2.close();

                return WorkbookFactory.create(stream.file());
            } catch (IOException | OpenXML4JException | SAXException e) {
                throw new PoijiException("Problem occurred while creating XSSFWorkbook", e);
            }
        }

        private XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
            XMLReader parser =
                    XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
            ContentHandler handler = new PoijiHandler(sst, null, null);
            parser.setContentHandler(handler);
            return parser;
        }
    }

    private static class PoiWorkbookHSSH extends PoijiWorkbook {

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
