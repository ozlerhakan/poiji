package com.poiji.bind.mapping;

import com.poiji.bind.PoijiFile;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import com.poiji.util.ExcelFileOpenUtil;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Created by hakan on 22/10/2017
 */
final class XSSFUnmarshallerFile extends XSSFUnmarshaller {

    private final PoijiFile<?> poijiFile;

    XSSFUnmarshallerFile(PoijiFile<?> poijiFile, PoijiOptions options) {
        super(options);
        this.poijiFile = poijiFile;
    }

    @Override
    public <T> void unmarshal(Class<T> type, Consumer<? super T> consumer) {

        returnFromExcelFile(type, consumer);
    }

    public <T> void returnFromExcelFile(Class<T> type, Consumer<? super T> consumer) {

        try (OPCPackage open = ExcelFileOpenUtil.openXlsxFile(poijiFile.file(), options)) {

            unmarshal0(type, consumer, open);

        } catch (ParserConfigurationException | SAXException | IOException | OpenXML4JException e) {
            throw new PoijiException("Problem occurred while reading data", e);
        }
    }
}
