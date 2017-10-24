package com.poiji.internal;

import com.poiji.exception.InvalidExcelFileExtension;
import com.poiji.internal.marshaller.Deserializer;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import com.poiji.util.Files;

import java.io.File;
import java.util.List;

import static com.poiji.util.PoijiConstants.XLSX_EXTENSION;
import static com.poiji.util.PoijiConstants.XLS_EXTENSION;

/**
 * The main entry point of mapping excel data to Java classes
 * <br>
 * Created by hakan on 16/01/2017.
 */
public final class Poiji {

    private Poiji() {
    }

    public static <T> List<T> fromExcel(File file, Class<T> clazz) {
        final Deserializer unmarshaller = deserializer(file, PoijiOptionsBuilder.settings().build());
        return deserialize(clazz, unmarshaller);
    }

    public static <T> List<T> fromExcel(File file, Class<T> clazz, PoijiOptions options) {
        final Deserializer unmarshaller = deserializer(file, options);
        return deserialize(clazz, unmarshaller);
    }

    @SuppressWarnings("unchecked")
    private static Deserializer deserializer(File file, PoijiOptions options) {
        final PoijiFile poijiFile = new PoijiFile(file);

        String extension = Files.getExtension(file.getName());

        if (XLS_EXTENSION.equals(extension)) {
            PoijiHSSHWorkbook poiWorkbookHSSH = new PoijiHSSHWorkbook(poijiFile);
            return Deserializer.instance(poiWorkbookHSSH, options);
        } else if (XLSX_EXTENSION.equals(extension)) {
            return Deserializer.instance(poijiFile, options);
        } else {
            throw new InvalidExcelFileExtension("Invalid file extension (" + extension + "), excepted .xls or .xlsx");
        }
    }

    private static <T> List<T> deserialize(final Class<T> type, final Deserializer unmarshaller) {
        return unmarshaller.deserialize(type);
    }

}
