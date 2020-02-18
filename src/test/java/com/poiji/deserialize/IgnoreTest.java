package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.IgnoreEntity;
import com.poiji.option.PoijiOptions;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class IgnoreTest {

    private String path;

    public IgnoreTest(String path) {
        this.path = path;
    }

    @Parameterized.Parameters
    public static List<String> excel() {
        return asList("src/test/resources/ignore.xlsx", "src/test/resources/ignore.xls");
    }

    @Test
    public void caseInsensitiveColumnNames() {
        final List<IgnoreEntity> expected = new ArrayList<>();
        final IgnoreEntity entity = new IgnoreEntity()
            .setWriteText("test")
            .setPrimitiveLong(2L);
        expected.add(entity);
        expected.add(new IgnoreEntity());
        final PoijiOptions options = PoijiOptions.PoijiOptionsBuilder
            .settings()
            .datePattern("dd-MM-yyyy HH:mm:ss")
            .preferNullOverDefault(true)
            .build();
        Poiji.toExcel(new File(path), IgnoreEntity.class, expected, options);

        final List<IgnoreEntity> read = Poiji.fromExcel(new File(path), IgnoreEntity.class, options);
        expected.forEach(ignoreEntity -> {
            ignoreEntity.setReadText(ignoreEntity.getWriteText()).setWriteText(null);
        });
        Assert.assertThat(read.toString(), equalTo(expected.toString()));
    }

}
