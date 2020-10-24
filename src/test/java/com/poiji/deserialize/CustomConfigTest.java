package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.config.Casting;
import com.poiji.deserialize.model.byid.ConfigPerson;
import com.poiji.deserialize.model.byid.ListAttributes;
import com.poiji.option.PoijiOptions;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

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
    public void shouldConvertCellsToListObjectsXLSX() {
        PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings()
                .addListDelimiter("=")
                .build();

        List<ListAttributes> actualEmployees = Poiji.fromExcel(new File("src/test/resources/attribute_list.xlsx"), ListAttributes.class, poijiOptions);
        assertThat(actualEmployees, notNullValue());

        ListAttributes employeeSecond = actualEmployees.get(1);
        assertThat(employeeSecond.getAge().get(1), is(10));
        assertThat(employeeSecond.getSurname().get(2), is("mlo"));
        assertThat(employeeSecond.getName().get(0), is("Sophie"));
        assertThat(employeeSecond.getBigdecimal().get(2), is(BigDecimal.valueOf(4)));
        assertThat(employeeSecond.getDoubleAge().get(0), is(20d));
        assertThat(employeeSecond.getFloatAge().get(0), is(Float.valueOf("323.12")));
        assertThat(employeeSecond.getLongAge().get(1), is(Long.valueOf("10")));
        assertThat(employeeSecond.getBooleanSingle().get(0), is(Boolean.valueOf("FALSE")));
    }

    @Test
    public void shouldConvertCellsToListObjectsXLS() {
        PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings()
                .addListDelimiter("=")
                .build();

        List<ListAttributes> actualEmployees = Poiji.fromExcel(new File("src/test/resources/attribute_list.xls"), ListAttributes.class, poijiOptions);
        assertThat(actualEmployees, notNullValue());

        ListAttributes employeeSecond = actualEmployees.get(1);
        assertThat(employeeSecond.getAge().get(1), is(10));
        assertThat(employeeSecond.getSurname().get(2), is("mlo"));
        assertThat(employeeSecond.getName().get(0), is("Sophie"));
        assertThat(employeeSecond.getBigdecimal().get(2), is(BigDecimal.valueOf(4)));
        assertThat(employeeSecond.getDoubleAge().get(0), is(20d));
        assertThat(employeeSecond.getFloatAge().get(0), is(Float.valueOf("323.12")));
        assertThat(employeeSecond.getLongAge().get(1), is(Long.valueOf("10")));
        assertThat(employeeSecond.getBooleanSingle().get(0), is(Boolean.valueOf("FALSE")));
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
