package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Employee;
import com.poiji.exception.InvalidExcelFileExtension;
import com.poiji.option.PoijiOptions;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test for ignoreFileExtension option
 */
public class IgnoreFileExtensionTest {

    @Test(expected = InvalidExcelFileExtension.class)
    public void shouldThrowExceptionWhenFileHasNoExtension() {
        Poiji.fromExcel(new File("src/test/resources/employees_no_extension"), Employee.class);
    }

    @Test
    public void shouldReadFileWithNoExtensionWhenIgnoreFileExtensionIsEnabled() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder
                .settings()
                .ignoreFileExtension(true)
                .build();

        List<Employee> employees = Poiji.fromExcel(
                new File("src/test/resources/employees_no_extension"),
                Employee.class,
                options);

        assertThat(employees, notNullValue());
        assertThat(employees.size(), is(3));

        Employee firstEmployee = employees.get(0);
        assertThat(firstEmployee.getEmployeeId(), is(123923L));
        assertThat(firstEmployee.getName(), is("Joe"));
        assertThat(firstEmployee.getSurname(), is("Doe"));
        assertThat(firstEmployee.getAge(), is(30));
        assertThat(firstEmployee.isSingle(), is(true));
        assertThat(firstEmployee.getBirthday(), is("4/9/1987"));
    }

    @Test
    public void shouldReadFileWithInvalidExtensionWhenIgnoreFileExtensionIsEnabled() {
        // Use the test file with an invalid extension
        File targetFile = new File("src/test/resources/employees_invalid.dat");

        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder
                .settings()
                .ignoreFileExtension(true)
                .build();

        List<Employee> employees = Poiji.fromExcel(targetFile, Employee.class, options);

        assertThat(employees, notNullValue());
        assertThat(employees.size(), is(3));
    }
}
