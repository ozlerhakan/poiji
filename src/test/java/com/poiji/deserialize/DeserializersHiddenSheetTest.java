package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Person;
import com.poiji.deserialize.model.byname.HiddenModel;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.poiji.util.Data.unmarshallingPersons;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DeserializersHiddenSheetTest {

    private final String path;
    private final List<Person> expectedPersonList;

    public DeserializersHiddenSheetTest(String path, List<Person> expectedPersonList) {
        this.path = path;
        this.expectedPersonList = expectedPersonList;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/hidden.xlsx", unmarshallingPersons()},
                {"src/test/resources/hidden.xls", unmarshallingPersons()},
                {"src/test/resources/hidden_very_large.xlsx", unmarshallingPersons()},
                {"src/test/resources/hidden_very_large.xls", unmarshallingPersons()}
        });
    }

    @Test
    public void testIgnoreHiddenSheets() {
        PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).build();

        List<Person> people = Poiji.fromExcel(new File(path), Person.class, poijiOptions);
        assertEquals(expectedPersonList.get(0).getRow(), people.get(0).getRow());
        assertEquals(expectedPersonList.get(1).getRow(), people.get(1).getRow());
        assertEquals(expectedPersonList.get(2).getRow(), people.get(2).getRow());
        assertEquals(expectedPersonList.get(3).getRow(), people.get(3).getRow());
        assertEquals(expectedPersonList.get(4).getRow(), people.get(4).getRow());
    }

    @Test
    public void testProcessHiddenSheets() {

        PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings().ignoreHiddenSheets(false).build();
        List<HiddenModel> people = Poiji.fromExcel(new File(path), HiddenModel.class, poijiOptions);
        assertEquals(people.size(), 0);

    }

}
