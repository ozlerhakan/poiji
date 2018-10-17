package com.poiji.bind.mapping;

import com.poiji.bind.Unmarshaller;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.apache.poi.ooxml.util.SAXHelper;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.IOUtils;
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
import java.util.List;
import java.util.function.Consumer;

import static org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;

/**
 * Created by hakan on 22/10/2017
 */
abstract class XSSFUnmarshaller implements Unmarshaller {

    protected final PoijiOptions options;

    XSSFUnmarshaller(PoijiOptions options) {
        this.options = options;
    }

    <T> void unmarshal0(Class<T> type, Consumer<? super T> consumer, OPCPackage open)
            throws ParserConfigurationException, IOException, SAXException, OpenXML4JException {

        ReadOnlySharedStringsTable readOnlySharedStringsTable = new ReadOnlySharedStringsTable(open);
        XSSFReader workbookReader = new XSSFReader(open);
        StylesTable styles = workbookReader.getStylesTable();
        XMLReader reader = SAXHelper.newXMLReader();

        InputSource is = new InputSource(workbookReader.getWorkbookData());

        reader.setContentHandler(new WorkBookContentHandler(options));
        reader.parse(is);

        WorkBookContentHandler wbch = (WorkBookContentHandler) reader.getContentHandler();
        List<WorkBookSheet> sheets = wbch.getSheets();

        int requestedIndex = options.sheetIndex();
        int nonHiddenSheetIndex = 0;
        int sheetCounter = 0;

        SheetIterator iter = (SheetIterator) workbookReader.getSheetsData();
        while (iter.hasNext()) {
            try (InputStream stream = iter.next()) {
                WorkBookSheet wbs = sheets.get(sheetCounter);
                if (wbs.getState().equals("visible")) {
                    if (nonHiddenSheetIndex == requestedIndex) {
                        processSheet(styles, reader, readOnlySharedStringsTable, type, stream, consumer);
                        return;
                    }
                    nonHiddenSheetIndex++;
                }
            }
            sheetCounter++;
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void processSheet(StylesTable styles,
                                  XMLReader reader,
                                  ReadOnlySharedStringsTable readOnlySharedStringsTable,
                                  Class<T> type,
                                  InputStream sheetInputStream,
                                  Consumer<? super T> consumer) {

        DataFormatter formatter = new DataFormatter();
        InputSource sheetSource = new InputSource(sheetInputStream);
        try {
            PoijiHandler poijiHandler = new PoijiHandler(type, options, consumer);
            ContentHandler contentHandler
                    = new XSSFSheetXMLHandler(styles, null, readOnlySharedStringsTable, poijiHandler, formatter, false);
            reader.setContentHandler(contentHandler);
            reader.parse(sheetSource);
        } catch (SAXException | IOException e) {
            throw new PoijiException("Problem occurred while reading data", e);
        }
    }

    <T> void listOfEncryptedItems(Class<T> type, Consumer<? super T> consumer, POIFSFileSystem fs) throws IOException {
        InputStream stream = DocumentFactoryHelper.getDecryptedStream(fs, options.getPassword());

        try (OPCPackage open = OPCPackage.open(stream)) {
            unmarshal0(type, consumer, open);

        } catch (ParserConfigurationException | SAXException | IOException | OpenXML4JException e) {
            IOUtils.closeQuietly(fs);
            throw new PoijiException("Problem occurred while reading data", e);
        }
    }

    abstract <T> void returnFromExcelFile(Class<T> type, Consumer<? super T> consumer);

    abstract <T> void returnFromEncryptedFile(Class<T> type, Consumer<? super T> consumer);
}
