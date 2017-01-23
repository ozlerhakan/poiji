package com.poiji.deserialize;

import com.poiji.deserialize.model.Employee;
import com.poiji.internal.Poiji;
import com.poiji.internal.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.poiji.internal.PoijiOptions.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by hakan on 17/01/2017.
 */
@RunWith(Parameterized.class)
public class DerializersEmptyCellTest {

    private String path;
    private List<Employee> expectedEmployess;
    private Class<?> expectedException;

    public DerializersEmptyCellTest(String path, List<Employee> expectedEmployess, Class<?> expectedException) {
        this.path = path;
        this.expectedEmployess = expectedEmployess;
        this.expectedException = expectedException;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() throws Exception {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/employeeswithemptycells.xlsx", unmarshalling(), null},
                {"src/test/resources/fruits.xlsx", unmarshalling(), FileNotFoundException.class},
        });
    }

    @Test
    public void shouldMapEmptyCellsAndSkipTwoRows() {

        try {
            PoijiOptions options = PoijiOptionsBuilder.settings(2).build();
            List<Employee> actualEmployees = Poiji.fromExcel(new File(path), Employee.class, options);

            assertThat(actualEmployees, notNullValue());
            assertThat(actualEmployees.size(), not(0));
            assertThat(actualEmployees.size(), is(expectedEmployess.size()));

            Employee actualEmployee1 = actualEmployees.get(0);
            Employee actualEmployee2 = actualEmployees.get(1);

            Employee expectedEmployee1 = expectedEmployess.get(0);
            Employee expectedEmployee2 = expectedEmployess.get(1);

            assertThat(actualEmployee1.toString(), is(expectedEmployee1.toString()));
            assertThat(actualEmployee2.toString(), is(expectedEmployee2.toString()));

        } catch (FileNotFoundException e) {
            if (expectedException == null) {
                fail(e.getMessage());
            } else {
                assertThat(e, instanceOf(expectedException));
            }
        }
    }

    private static List<Employee> unmarshalling() {
        List<Employee> employees = new ArrayList<>(2);

        Employee employee1 = new Employee();
        employee1.setEmployeeId(123123L);
        employee1.setName("");
        employee1.setSurname("Derue");
        employee1.setSingle(false);
        employee1.setAge(0);
        employee1.setBirthday("5/3/1997");
        employees.add(employee1);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(135923L);
        employee2.setName("Paul");
        employee2.setSurname("Raul");
        employee2.setSingle(false);
        employee2.setAge(31);
        employee2.setBirthday("4/9/1986");
        employees.add(employee2);

        return employees;
    }
}
