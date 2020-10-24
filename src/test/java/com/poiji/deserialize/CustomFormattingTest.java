package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.config.Formatting;
import com.poiji.deserialize.model.byid.FormattingPerson;
import com.poiji.option.PoijiOptions;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by hakan on 2020-10-24
 */
public class CustomFormattingTest {

    @Test
    public void shouldUseCustomXLSXConfig() {

        PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings()
                .withFormatting(new MyFormattingXLSX())
                .build();

        List<FormattingPerson> actualEmployees = Poiji.fromExcel(
                new File("src/test/resources/formatting_employees.xlsx"), FormattingPerson.class, poijiOptions);

        assertThat(actualEmployees, notNullValue());
        assertThat(actualEmployees.size(), not(0));

        FormattingPerson actualEmployee1 = actualEmployees.get(0);

        assertThat(actualEmployee1.getEmployeeId(), is("123923"));
        assertThat(actualEmployee1.getAge(), is("30"));
        assertThat(actualEmployee1.getBirthday(), is("4/9/1987"));
        assertThat(actualEmployee1.getName(), is("Joe"));
        assertThat(actualEmployee1.getSurname(), is("Doe"));

    }

    @Test
    public void shouldUseCustomXLSConfig() {

        PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings()
                .withFormatting(new MyFormattingXLS())
                .build();

        List<FormattingPerson> actualEmployees = Poiji.fromExcel
                (new File("src/test/resources/formatting_employees.xls"), FormattingPerson.class, poijiOptions);

        assertThat(actualEmployees, notNullValue());
        assertThat(actualEmployees.size(), not(0));

        FormattingPerson actualEmployee1 = actualEmployees.get(0);

        assertThat(actualEmployee1.getEmployeeId(), is("123923"));
        assertThat(actualEmployee1.getAge(), is("30"));
        assertThat(actualEmployee1.getBirthday(), is("4/9/1987"));
        assertThat(actualEmployee1.getName(), is("Joe"));
        assertThat(actualEmployee1.getSurname(), is("Doe"));

    }


    static class MyFormattingXLSX implements Formatting {
        @Override
        public String transform(PoijiOptions options, String value) {
            return value.toLowerCase().trim();
        }
    }

    static class MyFormattingXLS implements Formatting {
        @Override
        public String transform(PoijiOptions options, String value) {
            return value.toLowerCase().trim();
        }
    }
}
