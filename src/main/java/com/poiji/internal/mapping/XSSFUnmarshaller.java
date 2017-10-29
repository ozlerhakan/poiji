package com.poiji.internal.mapping;

import com.poiji.exception.PoijiException;
import com.poiji.internal.PoijiFile;
import com.poiji.option.PoijiOptions;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
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

import static org.apache.poi.xssf.eventusermodel.XSSFReader.*;

/**
 * Created by hakan on 22/10/2017
 */
final class XSSFUnmarshaller extends Unmarshaller {

    private final PoijiFile poijiFile;
    private final PoijiOptions options;

    XSSFUnmarshaller(PoijiFile poijiFile, PoijiOptions options) {
        this.poijiFile = poijiFile;
        this.options = options;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> unmarshal(Class<T> type) {
        try (OPCPackage open = OPCPackage.open(poijiFile.file())) {

            ReadOnlySharedStringsTable readOnlySharedStringsTable = new ReadOnlySharedStringsTable(open);
            XSSFReader xssfReader = new XSSFReader(open);
            StylesTable styles = xssfReader.getStylesTable();

            SheetIterator iter = (SheetIterator) xssfReader.getSheetsData();
            int index = 0;

            while (iter.hasNext()) {
                InputStream stream = iter.next();
                if (index == options.sheetIndex()) {
                    return processSheet(styles, readOnlySharedStringsTable, type, stream);
                }
                stream.close();
                ++index;
            }
            return new ArrayList<>();
        } catch (SAXException | IOException | OpenXML4JException e) {
            throw new PoijiException("Problem occurred while reading data", e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> processSheet(StylesTable styles, ReadOnlySharedStringsTable readOnlySharedStringsTable,
                                  Class<T> type, InputStream sheetInputStream) {

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
}
