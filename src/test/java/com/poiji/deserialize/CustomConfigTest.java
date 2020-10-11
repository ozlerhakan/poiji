package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.config.Casting;
import com.poiji.deserialize.model.byid.ConfigPerson;
import com.poiji.deserialize.model.byid.ListAttributes;
import com.poiji.option.PoijiOptions;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;
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

    @Test
    public void shouldAddList2() {
        PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings()
                .addListDelimiter("=")
                .build();

        List<ListAttributes> actualEmployees = Poiji.fromExcel(new File("src/test/resources/attribute_list.xlsx"), ListAttributes.class, poijiOptions);
        assertThat(actualEmployees, notNullValue());

        ListAttributes employeeSecond = actualEmployees.get(1);
        for (Integer age: employeeSecond.getAge()) {
            System.out.println(age);
        }
    }

    @Test
    public void shouldAddListXLS() {
        PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings()
                .addListDelimiter("=")
                .build();

        List<ListAttributes> actualEmployees = Poiji.fromExcel(new File("src/test/resources/attribute_list.xls"), ListAttributes.class, poijiOptions);
        assertThat(actualEmployees, notNullValue());
    }

    static class MyConfigXLSX implements Casting {
        @Override
        public Object castValue(Field fieldType, String value, int row, int column, PoijiOptions options) {
            return value.trim();
        }
    }

    static class MyConfigXLS implements Casting {
        @Override
        public Object castValue(Field fieldType, String value, int row, int column, PoijiOptions options) {
            return "-" + value.trim() + "-";
        }
    }
}
