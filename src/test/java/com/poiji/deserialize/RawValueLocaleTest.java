package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.RowModelDouble;
import com.poiji.option.PoijiOptions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Created by beckerdennis on 07.12.2021
 */
@RunWith(Parameterized.class)
public class RawValueLocaleTest {

    private final String path;

    public RawValueLocaleTest(String path) {
        this.path = path;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/raw_value_locale.xls"},
                {"src/test/resources/raw_value_locale.xlsx"},
        });
    }

    @BeforeClass
    public static void setLocaleGermany() {
        Locale.setDefault(Locale.GERMANY);
    }

    @Test
    public void shouldMapRawValueWithDifferentLocale() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().headerCount(0).setLocale(Locale.GERMANY).rawData(true).build();
        List<RowModelDouble> models = Poiji.fromExcel(new File(path), RowModelDouble.class, options);

        for (RowModelDouble model : models) {
            assertEquals(0.351D, model.getRowValue(), 0);
        }
    }
}
