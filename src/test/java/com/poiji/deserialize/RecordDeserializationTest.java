package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.CalculationRecord;
import com.poiji.deserialize.model.byname.EmployeeRecord;
import com.poiji.deserialize.model.byname.PersonRecord;
import com.poiji.option.PoijiOptions;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests for Java Records support in Poiji
 */
public class RecordDeserializationTest {

    @Test
    public void shouldMapExcelToRecordByName() {
        // Test with employees.xlsx
        List<EmployeeRecord> employees = Poiji.fromExcel(
                new File("src/test/resources/employees.xlsx"), 
                EmployeeRecord.class
        );

        assertThat(employees, notNullValue());
        assertThat(employees.size(), is(3));

        EmployeeRecord firstEmployee = employees.get(0);
        assertThat(firstEmployee.employeeId(), is(123923L));
        assertThat(firstEmployee.name(), is("Joe"));
        assertThat(firstEmployee.surname(), is("Doe"));
        assertThat(firstEmployee.age(), is(30));
        assertThat(firstEmployee.single(), is(true));
        assertThat(firstEmployee.birthday(), is("4/9/1987"));
    }

    @Test
    public void shouldMapExcelToRecordWithRowNumber() {
        // Test with person.xlsx
        List<PersonRecord> people = Poiji.fromExcel(
                new File("src/test/resources/person.xlsx"), 
                PersonRecord.class
        );

        assertThat(people, notNullValue());
        assertThat(people.size(), is(5));

        PersonRecord firstPerson = people.get(0);
        assertThat(firstPerson.name(), is("Rafique"));
        assertThat(firstPerson.row(), is(1)); // Row number should be captured
    }

    @Test
    public void shouldMapXLSToRecord() {
        // Test with XLS format
        List<EmployeeRecord> employees = Poiji.fromExcel(
                new File("src/test/resources/employees.xls"), 
                EmployeeRecord.class
        );

        assertThat(employees, notNullValue());
        assertThat(employees.size(), is(3));

        EmployeeRecord firstEmployee = employees.get(0);
        assertThat(firstEmployee.employeeId(), is(123923L));
        assertThat(firstEmployee.name(), is("Joe"));
    }

    @Test
    public void shouldMapExcelToRecordWithOptions() {
        // Test with options
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .headerStart(0)
                .build();

        List<EmployeeRecord> employees = Poiji.fromExcel(
                new File("src/test/resources/employees.xlsx"), 
                EmployeeRecord.class,
                options
        );

        assertThat(employees, notNullValue());
        assertThat(employees.size(), is(3));
    }

    @Test
    public void shouldMapExcelToRecordWithExcelCell() {
        // Test with @ExcelCell annotation (by index)
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .sheetIndex(1)
                .build();

        List<CalculationRecord> calculations = Poiji.fromExcel(
                new File("src/test/resources/calculations.xlsx"), 
                CalculationRecord.class,
                options
        );

        assertThat(calculations, notNullValue());
        assertThat(calculations.size(), is(4));

        // Verify that records with @ExcelCell work correctly
        for (CalculationRecord calculation : calculations) {
            assertThat(calculation.fromDate().toString(), is("2018-01-01"));
            assertThat(calculation.toDate().toString(), is("2018-06-30"));
        }
    }

    @Test
    public void shouldMapExcelToRecordFromInputStream() {
        // Test with InputStream
        try (java.io.FileInputStream stream = new java.io.FileInputStream(
                new File("src/test/resources/employees.xlsx"))) {
            List<EmployeeRecord> employees = Poiji.fromExcel(
                    stream,
                    com.poiji.exception.PoijiExcelType.XLSX,
                    EmployeeRecord.class
            );

            assertThat(employees, notNullValue());
            assertThat(employees.size(), is(3));

            EmployeeRecord firstEmployee = employees.get(0);
            assertThat(firstEmployee.employeeId(), is(123923L));
            assertThat(firstEmployee.name(), is("Joe"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldMapExcelToRecordFromInputStreamWithOptions() {
        // Test with InputStream and options
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .headerStart(0)
                .build();

        try (java.io.FileInputStream stream = new java.io.FileInputStream(
                new File("src/test/resources/employees.xlsx"))) {
            List<EmployeeRecord> employees = Poiji.fromExcel(
                    stream,
                    com.poiji.exception.PoijiExcelType.XLSX,
                    EmployeeRecord.class,
                    options
            );

            assertThat(employees, notNullValue());
            assertThat(employees.size(), is(3));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldMapXLSFromInputStream() {
        // Test XLS with InputStream
        try (java.io.FileInputStream stream = new java.io.FileInputStream(
                new File("src/test/resources/employees.xls"))) {
            List<EmployeeRecord> employees = Poiji.fromExcel(
                    stream,
                    com.poiji.exception.PoijiExcelType.XLS,
                    EmployeeRecord.class
            );

            assertThat(employees, notNullValue());
            assertThat(employees.size(), is(3));

            EmployeeRecord firstEmployee = employees.get(0);
            assertThat(firstEmployee.employeeId(), is(123923L));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldHandleRecordWithConsumer() {
        // Test with consumer interface
        final java.util.List<EmployeeRecord> collectedEmployees = new java.util.ArrayList<>();
        
        Poiji.fromExcel(
                new File("src/test/resources/employees.xlsx"),
                EmployeeRecord.class,
                collectedEmployees::add
        );

        assertThat(collectedEmployees.size(), is(3));
        assertThat(collectedEmployees.get(0).name(), is("Joe"));
    }

    @Test
    public void shouldHandleRecordWithConsumerAndOptions() {
        // Test with consumer interface and options
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .headerStart(0)
                .build();

        final java.util.List<EmployeeRecord> collectedEmployees = new java.util.ArrayList<>();
        
        Poiji.fromExcel(
                new File("src/test/resources/employees.xlsx"),
                EmployeeRecord.class,
                options,
                collectedEmployees::add
        );

        assertThat(collectedEmployees.size(), is(3));
    }

    @Test
    public void shouldMapExcelToRecordFromSheet() {
        // Test with Sheet API
        try {
            org.apache.poi.ss.usermodel.Workbook workbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(
                    new File("src/test/resources/employees.xlsx"));
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            List<EmployeeRecord> employees = Poiji.fromExcel(
                    sheet,
                    EmployeeRecord.class
            );

            assertThat(employees, notNullValue());
            assertThat(employees.size(), is(3));
            assertThat(employees.get(0).name(), is("Joe"));

            workbook.close();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldMapExcelToRecordFromSheetWithOptions() {
        // Test with Sheet API and options
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .headerStart(0)
                .build();

        try {
            org.apache.poi.ss.usermodel.Workbook workbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(
                    new File("src/test/resources/employees.xlsx"));
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            List<EmployeeRecord> employees = Poiji.fromExcel(
                    sheet,
                    EmployeeRecord.class,
                    options
            );

            assertThat(employees, notNullValue());
            assertThat(employees.size(), is(3));

            workbook.close();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldMapExcelToRecordFromSheetWithConsumer() {
        // Test with Sheet API and consumer
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .headerStart(0)
                .build();

        final java.util.List<EmployeeRecord> collectedEmployees = new java.util.ArrayList<>();

        try {
            org.apache.poi.ss.usermodel.Workbook workbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(
                    new File("src/test/resources/employees.xlsx"));
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            Poiji.fromExcel(
                    sheet,
                    EmployeeRecord.class,
                    options,
                    collectedEmployees::add
            );

            assertThat(collectedEmployees.size(), is(3));
            assertThat(collectedEmployees.get(0).name(), is("Joe"));

            workbook.close();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
