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

          PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().sheetName("SI calculations").build();

	        List<Calculation> calculations = Poiji.fromExcel(new File(path), Calculation.class, options);

	        for (Calculation calculation : calculations) {

	            assertThat(calculation.getToDate().toString(), is("2018-06-30"));
	            assertThat(calculation.getFromDate().toString(), is("2018-01-01"));
	        }
	    }

}
