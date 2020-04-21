package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Employee;
import com.poiji.exception.PoijiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class EmptyExcelSheetTest {

    private String path;

    public EmptyExcelSheetTest(String path) {
        super();
        this.path = path;
    }


    @Parameters
    public static Object[] excel() {
        return new Object[]{"src/test/resources/empty-excel-sheet.xlsx"};
    }

    @Test(expected = PoijiException.class)
    public void shouldRetrieveDataRow() {

        List<Employee> actualEmployees = Poiji.fromExcel(new File(path), Employee.class);
        assertThat(actualEmployees, notNullValue());
    }
}
