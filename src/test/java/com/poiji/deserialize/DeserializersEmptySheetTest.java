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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by hakan on 17/01/2017.
 */
@RunWith(Parameterized.class)
public class DeserializersEmptySheetTest {

    private String path;
    private int indexSheet;

    public DeserializersEmptySheetTest(String path,
                                       int indexSheet) {
        this.path = path;
        this.indexSheet = indexSheet;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/employees_sheet2.xlsx", 2},
        });
    }

    @Test
    public void shouldReturnEmptyList() {

        PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(indexSheet).build();

        List<Employee> actualEmployees = Poiji.fromExcel(new File(path), Employee.class, poijiOptions);

        assertThat(actualEmployees, notNullValue());
        assertThat(actualEmployees.size(), is(0));
    }

}
