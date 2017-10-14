package com.poiji.internal;

import com.poiji.internal.PoijiOptions.PoijiOptionsBuilder;
import com.poiji.internal.marshaller.Deserializer;
import com.poiji.util.Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * The main entry point of mapping excel data to Java classes
 * <br>
 * Created by hakan on 16/01/2017.
 */
public final class Poiji {

    private Poiji() {
    }

    public static <T> List<T> fromExcel(File file, Class<T> clazz) throws FileNotFoundException {
        final Deserializer unmarshaller = deserializer(file,  PoijiOptionsBuilder.settings().build());
        return deserialize(clazz, unmarshaller);
    }

    public static <T> List<T> fromExcel(File file, Class<T> clazz, PoijiOptions options) throws FileNotFoundException {
        final Deserializer unmarshaller = deserializer(file, options);
        return deserialize(clazz, unmarshaller);
    }

    @SuppressWarnings("unchecked")
    private static Deserializer deserializer(File file, PoijiOptions options) throws FileNotFoundException {
        final PoijiStream poiParser = new PoijiStream(fileInputStream(file));
        final PoiWorkbook workbook = PoiWorkbook.workbook(Files.getExtension(file.getName()), poiParser);
        return Deserializer.instance(workbook, options);
    }

    private static <T> List<T> deserialize(final Class<T> type, final Deserializer unmarshaller) {
        return unmarshaller.deserialize(type);
    }

    private static FileInputStream fileInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

}
