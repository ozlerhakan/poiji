package com.poiji.deserialize;


import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Employee;
import com.poiji.deserialize.model.byid.Employee2;
import com.poiji.deserialize.model.byname.EmployeeByName;
import com.poiji.deserialize.model.byname.EmployeeByName2;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.poiji.util.Data.unmarshallingDeserialize;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by passedbylove@gmail.com on 2018-11-15.
 */
@RunWith(Parameterized.class)
public class DeserializersByNameTagWithWhiteSpaceTest {

    private String path;

    public DeserializersByNameTagWithWhiteSpaceTest(String path) {
        this.path = path;

    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{               
                {"src/test/resources/employees-tagwithwhitespace.xlsx"},
        });
    }

    @Test
    public void shouldMapExcelToJava() {
        PoijiOptions options = PoijiOptionsBuilder.settings().datePattern("dd/MM/yyyy","yyyy-MM-dd","yyyy/MM/dd","yyyy年MM月dd日","yyyy\\MM\\dd").trimCellValue(true).trimTagName(true).build();
        try {
            List<EmployeeByName2> actualEmployees = Poiji.fromExcel(new FileInputStream(new File(path)), EmployeeByName2.class, options);
            assertThat(actualEmployees.size(),is(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
