package com.poiji.util;

import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public final class ExcelFileOpenUtil {

    private ExcelFileOpenUtil() {
    }

    public static OPCPackage openXlsxFile(final File file, final PoijiOptions options) {
        if (options.getPassword() != null) {
            try (POIFSFileSystem fs = new POIFSFileSystem(file, true);
                 InputStream decryptedStream = DocumentFactoryHelper.getDecryptedStream(fs, options.getPassword())) {

                return OPCPackage.open(decryptedStream);
            } catch (IOException | InvalidFormatException e) {
                throw new PoijiException("Problem occurred while reading data", e);
            }
        } else {
            try {
                return OPCPackage.open(file, PackageAccess.READ);
            } catch (InvalidFormatException e) {
                throw new PoijiException("Problem occurred while reading data", e);
            }
        }
    }

    public static OPCPackage openXlsxFile(final InputStream inputStream, final PoijiOptions options) {
        if (options.getPassword() != null) {
            try (POIFSFileSystem fs = new POIFSFileSystem(inputStream);
                 InputStream decryptedStream = DocumentFactoryHelper.getDecryptedStream(fs, options.getPassword())) {

                return OPCPackage.open(decryptedStream);
            } catch (IOException | InvalidFormatException e) {
                throw new PoijiException("Problem occurred while reading data", e);
            }
        } else {
            try {
                return OPCPackage.open(inputStream);
            } catch (InvalidFormatException | IOException e) {
                throw new PoijiException("Problem occurred while reading data", e);
            }
        }
    }
}
