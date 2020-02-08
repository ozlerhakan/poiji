package com.poiji.save;

import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;

public class FileWorkbookSaver extends WorkbookSaver {

    private final File file;

    public FileWorkbookSaver(
        final File file, final MappedFields mappedFields, final PoijiOptions options
    ) {
        super(mappedFields, options);
        this.file = file;
    }

    protected <T> void save(final List<T> data, final Workbook workbook) {
        createFile();
        writeInFile(data, workbook);
    }

    private <T> void writeInFile(final List<T> data, final Workbook workbook) {
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            save(data, workbook, outputStream);
        } catch (IOException e) {
            throw new PoijiException(e.getMessage(), e);
        }
    }

    private void createFile() {
        final Path absolutePath = file.toPath().toAbsolutePath();
        try {
            Files.createDirectories(absolutePath.getParent());
            Files.deleteIfExists(absolutePath);
            Files.createFile(absolutePath);
        } catch (IOException e) {
            throw new PoijiException(e.getMessage(), e);
        }
    }

}
