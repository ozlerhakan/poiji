package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.WriteEntity;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.Ignore;
import org.junit.Test;

import static java.time.ZoneOffset.UTC;

/**
 * For manual testing.
 */
@Ignore
public class WriteTest {

    @Test
    public void caseInsensitiveColumnNames() {
        final Map<String, String> unknown = new HashMap<>();
        unknown.put("unKnown1", "unknown value 1");
        unknown.put("unKnown2", "unknown value 2");
        final List<WriteEntity> xlsx = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            final WriteEntity entity = new WriteEntity()
                .setPrimitiveDouble(10.0 + i)
                .setWrappedDouble(11.0)
                .setPrimitiveFloat(20.0f)
                .setWrappedFloat(21.0f)
                .setPrimitiveLong(1)
                .setText("test")
                .setPrimitiveBoolean(true)
                .setWrappedBoolean(null)
                .setPrimitiveChar('f')
                .setWrappedCharacter('g')
                .setDate(new Date(1234567890L))
                .setLocalDate(LocalDate.of(2020, 1, 2))
                .setLocalDateTime(LocalDateTime.of(2020, 1, 2, 12, 0))
                .setZonedDateTime(ZonedDateTime.of(2020, 1, 2, 12, 0, 0, 0, UTC))
                .setAnotherUnknown(unknown);
            xlsx.add(entity);
        }
        final List<WriteEntity> xls = new ArrayList<>();
        for (int i = 0; i < 65535; i++) {
            final WriteEntity entity = new WriteEntity()
                .setPrimitiveDouble(10.0 + i)
                .setWrappedDouble(11.0)
                .setPrimitiveFloat(20.0f)
                .setWrappedFloat(21.0f)
                .setPrimitiveLong(2)
                .setText("test")
                .setPrimitiveBoolean(true)
                .setWrappedBoolean(false)
                .setPrimitiveChar('f')
                .setWrappedCharacter('g')
                .setDate(new Date(1234567890L))
                .setLocalDate(LocalDate.of(2020, 1, 2))
                .setLocalDateTime(LocalDateTime.of(2020, 1, 2, 12, 0))
                .setZonedDateTime(ZonedDateTime.of(2020, 1, 2, 12, 0, 0, 0, UTC))
                .setAnotherUnknown(unknown);
            xls.add(entity);
        }

        Stream
            .of(new TestData(xlsx, "src/test/resources/write.xlsx"), new TestData(xls, "src/test/resources/write.xls"))
            .parallel()
            .forEach(testData -> Poiji.toExcel(new File(testData.fileName), WriteEntity.class, testData.entities));

    }

    private static final class TestData {
        private final List<WriteEntity> entities;
        private final String fileName;

        private TestData(final List<WriteEntity> entities, final String fileName) {
            this.entities = entities;
            this.fileName = fileName;
        }

    }

}
