package com.poiji.internal;

import com.poiji.exception.InvalidExcelFileExtension;
import com.poiji.internal.mapping.Unmarshaller;
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

    public static <T> List<T> fromExcel(final File file, final Class<T> clazz) {
        final Unmarshaller unmarshaller = deserializer(file, PoijiOptionsBuilder.settings().build());
        return unmarshaller.unmarshal(clazz);
    }

    public static <T> List<T> fromExcel(final File file, final Class<T> clazz, final PoijiOptions options) {
        final Unmarshaller unmarshaller = deserializer(file, options);
        return unmarshaller.unmarshal(clazz);
    }

    @SuppressWarnings("unchecked")
    private static Unmarshaller deserializer(final File file, final PoijiOptions options) {
        final PoijiFile poijiFile = new PoijiFile(file);

        String extension = Files.getExtension(file.getName());

        if (XLS_EXTENSION.equals(extension)) {
            PoijiHSSHWorkbook poiWorkbookHSSH = new PoijiHSSHWorkbook(poijiFile);
            return Unmarshaller.instance(poiWorkbookHSSH, options);
        } else if (XLSX_EXTENSION.equals(extension)) {
            return Unmarshaller.instance(poijiFile, options);
        } else {
            throw new InvalidExcelFileExtension("Invalid file extension (" + extension + "), excepted .xls or .xlsx");
        }
    }
}
