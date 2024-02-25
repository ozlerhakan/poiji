package com.poiji.bind.mapping;

import com.poiji.annotation.ExcelCellName;
import com.poiji.deserialize.model.InventoryData;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class HSSFUnmarshallerTest {

    private final ExcelCellName annotation;

    public HSSFUnmarshallerTest(String fieldName) throws NoSuchFieldException {
        Field author = InventoryData.class.getDeclaredField(fieldName);
        annotation = author.getAnnotation(ExcelCellName.class);
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return List.of(new Object[][] {
                {
                        "id"
                },
                {
                        "author"
                },
                { "empty" }
        });
    }

    @Test
    public void shouldFindTitleColumn() {
        HSSFUnmarshallerFile hssfUnmarshallerFile = new HSSFUnmarshallerFile(null,
                PoijiOptions.PoijiOptionsBuilder.settings().build());

        Integer titleColumn = hssfUnmarshallerFile.findTitleColumn(annotation);

        assertNull(titleColumn);
    }
}