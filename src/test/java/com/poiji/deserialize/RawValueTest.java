package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.RowModel;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by hakan on 02/08/2018
 */
@RunWith(Parameterized.class)
public class RawValueTest {

    private String path;

    public RawValueTest(String path) {
        this.path = path;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/raw_value.xls"},
                {"src/test/resources/raw_value.xlsx"},
        });
    }

    @Test
    public void shouldMapCalculations() {

        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().headerCount(0).rawData(true).build();
        List<RowModel> models = Poiji.fromExcel(new File(path), RowModel.class, options);

        for (RowModel model : models) {
            assertThat(model.getRowValue(), is(57363987456321L));
        }
    }
}
