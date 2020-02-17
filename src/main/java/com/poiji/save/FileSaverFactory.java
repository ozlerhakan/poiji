package com.poiji.save;

import com.poiji.exception.InvalidExcelFileExtension;
import com.poiji.option.PoijiOptions;
import java.io.File;

import static com.poiji.util.PoijiConstants.XLSX_EXTENSION;
import static com.poiji.util.PoijiConstants.XLS_EXTENSION;

public final class FileSaverFactory<T> {

    private final Class<T> entity;
    private final PoijiOptions options;

    public FileSaverFactory(final Class<T> entity, final PoijiOptions options) {
        this.entity = entity;
        this.options = options;
    }

    public FileSaver toFile(final File file) {
        final MappedFields mappedFields = new MappedFields(entity, options).parseEntity();
        if (file.toString().endsWith(XLSX_EXTENSION)) {
            return new XlsxFileSaver(new FileWorkbookSaver(file, mappedFields, options), options);
        } else if (file.toString().endsWith(XLS_EXTENSION)) {
            return new XlsFileSaver(new FileWorkbookSaver(file, mappedFields, options), options);
        } else {
            throw new InvalidExcelFileExtension(file.getName() + " has unsupported extension. 'xlsx' and 'xls' are supported only.");
        }
    }

}
