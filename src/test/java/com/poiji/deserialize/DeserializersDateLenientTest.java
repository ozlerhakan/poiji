package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.EmployeeExtended;
import com.poiji.option.PoijiOptions;
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
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class DeserializersDateLenientTest {

    private final String path;
    private final List<EmployeeExtended> expectedEmployess;
    private final Class<?> expectedException;

    public DeserializersDateLenientTest(String path, List<EmployeeExtended> expectedEmployess, Class<?> expectedException) {
        this.path = path;
        this.expectedEmployess = expectedEmployess;
        this.expectedException = expectedException;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() throws Exception {
        return Arrays.asList(new Object[][]{
            {"src/test/resources/date_lenient.xlsx", unmarshalling(), null},
            {"src/test/resources/date_lenient.xls", unmarshalling(), null}});
    }

    @Test
    public void lenientTrue() {

        try {
            PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().datePattern("yyyy-MM-dd").dateLenient(true).build();

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

            assertThat(actualEmployee1, is(expectedEmployee1));
            assertThat(actualEmployee2, is(expectedEmployee2));
            assertThat(actualEmployee3, is(expectedEmployee3));

        } catch (Exception e) {
            if (expectedException == null) {
                fail(e.getMessage());
            } else {
                assertThat(e, instanceOf(expectedException));
            }
        }
    }

    @Test
    public void lenientFalse() {

        try {
            PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().datePattern("yyyy-MM-dd").dateLenient(false).build();

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

            assertThat(actualEmployee1, not(expectedEmployee1));
            assertThat(actualEmployee2, not(expectedEmployee2));
            assertThat(actualEmployee3, not(expectedEmployee3));

        } catch (Exception e) {
            if (expectedException == null) {
                fail(e.getMessage());
            } else {
                assertThat(e, instanceOf(expectedException));
            }
        }
    }

    private static List<EmployeeExtended> unmarshalling() throws ParseException {
        List<EmployeeExtended> employees = new ArrayList<>(3);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        EmployeeExtended employee1 = new EmployeeExtended();
        employee1.setEmployeeId(123923L);
        employee1.setName("Joe");
        employee1.setSurname("Doe");
        employee1.setSingle(true);
        employee1.setAge(30);
        employee1.setBirthday("1987-12-31");
        employee1.setRate(4.9);
        employee1.setDate(sdf.parse("0035-08-10"));
        employees.add(employee1);

        EmployeeExtended employee2 = new EmployeeExtended();
        employee2.setEmployeeId(123123L);
        employee2.setName("Sophie");
        employee2.setSurname("Derue");
        employee2.setSingle(false);
        employee2.setAge(20);
        employee2.setBirthday("1997-05-03");
        employee2.setRate(5.3);
        employee2.setDate((sdf).parse("0035-08-10"));
        employees.add(employee2);

        EmployeeExtended employee3 = new EmployeeExtended();
        employee3.setEmployeeId(135923L);
        employee3.setName("Paul");
        employee3.setSurname("Raul");
        employee3.setSingle(false);
        employee3.setAge(31);
        employee3.setBirthday("1986-04-09");
        employee3.setRate(6.6);
        employee3.setDate(sdf.parse("0035-08-10"));
        employees.add(employee3);

        return employees;
    }
}
