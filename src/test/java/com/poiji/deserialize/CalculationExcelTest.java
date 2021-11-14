package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Calculation;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by hakan on 02/08/2018
 */
@RunWith(Parameterized.class)
public class CalculationExcelTest {

    private String path;

    public CalculationExcelTest(String path) {
        this.path = path;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/calculations.xlsx"},
        });
    }

    @Test
    public void shouldMapCalculations() {

        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(1).build();

        List<Calculation> calculations = Poiji.fromExcel(new File(path), Calculation.class, options);

        for (Calculation calculation : calculations) {

            assertThat(calculation.getToDate().toString(), is("2018-06-30"));
            assertThat(calculation.getFromDate().toString(), is("2018-01-01"));
        }
    }
}
