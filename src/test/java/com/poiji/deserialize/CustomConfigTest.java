package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.config.Casting;
import com.poiji.deserialize.model.byid.ConfigPerson;
import com.poiji.option.PoijiOptions;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by hakan on 2018-12-01
 */
public class CustomConfigTest {

    @Test
    public void shouldUseCustomXLSXConfig() {

        PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings()
                .withCasting(new MyConfigXLSX())
                .build();

        List<ConfigPerson> actualEmployees = Poiji.fromExcel(new File("src/test/resources/employees.xlsx"), ConfigPerson.class, poijiOptions);

        assertThat(actualEmployees, notNullValue());
        assertThat(actualEmployees.size(), not(0));

        ConfigPerson actualEmployee1 = actualEmployees.get(0);

        assertThat(actualEmployee1.getEmployeeId(), is("123923"));
        assertThat(actualEmployee1.getAge(), is("30"));
        assertThat(actualEmployee1.getBirthday(), is("4/9/1987"));
        assertThat(actualEmployee1.getName(), is("Joe"));
        assertThat(actualEmployee1.getSurname(), is("Doe"));

    }

    @Test
    public void shouldUseCustomXLSConfig() {

        PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings()
                .withCasting(new MyConfigXLS())
                .build();

        List<ConfigPerson> actualEmployees = Poiji.fromExcel(new File("src/test/resources/employees.xls"), ConfigPerson.class, poijiOptions);

        assertThat(actualEmployees, notNullValue());
        assertThat(actualEmployees.size(), not(0));

        ConfigPerson actualEmployee1 = actualEmployees.get(0);

        assertThat(actualEmployee1.getEmployeeId(), is("-123923-"));
        assertThat(actualEmployee1.getAge(), is("-30-"));
        assertThat(actualEmployee1.getBirthday(), is("-4/9/1987-"));
        assertThat(actualEmployee1.getName(), is("-Joe-"));
        assertThat(actualEmployee1.getSurname(), is("-Doe-"));

    }

    static class MyConfigXLSX implements Casting {
        @Override
        public Object castValue(Class<?> fieldType, String value, int row, int column, PoijiOptions options) {
            return value.trim();
        }
    }

    static class MyConfigXLS implements Casting {
        @Override
        public Object castValue(Class<?> fieldType, String value, int row, int column, PoijiOptions options) {
            return "-" + value.trim() + "-";
        }
    }
}
