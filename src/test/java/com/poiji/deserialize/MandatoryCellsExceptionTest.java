package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byname.MandatoryMissingCells;
import com.poiji.exception.PoijiMultiRowException;
import com.poiji.exception.PoijiMultiRowException.PoijiRowSpecificException;
import com.poiji.option.PoijiOptions;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MandatoryCellsExceptionTest {

    @Test
    public void testExcelMandatoryColumn() {
        try {
            Poiji.fromExcel(createDummyExcel(), MandatoryMissingCells.class, PoijiOptions.PoijiOptionsBuilder
                    .settings()
                    .build());
        } catch (PoijiMultiRowException e) {
            List<PoijiRowSpecificException> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertEquals("Address", errors.get(0).getColumnName());
            assertEquals("address", errors.get(0).getFieldName());
            assertEquals((Integer) 1, errors.get(0).getRowNum());
            return;
        }
        fail("Expected exception: " + PoijiMultiRowException.class.getName());
    }

    private Sheet createDummyExcel() {

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Example");

        Row headerRow = sheet.createRow(0);
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("Name");
        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("Address");

        Row dataRow = sheet.createRow(1);
        Cell dataCell1 = dataRow.createCell(0);
        dataCell1.setCellValue("Paul");

        try {
            workbook.close();
            return sheet;
        } catch (IOException e) {
        }
        return sheet;
    }
}
