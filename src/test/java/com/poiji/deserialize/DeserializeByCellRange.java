package com.poiji.deserialize;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Classes;
import com.poiji.deserialize.model.byid.PersonTest;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;

public class DeserializeByCellRange {
	
	 @Test
	    public void shouldMapExcelToJavaMulti() {
	        try {
	        	PoijiOptions options = PoijiOptionsBuilder.settings(2).rowStart(1).build();
	            List<Classes> actualClasses = Poiji.fromExcel(new File("src/test/resources/test_multi.xls"), 
	            		Classes.class, options);

	            assertThat(actualClasses, notNullValue());
	            assertThat(actualClasses.size(), not(0));
	            assertThat(actualClasses.size(), is(4));

	            Classes actualClasses1 = actualClasses.get(0);
	            Classes actualClasses2 = actualClasses.get(1);

	            PersonTest expectedPerson1 = actualClasses1.getClassA();
	            PersonTest expectedPerson2 = actualClasses1.getClassB();
	            PersonTest expectedPerson3 = actualClasses2.getClassA();
	            PersonTest expectedPerson4 = actualClasses2.getClassB();
	            
	            assertThat(expectedPerson1.getAge(), is(21));
	            assertThat(expectedPerson2.getCity(), is("McLean"));
	            assertThat(expectedPerson3.getName(), is("Jane Doe"));
	            assertThat(expectedPerson4.getState(), is("California"));
	        } catch (Exception e) {
	        	System.err.println(e.getMessage());

	        }
	    }

}
