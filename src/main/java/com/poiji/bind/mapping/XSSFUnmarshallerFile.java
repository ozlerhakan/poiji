package com.poiji.bind.mapping;

import com.poiji.bind.PoijiFile;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Created by hakan on 22/10/2017
 */
final class XSSFUnmarshallerFile extends XSSFUnmarshaller {

    private final PoijiFile poijiFile;

    XSSFUnmarshallerFile(PoijiFile poijiFile, PoijiOptions options) {
        super(options);
        this.poijiFile = poijiFile;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void unmarshal(Class<T> type, Consumer<? super T> consumer) {

        if (options.getPassword() != null) {
            returnFromEncryptedFile(type, consumer);
            return;
        }
        returnFromExcelFile(type,consumer);
    }

    public <T> void returnFromExcelFile(Class<T> type, Consumer<? super T> consumer) {

        try (OPCPackage open = OPCPackage.open(poijiFile.file(), PackageAccess.READ)) {

            unmarshal0(type, consumer, open);

        } catch (ParserConfigurationException | SAXException | IOException | OpenXML4JException e) {
            throw new PoijiException("Problem occurred while reading data", e);
        }
    }

    public <T> void returnFromEncryptedFile(Class<T> type, Consumer<? super T> consumer) {

        try (NPOIFSFileSystem fs = new NPOIFSFileSystem(poijiFile.file(), true)) {

            listOfEncryptedItems(type, consumer, fs);

        } catch (IOException e) {
            throw new PoijiException("Problem occurred while reading data", e);
        }
    }

}
