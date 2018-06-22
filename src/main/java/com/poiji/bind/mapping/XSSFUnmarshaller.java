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
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;

/**
 * Created by hakan on 22/10/2017
 */
abstract class XSSFUnmarshaller implements Unmarshaller {

    protected final PoijiOptions options;

    XSSFUnmarshaller(PoijiOptions options) {
        this.options = options;
    }

    <T> List<T> unmarshal0(Class<T> type, OPCPackage open) throws IOException, SAXException, OpenXML4JException {

        ReadOnlySharedStringsTable readOnlySharedStringsTable = new ReadOnlySharedStringsTable(open);
        XSSFReader xssfReader = new XSSFReader(open);
        StylesTable styles = xssfReader.getStylesTable();

        SheetIterator iter = (SheetIterator) xssfReader.getSheetsData();
        int index = 0;

        while (iter.hasNext()) {
            try (InputStream stream = iter.next()) {
                if (index == options.sheetIndex()) {
                    return processSheet(styles, readOnlySharedStringsTable, type, stream);
                }
            }
            ++index;
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> processSheet(StylesTable styles,
                                     ReadOnlySharedStringsTable readOnlySharedStringsTable,
                                     Class<T> type,
                                     InputStream sheetInputStream) {

        DataFormatter formatter = new DataFormatter();
        InputSource sheetSource = new InputSource(sheetInputStream);
        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            PoijiHandler poijiHandler = new PoijiHandler(type, options);
            ContentHandler contentHandler =
                    new XSSFSheetXMLHandler(styles, null, readOnlySharedStringsTable, poijiHandler, formatter, false);
            sheetParser.setContentHandler(contentHandler);
            sheetParser.parse(sheetSource);
            return poijiHandler.getDataset();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new PoijiException("Problem occurred while reading data", e);
        }
    }

    <T> List<T> listOfEncryptedItems(Class<T> type, NPOIFSFileSystem fs) throws IOException {
        InputStream stream = DocumentFactoryHelper.getDecryptedStream(fs, options.getPassword());

        try (OPCPackage open = OPCPackage.open(stream)) {
            return unmarshal0(type, open);

        } catch (SAXException | IOException | OpenXML4JException e) {
            IOUtils.closeQuietly(fs);
            throw new PoijiException("Problem occurred while reading data", e);
        }
    }

    abstract <T> List<T> returnFromExcelFile(Class<T> type);

    abstract <T> List<T> returnFromEncryptedFile(Class<T> type);
}
