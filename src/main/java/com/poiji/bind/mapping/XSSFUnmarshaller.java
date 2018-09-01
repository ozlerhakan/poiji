package com.poiji.bind.mapping;

import com.poiji.bind.Unmarshaller;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
////
//import java.io.InputStream;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.xssf.eventusermodel.XSSFReader;
//import org.apache.poi.xssf.model.SharedStringsTable;
//import org.apache.xerces.parsers.SAXParser;
//import org.xml.sax.InputSource;
//import org.xml.sax.XMLReader;
//
//import poi.example.eventmodel.SheetHandler;
//import poi.example.eventmodel.WorkbookHandler;
//import poi.example.eventmodel.mapping.annotation.Sheet;

/**
 * Created by hakan on 22/10/2017
 */
abstract class XSSFUnmarshaller implements Unmarshaller {

    protected final PoijiOptions options;

    XSSFUnmarshaller(PoijiOptions options) {
        this.options = options;
    }

    <T> void unmarshal0(Class<T> type, Consumer<? super T> consumer, OPCPackage open) throws IOException, SAXException, OpenXML4JException {

        WorkBookContentHandler wbch = null;
        try {
            XSSFReader workbookReader = new XSSFReader(open);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(new WorkBookContentHandler());
            InputSource is = new InputSource(workbookReader.getWorkbookData());
            reader.parse(is);
            wbch = (WorkBookContentHandler) reader.getContentHandler();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new PoijiException("Problem occurred while reading workbook data", e);
        }

//        System.out.println("wbch" + wbch);
        int processIndex = 0;
        if (wbch != null) {

//            System.out.println("wbch.sheets" + wbch.sheets);
            int findIndex;
            //if given sheet index to use, use that
            if (options.sheetIndex() != null && options.sheetIndex() > -1) {
                findIndex = options.sheetIndex();
            } else {
                //else default
                findIndex = 0;
            }

//            System.out.println("findIndex " + findIndex);
            int sheetIndex;
            //if set to hignore hidden find the visiable sheet that matches the index requested
            if (options.ignoreHiddenSheets()) {
//                System.out.println("ignoreHiddenSheets  true ");
                Integer visiableIndex = null;

                int sheetCount = 0;
                for (WorkBookSheet s : wbch.sheets) {

//                    System.out.println("WorkBookSheet  s " + s);
                    if (s.state == null) {
//                        System.out.println("WorkBookSheet NULL");
                        //cannot use sheet is, cos that is its id not its index, they seem to be diffent things
                        visiableIndex = sheetCount;
                    }

                    sheetCount++;

                }

//                System.out.println("visiableIndex" + visiableIndex);
                if (visiableIndex != null) {
                    sheetIndex = visiableIndex;
                } else {
                    //if no sheet found, default back
                    sheetIndex = findIndex;
                }

//                System.out.println("sheetIndex" + sheetIndex);
            } else {

                //if dont want to ignore hidden sheets, use index given or default
                sheetIndex = findIndex;
//                System.out.println("ELSE sheetIndex" + sheetIndex);
            }
            processIndex = sheetIndex;
        }

//        System.out.println("processIndex" + processIndex);
//        XSSFWorkbook wb = new XSSFWorkbook(open);
//        Workbook workbook = new SXSSFWorkbook(wb);
//        //work out which sheet must process
//        int processIndex = PoijiOptions.getSheetIndexToProcess(workbook, options);
        ReadOnlySharedStringsTable readOnlySharedStringsTable = new ReadOnlySharedStringsTable(open);
        XSSFReader xssfReader = new XSSFReader(open);
        StylesTable styles = xssfReader.getStylesTable();

        SheetIterator iter = (SheetIterator) xssfReader.getSheetsData();
        int index = 0;

        while (iter.hasNext()) {
            try (InputStream stream = iter.next()) {
                if (index == processIndex) {
                    processSheet(styles, readOnlySharedStringsTable, type, stream, consumer);
                    return;
                }
            }
            ++index;
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void processSheet(StylesTable styles,
            ReadOnlySharedStringsTable readOnlySharedStringsTable,
            Class<T> type,
            InputStream sheetInputStream,
            Consumer<? super T> consumer) {

        DataFormatter formatter = new DataFormatter();
        InputSource sheetSource = new InputSource(sheetInputStream);
        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            PoijiHandler poijiHandler = new PoijiHandler(type, options, consumer);
            ContentHandler contentHandler
                    = new XSSFSheetXMLHandler(styles, null, readOnlySharedStringsTable, poijiHandler, formatter, false);
            sheetParser.setContentHandler(contentHandler);
            sheetParser.parse(sheetSource);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new PoijiException("Problem occurred while reading data", e);
        }
    }

    <T> void listOfEncryptedItems(Class<T> type, Consumer<? super T> consumer, NPOIFSFileSystem fs) throws IOException {
        InputStream stream = DocumentFactoryHelper.getDecryptedStream(fs, options.getPassword());

        try (OPCPackage open = OPCPackage.open(stream)) {
            unmarshal0(type, consumer, open);

        } catch (SAXException | IOException | OpenXML4JException e) {
            IOUtils.closeQuietly(fs);
            throw new PoijiException("Problem occurred while reading data", e);
        }
    }

    abstract <T> void returnFromExcelFile(Class<T> type, Consumer<? super T> consumer);

    abstract <T> void returnFromEncryptedFile(Class<T> type, Consumer<? super T> consumer);
}
