package com.poiji.bind.mapping;

import com.poiji.bind.PoijiInputStream;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

/**
 * Created by hakan on 22/10/2017
 */
final class XSSFUnmarshallerStream extends XSSFUnmarshaller  {

    private final PoijiInputStream poijiInputStream;

    XSSFUnmarshallerStream(PoijiInputStream poijiInputStream, PoijiOptions options) {
        super(options);
        this.poijiInputStream = poijiInputStream;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> unmarshal(Class<T> type) {
        try (OPCPackage open = OPCPackage.open(poijiInputStream.stream())) {

            return unmarshal0(type, open);

        } catch (SAXException | IOException | OpenXML4JException e) {
            throw new PoijiException("Problem occurred while reading data", e);
        }
    }
}
