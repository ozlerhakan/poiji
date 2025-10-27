package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Person;
import com.poiji.option.PoijiOptions;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests for code paths in modified classes to maintain coverage
 * These tests use POJOs to ensure they work on Java 11
 */
public class ModifiedClassesCoverageTest {

    @Test
    public void shouldMapExcelWithPojoUsingFile() {
        // Test File API with POJO to ensure non-record path is covered
        List<Person> people = Poiji.fromExcel(
                new File("src/test/resources/person.xlsx"),
                Person.class
        );

        assertThat(people, notNullValue());
        assertThat(people.size(), is(5));
        assertThat(people.get(0).getName(), is("Rafique"));
    }

    @Test
    public void shouldMapExcelWithPojoUsingFileAndOptions() {
        // Test File API with options and POJO
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .headerStart(0)
                .build();

        List<Person> people = Poiji.fromExcel(
                new File("src/test/resources/person.xlsx"),
                Person.class,
                options
        );

        assertThat(people, notNullValue());
        assertThat(people.size(), is(5));
    }

    @Test
    public void shouldMapExcelWithPojoUsingConsumer() {
        // Test consumer interface with POJO
        final java.util.List<Person> collectedPeople = new java.util.ArrayList<>();

        Poiji.fromExcel(
                new File("src/test/resources/person.xlsx"),
                Person.class,
                collectedPeople::add
        );

        assertThat(collectedPeople.size(), is(5));
        assertThat(collectedPeople.get(0).getName(), is("Rafique"));
    }

    @Test
    public void shouldMapExcelWithPojoUsingInputStream() {
        // Test InputStream API with POJO
        try (java.io.FileInputStream stream = new java.io.FileInputStream(
                "src/test/resources/person.xlsx")) {
            List<Person> people = Poiji.fromExcel(
                    stream,
                    com.poiji.exception.PoijiExcelType.XLSX,
                    Person.class
            );

            assertThat(people, notNullValue());
            assertThat(people.size(), is(5));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldMapXLSWithPojoUsingInputStream() {
        // Test XLS InputStream API with POJO
        try (java.io.FileInputStream stream = new java.io.FileInputStream(
                "src/test/resources/person.xls")) {
            List<Person> people = Poiji.fromExcel(
                    stream,
                    com.poiji.exception.PoijiExcelType.XLS,
                    Person.class
            );

            assertThat(people, notNullValue());
            assertThat(people.size(), is(5));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldMapExcelWithPojoUsingSheet() {
        // Test Sheet API with POJO
        try {
            org.apache.poi.ss.usermodel.Workbook workbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(
                    new File("src/test/resources/person.xlsx"));
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            List<Person> people = Poiji.fromExcel(
                    sheet,
                    Person.class
            );

            assertThat(people, notNullValue());
            assertThat(people.size(), is(5));

            workbook.close();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldMapExcelWithPojoUsingSheetAndOptions() {
        // Test Sheet API with options and POJO
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .headerStart(0)
                .build();

        try {
            org.apache.poi.ss.usermodel.Workbook workbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(
                    new File("src/test/resources/person.xlsx"));
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            List<Person> people = Poiji.fromExcel(
                    sheet,
                    Person.class,
                    options
            );

            assertThat(people, notNullValue());
            assertThat(people.size(), is(5));

            workbook.close();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldMapExcelWithPojoUsingSheetAndConsumer() {
        // Test Sheet API with consumer and POJO
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .headerStart(0)
                .build();

        final java.util.List<Person> collectedPeople = new java.util.ArrayList<>();

        try {
            org.apache.poi.ss.usermodel.Workbook workbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(
                    new File("src/test/resources/person.xlsx"));
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            Poiji.fromExcel(
                    sheet,
                    Person.class,
                    options,
                    collectedPeople::add
            );

            assertThat(collectedPeople.size(), is(5));

            workbook.close();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
