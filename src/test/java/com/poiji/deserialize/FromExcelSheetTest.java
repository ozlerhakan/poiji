package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byname.CellFormatModel;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by hakan on 11.10.2020
 */
public class FromExcelSheetTest {

    @Test
    public void shouldMapXLSSheet() throws IOException {

        File file = new File("src/test/resources/employees_format.xls");
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        List<CellFormatModel> result = Poiji.fromExcel(sheet, CellFormatModel.class);

        assertThat(result, notNullValue());
        CellFormatModel cellFormatModel = result.get(1);
        assertThat(cellFormatModel.getAge(), is(40));
    }

    @Test
    public void shouldMapXLSXSheet() throws IOException {

        File file = new File("src/test/resources/employees_format.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        List<CellFormatModel> result = Poiji.fromExcel(sheet, CellFormatModel.class);

        assertThat(result, notNullValue());
        CellFormatModel cellFormatModel = result.get(1);
        assertThat(cellFormatModel.getAge(), is(40));
    }


    @Test
    public void shouldMapXLSXSheetWithOptions() throws IOException {

        File file = new File("src/test/resources/employees_format.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        List<CellFormatModel> result = Poiji.fromExcel(sheet, CellFormatModel.class, PoijiOptions.PoijiOptionsBuilder.settings().build());

        assertThat(result, notNullValue());
        CellFormatModel cellFormatModel = result.get(1);
        assertThat(cellFormatModel.getAge(), is(40));
    }

    @Test
    public void shouldMapXLSXSheetWithOptionsConsumer() throws IOException {

        File file = new File("src/test/resources/employees_format.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        Poiji.fromExcel(sheet, CellFormatModel.class, PoijiOptions.PoijiOptionsBuilder.settings().build(), this::printout);
    }

    private void printout(CellFormatModel instance) {
        assertThat(instance, notNullValue());
    }

    @Test(expected = PoijiException.class)
    public void shouldMapXLSXSheetError() throws IOException {
        File file = new File("src/test/resources/employees_format.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
        Workbook workbook = new SXSSFWorkbook(xssfWorkbook);
        Sheet sheet = workbook.getSheetAt(0);
        Poiji.fromExcel(sheet, CellFormatModel.class);
    }
}
