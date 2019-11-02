package com.poiji.deserialize;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Person;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;

public class SkipReadsTest {

	@Test
	public void testWithDefaultsShouldReadAll() {

		List<Person> actualEmployees = Poiji.fromExcel(new File("src/test/resources/person.xlsx"), Person.class);

		assertEquals(5, actualEmployees.size());

		actualEmployees = Poiji.fromExcel(new File("src/test/resources/person.xls"), Person.class);

		assertEquals(5, actualEmployees.size());
	}

	@Test
	public void testWithoutAnyLimitOrSkip() {

		PoijiOptions options = PoijiOptionsBuilder.settings().jumpReads(2, 1).build();
		List<Person> actualEmployees = Poiji.fromExcel(new File("src/test/resources/person.xlsx"), Person.class,
				options);

		assertEquals(4, actualEmployees.size());

		actualEmployees = Poiji.fromExcel(new File("src/test/resources/person.xls"), Person.class, options);

		assertEquals(4, actualEmployees.size());
	}

	@Test
	public void testWithoutLimitWithSkipEveryZeroShouldReadAllRows() {

		PoijiOptions options = PoijiOptionsBuilder.settings().jumpReads(3, 0).build();
		List<Person> actualEmployees = Poiji.fromExcel(new File("src/test/resources/person.xlsx"), Person.class,
				options);

		assertEquals(5, actualEmployees.size());

		actualEmployees = Poiji.fromExcel(new File("src/test/resources/person.xls"), Person.class, options);

		assertEquals(5, actualEmployees.size());
	}

	@Test
	public void testWithLimitAndSkip() {

		PoijiOptions options = PoijiOptionsBuilder.settings().skip(1).limit(3).jumpReads(1, 1).build();
		List<Person> actualEmployees = Poiji.fromExcel(new File("src/test/resources/person.xlsx"), Person.class,
				options);

		assertEquals(2, actualEmployees.size());

		actualEmployees = Poiji.fromExcel(new File("src/test/resources/person.xls"), Person.class, options);

		assertEquals(2, actualEmployees.size());
	}

	@Test(expected = PoijiException.class)
	public void testWithNotSuitingValueShouldThrowException() {
		PoijiOptionsBuilder.settings().jumpReads(0, -1).build();	
	}

}
