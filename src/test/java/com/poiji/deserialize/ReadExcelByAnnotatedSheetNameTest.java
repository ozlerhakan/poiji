package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.Student;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.poiji.util.Data.unmarshallingStudents;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test for Reading excel files using annotated sheet name
 */
@RunWith(Parameterized.class)
public class ReadExcelByAnnotatedSheetNameTest {
    private String path;
    private List<Student> expectedStudents;
    private PoijiOptions options;
    private Class<?> expectedException;

    public ReadExcelByAnnotatedSheetNameTest(String path, List<Student> expectedStudents, PoijiOptions options,
                                             Class<?> expectedException) {
        this.path = path;
        this.expectedStudents = expectedStudents;
        this.options = options;
        this.expectedException = expectedException;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/student.xlsx", unmarshallingStudents(), null, null},
                {"src/test/resources/student.xls", unmarshallingStudents(), null, null},
                {"src/test/resources/student.xlsx", unmarshallingStudents(), PoijiOptions.PoijiOptionsBuilder.settings()
                        .sheetName("Sheet1")
                        .build(),
                        PoijiException.class},
        });
    }

    @Test
    public void shouldReadAnnotatedSheetNameFromStudent() {

        try {
            List<Student> actualStudents;
            if (options == null) {
                actualStudents = Poiji.fromExcel(new File(path), Student.class);
            } else {
                actualStudents = Poiji.fromExcel(new File(path), Student.class, options);
            }

            assertThat(actualStudents, notNullValue());
            assertThat(actualStudents.size(), not(0));
            assertThat(actualStudents.size(), is(expectedStudents.size()));

            Student actualStudent1 = actualStudents.get(0);
            Student actualStudent2 = actualStudents.get(1);

            Student expectedStudent1 = expectedStudents.get(0);
            Student expectedStudent2 = expectedStudents.get(1);

            assertEquals(actualStudent1, expectedStudent1);
            assertEquals(actualStudent2, expectedStudent2);
        } catch (Exception e) {
            if (expectedException == null) {
                fail(e.getMessage());
            } else {
                assertThat(e, instanceOf(expectedException));
                assertEquals("The configured sheet name in PoijiOptions (Sheet1) and the annotated sheet name "
                        + "(Sheet2) do not match", e.getMessage());
            }
        }
    }

}
