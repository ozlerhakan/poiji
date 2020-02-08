package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.ConcurrentEntity;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class MultinameTest {

    private final String path;

    public MultinameTest(String path) {
        this.path = path;
    }

    @Parameterized.Parameters
    public static List<String> excel() {
        return Arrays.asList("src/test/resources/multiname.xlsx", "src/test/resources/multiname.xls");
    }

    @Test
    public void read() {
        final List<ConcurrentEntity> expected = singletonList(new ConcurrentEntity().setPrimitiveLong(1).setText("1"));

        final List<ConcurrentEntity> actual = Poiji.fromExcel(new File(path), ConcurrentEntity.class);

        assertThat(actual, equalTo(expected));

    }

}
