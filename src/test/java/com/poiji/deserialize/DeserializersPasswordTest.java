package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Employee;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.poiji.util.Data.unmarshallingDeserialize;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by hakan on 22/06/2018.
 */
@RunWith(Parameterized.class)
public class DeserializersPasswordTest {

    private String path;
    private List<Employee> expectedEmployess;
    private Class<?> expectedException;
    private String password;

    public DeserializersPasswordTest(String path,
                                     List<Employee> expectedEmployess,
                                     Class<?> expectedException,
                                     String password) {
        this.path = path;
        this.expectedEmployess = expectedEmployess;
        this.expectedException = expectedException;
        this.password = password;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/employees-password.xlsx", unmarshallingDeserialize(), null, "1234"},
                {"src/test/resources/employees-password.xls", unmarshallingDeserialize(), null, "9876"},
        });
    }

    @Test
    public void shouldMapExcelToJava() {

        try {

            PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings().password(password).build();
            List<Employee> actualEmployees = Poiji.fromExcel(new File(path), Employee.class, poijiOptions);

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

}
