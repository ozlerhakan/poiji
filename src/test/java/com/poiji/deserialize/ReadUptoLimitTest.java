package com.poiji.deserialize;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Person;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;

public class ReadUptoLimitTest {

	@Test
	public void limitWithoutSkip() {

		PoijiOptions options = PoijiOptionsBuilder.settings().limit(3).build();

		List<Person> personListFromXSSF = Poiji.fromExcel(new File("src/test/resources/person.xlsx"), Person.class,
				options);
		assertEquals(3, personListFromXSSF.size());

		List<Person> personListFromHSSF = Poiji.fromExcel(new File("src/test/resources/person.xls"), Person.class,
				options);
		assertEquals(3, personListFromHSSF.size());

	}

	@Test
	public void limitWithSkip() {

		PoijiOptions options = PoijiOptionsBuilder.settings().skip(4).limit(3).build();

		List<Person> personListFromXSSF = Poiji.fromExcel(new File("src/test/resources/person.xlsx"), Person.class,
				options);
		assertEquals(1, personListFromXSSF.size());

		List<Person> personListFromHSSF = Poiji.fromExcel(new File("src/test/resources/person.xls"), Person.class,
				options);
		assertEquals(1, personListFromHSSF.size());
	}

	@Test(expected = PoijiException.class)
	public void negativeLimitOptionThrowsException() {

		PoijiOptions options = PoijiOptionsBuilder.settings().limit(-1).build();
		Poiji.fromExcel(new File("src/test/resources/person.xlsx"), Person.class, options,System.out::println);
	}

}
