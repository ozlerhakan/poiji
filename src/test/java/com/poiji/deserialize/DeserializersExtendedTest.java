package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.EmployeeExtended;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by hakan on 17/01/2017.
 */
@RunWith(Parameterized.class)
public class DeserializersExtendedTest {

    private String path;
    private List<EmployeeExtended> expectedEmployess;
    private Class<?> expectedException;

    public DeserializersExtendedTest(String path, List<EmployeeExtended> expectedEmployess, Class<?> expectedException) {
        this.path = path;
        this.expectedEmployess = expectedEmployess;
        this.expectedException = expectedException;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() throws Exception {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/employees_extended.xls", unmarshalling(), null},
                {"src/test/resources/cloud.xls", unmarshalling(), PoijiException.class},
        });
    }

    @Test
    public void shouldMapExcelToJava() {

        try {
            PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().datePattern("dd/mm/yyyy").build();
            List<EmployeeExtended> actualEmployees = Poiji.fromExcel(new File(path), EmployeeExtended.class, options);

            assertThat(actualEmployees, notNullValue());
            assertThat(actualEmployees.size(), not(0));
            assertThat(actualEmployees.size(), is(expectedEmployess.size()));


            EmployeeExtended actualEmployee1 = actualEmployees.get(0);
            EmployeeExtended actualEmployee2 = actualEmployees.get(1);
            EmployeeExtended actualEmployee3 = actualEmployees.get(2);

            EmployeeExtended expectedEmployee1 = expectedEmployess.get(0);
            EmployeeExtended expectedEmployee2 = expectedEmployess.get(1);
            EmployeeExtended expectedEmployee3 = expectedEmployess.get(2);

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

    @Test
    public void shouldMapExcelDatesToJava() throws Exception {

        File excelFile =
                new File(this.getClass().getResource("/employees_extended.xls").getPath());

        PoijiOptions options = PoijiOptionsBuilder.settings()
                .datePattern("dd/MM/yyyy")
                .build();

        List<EmployeeExtended> actualEmployees =
                Poiji.fromExcel(excelFile, EmployeeExtended.class, options);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        assertEquals(actualEmployees.get(0).getDate(),formatter.parse("05/01/2017"));
        assertEquals(actualEmployees.get(1).getDate(),formatter.parse("05/01/2017"));
        assertEquals(actualEmployees.get(2).getDate(),formatter.parse("05/01/2017"));

    }

    private static List<EmployeeExtended> unmarshalling() throws ParseException {
        List<EmployeeExtended> employees = new ArrayList<>(3);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

        EmployeeExtended employee1 = new EmployeeExtended();
        employee1.setEmployeeId(123923L);
        employee1.setName("Joe");
        employee1.setSurname("Doe");
        employee1.setSingle(true);
        employee1.setAge(30);
        employee1.setBirthday("31/12/1987");
        employee1.setRate(4.9);
        employee1.setDate(sdf.parse("05/01/2017 22:00:01.0"));
        employees.add(employee1);

        EmployeeExtended employee2 = new EmployeeExtended();
        employee2.setEmployeeId(123123L);
        employee2.setName("Sophie");
        employee2.setSurname("Derue");
        employee2.setSingle(false);
        employee2.setAge(20);
        employee2.setBirthday("3/5/1997");
        employee2.setRate(5.3);
        employee2.setDate((sdf).parse("05/01/2017 22:00:01.0"));
        employees.add(employee2);

        EmployeeExtended employee3 = new EmployeeExtended();
        employee3.setEmployeeId(135923L);
        employee3.setName("Paul");
        employee3.setSurname("Raul");
        employee3.setSingle(false);
        employee3.setAge(31);
        employee3.setBirthday("9/4/1986");
        employee3.setRate(6.6);
        employee3.setDate(sdf.parse("05/01/2017 22:00:01.0"));
        employees.add(employee3);

        return employees;
    }
}
