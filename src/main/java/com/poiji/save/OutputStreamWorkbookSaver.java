package com.poiji.save;

import com.poiji.option.PoijiOptions;
import java.io.OutputStream;
import java.util.Collection;
import java.util.stream.Stream;
import org.apache.poi.ss.usermodel.Workbook;

public class OutputStreamWorkbookSaver extends AbstractWorkbookSaver implements WorkbookSaver{

    private final OutputStream outputStream;

    public OutputStreamWorkbookSaver(
        final OutputStream outputStream, final MappedFields mappedFields, final PoijiOptions options
    ) {
        super(mappedFields, options);
        this.outputStream = outputStream;
    }

    @Override
    public <T> void save(final Stream<T> data, final Workbook workbook) {
        super.save(data, workbook, outputStream);
    }

    @Override
    public  <T> void save(final Collection<T> data, final Workbook workbook) {
        super.save(data, workbook, outputStream);
    }

}
