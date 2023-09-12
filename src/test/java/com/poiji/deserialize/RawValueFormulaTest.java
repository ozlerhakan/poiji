package com.poiji.deserialize;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.RowModelFormula;
import com.poiji.option.PoijiOptions;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Created by jmorgan on 12.09.2023
 */
@RunWith(Parameterized.class)
public class RawValueFormulaTest {

    private String path;

    public RawValueFormulaTest(String path) {
        this.path = path;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/raw_value_formula.xls"},
                {"src/test/resources/raw_value_formula.xlsx"},
        });
    }

    @Test
    public void shouldMapCalculations() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().headerCount(0).rawData(true).build();
        List<RowModelFormula> models = Poiji.fromExcel(new File(path), RowModelFormula.class, options);

        for (RowModelFormula model : models) {
            assertThat(model.getCurrencyValue(), is(123.45D));
            assertThat(model.getFormulaValue(), is(246.90D));
        }
    }
}
