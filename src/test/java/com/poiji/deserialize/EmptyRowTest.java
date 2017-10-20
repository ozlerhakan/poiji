package com.poiji.deserialize;

import com.poiji.deserialize.model.Employee;
import com.poiji.internal.Poiji;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class EmptyRowTest {

    private String path;

    public EmptyRowTest(String path) {
        super();
        this.path = path;
    }

    @Parameters
    public static Object[] excel() throws Exception {
        return new Object[]{"src/test/resources/synonyms-test.xlsx"};
    }

    @Test
    public void shouldRetrieveDataRow() {

        List<Employee> actualEmployees = Poiji.fromExcel(new File(path), Employee.class);
        assertThat(actualEmployees, notNullValue());
        assertThat(actualEmployees.size(), is(101));


    }
}
