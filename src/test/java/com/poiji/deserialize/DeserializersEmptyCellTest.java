package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Employee;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by hakan on 17/01/2017.
 */
@RunWith(Parameterized.class)
public class DeserializersEmptyCellTest {

    private String path;
    private List<Employee> expectedEmployess;
    private boolean fromStream;

    public DeserializersEmptyCellTest(String path, List<Employee> expectedEmployess, boolean fromStream) {
        this.path = path;
        this.expectedEmployess = expectedEmployess;
        this.fromStream = fromStream;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/employeeswithemptycells.xlsx", unmarshalling(), false},
                {"src/test/resources/employeeswithemptycells.xlsx", unmarshalling(), true},
        });
    }

    @Test
    public void shouldMapEmptyCellsAndSkipTwoRows() {

        PoijiOptions options = PoijiOptionsBuilder.settings(1).build();
        List<Employee> actualEmployees;

        if (fromStream) {
            try (InputStream stream = new FileInputStream(new File(path))) {
                actualEmployees = Poiji.fromExcel(stream, PoijiExcelType.XLSX, Employee.class, options);
            } catch (IOException e) {
                fail(e.getMessage());
                return;
            }
        } else {
            actualEmployees = Poiji.fromExcel(new File(path), Employee.class, options);
        }

        assertThat(actualEmployees, notNullValue());
        assertThat(actualEmployees.size(), not(0));
        assertThat(actualEmployees.size(), is(expectedEmployess.size()));

        Employee actualEmployee1 = actualEmployees.get(0);
        Employee actualEmployee2 = actualEmployees.get(1);

        Employee expectedEmployee1 = expectedEmployess.get(0);
        Employee expectedEmployee2 = expectedEmployess.get(1);

        assertThat(actualEmployee1.toString(), is(expectedEmployee1.toString()));
        assertThat(actualEmployee2.toString(), is(expectedEmployee2.toString()));

    }

    private static List<Employee> unmarshalling() {
        List<Employee> employees = new ArrayList<>(2);

        Employee employee1 = new Employee();
        employee1.setEmployeeId(123123L);
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
