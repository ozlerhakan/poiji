package com.poiji.deserialize;

import com.poiji.deserialize.model.Employee;
import com.poiji.exception.PoijiException;
import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class DerializersTest {

    private String path;
    private List<Employee> expectedEmployess;
    private Class<?> expectedException;
    private int indexSheet;

    public DerializersTest(String path,
                           List<Employee> expectedEmployess,
                           Class<?> expectedException,
                           int indexSheet) {
        this.path = path;
        this.expectedEmployess = expectedEmployess;
        this.expectedException = expectedException;
        this.indexSheet = indexSheet;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() throws Exception {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/employees.xlsx", unmarshalling(), null, -1},
                {"src/test/resources/employees_sheet2.xlsx", unmarshalling(), null, 1},
                {"src/test/resources/cloud.xls", unmarshalling(), PoijiException.class, -1},
                {"src/test/resources/cloud", unmarshalling(), PoijiException.class, -1},
        });
    }

    @Test
    public void shouldMapExcelToJava() {

        try {

            List<Employee> actualEmployees;
            if (indexSheet == 1) {
                PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(indexSheet).build();
                actualEmployees = Poiji.fromExcel(new File(path), Employee.class, poijiOptions);
            } else {
                actualEmployees = Poiji.fromExcel(new File(path), Employee.class);
            }

            assertThat(actualEmployees, notNullValue());
            assertThat(actualEmployees.size(), not(0));
            assertThat(actualEmployees.size(), is(expectedEmployess.size()));

            Employee actualEmployee1 = actualEmployees.get(0);
            Employee actualEmployee2 = actualEmployees.get(1);
            Employee actualEmployee3 = actualEmployees.get(2);

            Employee expectedEmployee1 = expectedEmployess.get(0);
            Employee expectedEmployee2 = expectedEmployess.get(1);
            Employee expectedEmployee3 = expectedEmployess.get(2);

            assertThat(actualEmployee1.toString(), is(expectedEmployee1.toString()));
            assertThat(actualEmployee2.toString(), is(expectedEmployee2.toString()));
            assertThat(actualEmployee3.toString(), is(expectedEmployee3.toString()));
        } catch (Exception e) {
            if (expectedException == null) {
                fail(e.getMessage());
            } else {
                assertThat(e, instanceOf(expectedException));
            }
        }
    }

    private static List<Employee> unmarshalling() {
        List<Employee> employees = new ArrayList<>(3);

        Employee employee1 = new Employee();
        employee1.setEmployeeId(123923L);
        employee1.setName("Joe");
        employee1.setSurname("Doe");
        employee1.setSingle(true);
        employee1.setAge(30);
        employee1.setBirthday("4/9/1987");
        employees.add(employee1);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(123123L);
        employee2.setName("Sophie");
        employee2.setSurname("Derue");
        employee2.setSingle(false);
        employee2.setAge(20);
        employee2.setBirthday("5/3/1997");
        employees.add(employee2);

        Employee employee3 = new Employee();
        employee3.setEmployeeId(135923L);
        employee3.setName("Paul");
        employee3.setSurname("Raul");
        employee3.setSingle(false);
        employee3.setAge(31);
        employee3.setBirthday("4/9/1986");
        employees.add(employee3);

        return employees;
    }
}
