package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.Album;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.List;

import static com.poiji.option.PoijiOptions.PoijiOptionsBuilder.settings;
import static com.poiji.util.Data.unmarshallingAlbums;
import static org.junit.Assert.assertEquals;

/**
 * Test for Reading Excel columns with Regular expression (Regex)
 */
@RunWith(Parameterized.class)
public class ReadExcelWithRegexAndJoinValuesTest {
    private final String path;
    private final List<Album> expectedData;
    private final PoijiOptions options;

    public ReadExcelWithRegexAndJoinValuesTest(String path, List<Album> expectedData, PoijiOptions options) {
        this.path = path;
        this.expectedData = expectedData;
        this.options = options;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return List.of(new Object[][]{
                {
                        "src/test/resources/regex/album.xlsx",
                        unmarshallingAlbums(),
                        settings().sheetName("Sheet 1").build()
                },
                {
                        "src/test/resources/regex/album.xls",
                        unmarshallingAlbums(),
                        settings().sheetName("Sheet 1").build()
                },
        });
    }

    @Test
    public void shouldReadAlbumData() {
        List<Album> actualData = Poiji.fromExcel(new File(path), Album.class, options);

        assertEquals(expectedData, actualData);
    }

}
