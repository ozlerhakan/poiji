package com.poiji.deserialize;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.ConfigPerson;
import com.poiji.deserialize.model.byid.Person;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;

public class ReadUptoLimitTest {

	@Test
	public void testWithoutAnyLimit() {

		List<ConfigPerson> actualEmployees = Poiji.fromExcel(new File("src/test/resources/employees.xlsx"),
				ConfigPerson.class);
		
		assertEquals(3, actualEmployees.size());

	}

	@Test
	public void limitWithoutSkip() {

		PoijiOptions options = PoijiOptionsBuilder.settings().limit(4).build();

		List<Person> personListFromXSSF = Poiji.fromExcel(new File("src/test/resources/person.xlsx"), Person.class,
				options);
		assertEquals(4, personListFromXSSF.size());

		List<Person> personListFromHSSF = Poiji.fromExcel(new File("src/test/resources/person.xls"), Person.class,
				options);
		assertEquals(4, personListFromHSSF.size());

	}

	@Test
	public void limitWithSkip() {

		PoijiOptions options = PoijiOptionsBuilder.settings().skip(2).limit(4).build();

		List<Person> personListFromXSSF = Poiji.fromExcel(new File("src/test/resources/person.xlsx"), Person.class,
				options);
		assertEquals(3, personListFromXSSF.size());

		List<Person> personListFromHSSF = Poiji.fromExcel(new File("src/test/resources/person.xls"), Person.class,
				options);
		assertEquals(3, personListFromHSSF.size());
	}

	@Test(expected = PoijiException.class)
	public void negativeLimitOptionThrowsException() {

		PoijiOptions options = PoijiOptionsBuilder.settings().limit(-1).build();
		Poiji.fromExcel(new File("src/test/resources/person.xlsx"), Person.class, options, System.out::println);
	}

}
