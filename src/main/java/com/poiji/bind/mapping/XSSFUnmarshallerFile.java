package com.poiji.bind.mapping;

import com.poiji.bind.PoijiFile;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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

        if (options.getPassword() != null) {
            returnFromEncryptedFile(type, consumer);
            return;
        }
        returnFromExcelFile(type, consumer);
    }

    @Override
    public <T> void returnFromExcelFile(Class<T> type, Consumer<? super T> consumer) {
        executeWithExceptionHandling(() -> {
            try (OPCPackage open = OPCPackage.open(poijiFile.file(), PackageAccess.READ)){
                unmarshal0(type, consumer, open);
            }
        });
    }

    @Override
    public <T> void returnFromEncryptedFile(Class<T> type, Consumer<? super T> consumer) {
        executeWithExceptionHandling(() -> {
            try (POIFSFileSystem fs = new POIFSFileSystem(poijiFile.file(), true)){
                listOfEncryptedItems(type, consumer, fs);
            }
        });

    }

    @FunctionalInterface
    private interface UnmarshalingAction{
        void run() throws Exception;
    }

    private void executeWithExceptionHandling(UnmarshalingAction action){
        try{
            action.run();
        } catch (RuntimeException e) {
            throw e;
        }  catch (ParserConfigurationException | SAXException | IOException | OpenXML4JException e){
            throw new PoijiException("problem occurred reading data", e);
        } catch (Exception e) {
            throw new PoijiException("problem occurred reading data", e);
        }
    }



}
