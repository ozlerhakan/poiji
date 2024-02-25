package com.poiji.bind.mapping;

import com.poiji.annotation.ExcelCellName;
import com.poiji.deserialize.model.InventoryData;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PoijiHandlerTest {

    private final ExcelCellName annotation;

    public PoijiHandlerTest(String fieldName) throws NoSuchFieldException {
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
        PoijiHandler<InventoryData> poijiHandler = new PoijiHandler<>(InventoryData.class,
                PoijiOptions.PoijiOptionsBuilder.settings().build(), o -> {
                });

        Integer titleColumn = poijiHandler.findTitleColumn(annotation);

        assertNull(titleColumn);
    }
}