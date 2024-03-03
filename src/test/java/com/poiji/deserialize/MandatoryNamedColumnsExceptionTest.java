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
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

    @Test
    public void testExcelMandatoryColumn() {
        try {
            Poiji.fromExcel(new File(path), PersonByNameWithMissingColumn.class, PoijiOptions.PoijiOptionsBuilder
                    .settings()
                    .build());
        } catch (HeaderMissingException e) {
            assertEquals(Set.of(6), e.getMissingExcelCellHeaders());
            assertEquals(Set.of("This column will be missing"), e.getMissingExcelCellNameHeaders());
            return;
        }
        fail("Expected exception: " + HeaderMissingException.class.getName());
    }
}
