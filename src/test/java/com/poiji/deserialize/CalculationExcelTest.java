package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Calculation;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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

        final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("M/d/")
                .appendValueReduced(ChronoField.YEAR_OF_ERA, 2, 2, LocalDate.now().minusYears(80)).toFormatter();

        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(1).dateFormatter(formatter).build();

        List<Calculation> calculations = Poiji.fromExcel(new File(path), Calculation.class, options);

        for (Calculation calculation : calculations) {

            assertThat(calculation.getToDate().toString(), is("2018-06-30"));
            assertThat(calculation.getFromDate().toString(), is("2018-01-01"));
        }
    }
}
