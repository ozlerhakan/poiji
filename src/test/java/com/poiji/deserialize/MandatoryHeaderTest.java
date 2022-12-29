package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byname.PersonByNameOptionalColumn;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class MandatoryHeaderTest {

    private String path;

    public MandatoryHeaderTest(String path) {
        this.path = path;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][] {
                { "src/test/resources/person.xlsx" },
                { "src/test/resources/person.xls" }
        });
    }

    @Test
    public void testExcelSuccessOptionalColumn() {
        List<PersonByNameOptionalColumn> response = Poiji.fromExcel(new File(path), PersonByNameOptionalColumn.class,
                PoijiOptions.PoijiOptionsBuilder
                        .settings()
                        .build());
        assertThat(response, notNullValue());
    }

}
