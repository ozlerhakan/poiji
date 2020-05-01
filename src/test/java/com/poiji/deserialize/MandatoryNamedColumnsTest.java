package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Person;
import com.poiji.deserialize.model.byname.PersonByNameWithMissingColumn;
import com.poiji.exception.HeaderMissingException;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.poiji.util.Data.unmarshallingPersons;

@RunWith(Parameterized.class)
public class MandatoryNamedColumnsTest {

    private String path;
    private List<Person> expectedPersonList;
    private Class<?> clazz;

    public MandatoryNamedColumnsTest(String path, List<Person> expectedPersonList, Class<?> clazz) {
        this.path = path;
        this.expectedPersonList = expectedPersonList;
        this.clazz = clazz;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/person.xlsx", unmarshallingPersons(), PersonByNameWithMissingColumn.class},
                {"src/test/resources/person.xls", unmarshallingPersons(), PersonByNameWithMissingColumn.class}
        });
    }

    @Test
    public void testExcelSuccess() {
        Poiji.fromExcel(new File(path), PersonByNameWithMissingColumn.class, PoijiOptions.PoijiOptionsBuilder
                .settings()
                .namedHeaderMandatory(false)
                .build());
    }

    @Test(expected = HeaderMissingException.class)
    public void testExcelFail() {

        Poiji.fromExcel(new File(path), PersonByNameWithMissingColumn.class, PoijiOptions.PoijiOptionsBuilder
                .settings()
                .namedHeaderMandatory(true)
                .build());
    }
}
