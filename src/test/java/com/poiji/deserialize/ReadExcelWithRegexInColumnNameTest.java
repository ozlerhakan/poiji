package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.InventoryData;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.List;

import static com.poiji.option.PoijiOptions.PoijiOptionsBuilder.settings;
import static com.poiji.util.Data.unmarshallingInventoryData;
import static org.junit.Assert.assertEquals;

/**
 * Test for Reading Excel columns with Regular expression (Regex)
 */
@RunWith(Parameterized.class)
public class ReadExcelWithRegexInColumnNameTest {
    private final String path;
    private final List<InventoryData> expectedData;
    private final PoijiOptions options;

    public ReadExcelWithRegexInColumnNameTest(String path, List<InventoryData> expectedData, PoijiOptions options) {
        this.path = path;
        this.expectedData = expectedData;
        this.options = options;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return List.of(new Object[][]{
                {
                        "src/test/resources/regex/inventory.xlsx",
                        unmarshallingInventoryData(),
                        settings().sheetName("Books").build()
                },
                {
                        "src/test/resources/regex/inventory.xlsx",
                        unmarshallingInventoryData(),
                        settings().sheetName("Songs").build()
                },
                {
                        "src/test/resources/regex/inventory.xls",
                        unmarshallingInventoryData(),
                        settings().sheetName("Books").build()
                },
                {
                        "src/test/resources/regex/inventory.xls",
                        unmarshallingInventoryData(),
                        settings().sheetName("Songs").build()
                },
        });
    }

    @Test
    public void shouldReadInventoryData() {
        List<InventoryData> actualData = Poiji.fromExcel(new File(path), InventoryData.class, options);

        assertEquals(expectedData, actualData);
    }

}
