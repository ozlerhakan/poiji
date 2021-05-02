package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Employee;
import com.poiji.exception.PoijiException;
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
 * Created by hakan on 17/01/2017.
 */
@RunWith(Parameterized.class)
public class DeserializersTest {

    private String path;
    private List<Employee> expectedEmployess;
    private Class<?> expectedException;
    private int indexSheet;

    public DeserializersTest(String path,
                             List<Employee> expectedEmployess,
                             Class<?> expectedException,
                             int indexSheet) {
        this.path = path;
        this.expectedEmployess = expectedEmployess;
        this.expectedException = expectedException;
        this.indexSheet = indexSheet;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/employees.xlsx", unmarshallingDeserialize(), null, -1},
                {"src/test/resources/employees_sheet2.xlsx", unmarshallingDeserialize(), null, 1},
                {"src/test/resources/cloud-not-found.xls", unmarshallingDeserialize(), PoijiException.class, -1},
                {"src/test/resources/cloud.xls", unmarshallingDeserialize(), PoijiException.class, -1},
                {"src/test/resources/cloud", unmarshallingDeserialize(), PoijiException.class, -1},
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

}
