package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.ConcurrentEntity;
import com.poiji.option.PoijiOptions;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Ignore;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * For manual testing only.
 */
public class ConcurrentTest {

    /**
     * 1000000 records, Intel® Core™ i3-4160, 24GB, SSD
     * <p>
     * Synchronized read:
     * Generated in 370 ms
     * Written in 8761 ms
     * Read in 39167 ms
     * <p>
     * Synchronized optimized read:
     * Generated in 304 ms
     * Written in 8887 ms
     * Read in 28832 ms
     * <p>
     * Optimized read:
     * Generated in 315 ms
     * Written in 8708 ms
     * Read in 18536 ms
     * <p>
     * Synchronized write:
     * Generated in 309 ms
     * Written in 16225 ms
     * Read in 24233 ms
     */
    @Test
    @Ignore("Test disabled to prevent huge xlsx files writing in CI")
    public void writeThenRead() {
        final long start = System.nanoTime();
        final int size = 1000000;
        final List<ConcurrentEntity> entities1 = generateEntities(size, "1");
        final List<ConcurrentEntity> entities2 = generateEntities(size, "2");
        final List<ConcurrentEntity> entities3 = generateEntities(size, "3");
        final List<ConcurrentEntity> entities4 = generateEntities(size, "4");
        final Set<List<ConcurrentEntity>> expected = new HashSet<>(asList(entities1, entities2, entities3, entities4));
        final String name1 = "src/test/resources/concurrent1.xlsx";
        final String name2 = "src/test/resources/concurrent2.xlsx";
        final String name3 = "src/test/resources/concurrent3.xlsx";
        final String name4 = "src/test/resources/concurrent4.xlsx";
        final List<WriteData> writeData = asList(
            new WriteData(name1, entities1),
            new WriteData(name2, entities2),
            new WriteData(name3, entities3),
            new WriteData(name4, entities4)
        );

        final PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().preferNullOverDefault(true).build();

        final long generated = System.nanoTime();
        System.out.println("Generated in " + (generated - start) / 1000000 + " ms");

        writeData
            .parallelStream()
            .forEach(data -> Poiji.toExcel(new File(data.path), ConcurrentEntity.class, data.entities, options));

        final long written = System.nanoTime();
        System.out.println("Written in " + (written - generated) / 1000000 + " ms");

        final Set<List<ConcurrentEntity>> actual = asList(name1, name2, name3, name4)
            .parallelStream()
            .map(s -> Poiji.fromExcel(new File(s), ConcurrentEntity.class, options))
            .collect(Collectors.toSet());

        final long read = System.nanoTime();
        System.out.println("Read in " + (read - written) / 1000000 + " ms");

        assertThat(actual, equalTo(expected));


    }

    private List<ConcurrentEntity> generateEntities(final int size, final String marker) {
        final List<ConcurrentEntity> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(new ConcurrentEntity().setPrimitiveLong(i).setText(marker));
        }
        return result;
    }

    public static class WriteData {
        private final String path;
        private final List<ConcurrentEntity> entities;

        public WriteData(final String path, final List<ConcurrentEntity> entities) {
            this.path = path;
            this.entities = entities;
        }
    }

}
