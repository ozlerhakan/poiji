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
import org.apache.poi.ss.usermodel.Workbook;

import static org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by hakan on 22/10/2017
 */
abstract class XSSFUnmarshaller implements Unmarshaller {

    protected final PoijiOptions options;

    XSSFUnmarshaller(PoijiOptions options) {
        this.options = options;
    }

    <T> void unmarshal0(Class<T> type, Consumer<? super T> consumer, OPCPackage open) throws IOException, SAXException, OpenXML4JException {

        //ISSUE #55
        XSSFWorkbook wb = new XSSFWorkbook(open);
        Workbook workbook = new SXSSFWorkbook(wb);
        //work out which sheet must process
        int processIndex = PoijiOptions.getSheetIndexToProcess(workbook, options);

        ReadOnlySharedStringsTable readOnlySharedStringsTable = new ReadOnlySharedStringsTable(open);
        XSSFReader xssfReader = new XSSFReader(open);
        StylesTable styles = xssfReader.getStylesTable();

        SheetIterator iter = (SheetIterator) xssfReader.getSheetsData();
        int index = 0;

        while (iter.hasNext()) {
            try (InputStream stream = iter.next()) {
                //if (index == options.sheetIndex()) {
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
