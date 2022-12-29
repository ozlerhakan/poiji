package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byname.PersonByNameWithMissingColumn;
import com.poiji.exception.HeaderMissingException;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;

@RunWith(Parameterized.class)
public class MandatoryNamedColumnsExceptionTest {

    private String path;

    public MandatoryNamedColumnsExceptionTest(String path) {
        this.path = path;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][] {
                { "src/test/resources/person.xlsx" },
                { "src/test/resources/person.xls" }
        });
    }

    @Test(expected = HeaderMissingException.class)
    public void testExcelMandatoryColumn() {
        Poiji.fromExcel(new File(path), PersonByNameWithMissingColumn.class, PoijiOptions.PoijiOptionsBuilder
                .settings()
                .build());
    }
}
