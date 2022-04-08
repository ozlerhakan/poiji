package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.Person;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class WriteToExcelTest {

    @Test
    public void toExcel() throws IOException {
        Workbook book = new XSSFWorkbook();

        Sheet sheet = book.createSheet("test_sheet");

        Person mike = new Person("Mike", "Black", 18);
        Person jane = new Person("Jane", "White", 21);
        Collection<Person> people = Arrays.asList(mike, jane);

        Poiji.toExcel(sheet, people, Person.class);

        File file = File.createTempFile("person_temp_file", ".xlsx");
        book.write(new FileOutputStream(file));
        book.close();

        Collection<Person> actual = Poiji.fromExcel(file, Person.class);
        file.delete();
        Assert.assertEquals(people, actual);

    }
}
