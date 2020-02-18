package com.poiji.save;

import java.util.Collection;
import java.util.stream.Stream;
import org.apache.poi.ss.usermodel.Workbook;

public interface WorkbookSaver {

    <T> void save(final Collection<T> data, final Workbook workbook);

    <T> void save(final Stream<T> data, final Workbook workbook);

}
