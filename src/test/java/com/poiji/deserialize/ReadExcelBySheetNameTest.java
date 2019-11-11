package com.poiji.deserialize;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Calculation;
import com.poiji.option.PoijiOptions;

@RunWith(Parameterized.class)
public class ReadExcelBySheetNameTest {
	
	    private String path;

	    public ReadExcelBySheetNameTest(String path) {
	        this.path = path;
	    }

	    @Parameterized.Parameters(name = "{index}: ({0})={1}")
	    public static Iterable<Object[]> queries() {
	        return Arrays.asList(new Object[][]{
	                {"src/test/resources/calculations.xlsx"},
	        });
	    }

	    @Test
	    public void shouldReadExcelBySheetName() {

	        final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
	                .appendPattern("M/d/")
	                .appendValueReduced(ChronoField.YEAR_OF_ERA, 2, 2, LocalDate.now().minusYears(80)).toFormatter();

	        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().sheetName("SI calculations").dateFormatter(formatter).build();

	        List<Calculation> calculations = Poiji.fromExcel(new File(path), Calculation.class, options);

	        for (Calculation calculation : calculations) {

	            assertThat(calculation.getToDate().toString(), is("2018-06-30"));
	            assertThat(calculation.getFromDate().toString(), is("2018-01-01"));
	        }
	    }

}
