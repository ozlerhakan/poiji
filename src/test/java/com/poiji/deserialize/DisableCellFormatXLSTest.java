package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.TestInfo;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class DisableCellFormatXLSTest {

    @Test
    public void shouldDisableCellFormatFromAnnotation() {
        List<TestInfo> result = Poiji.fromExcel(new File("src/test/resources/test-format.xls"), TestInfo.class);

        assertNotNull(result);
        assertThat(result.size(), is(4));

        TestInfo test1 = result.get(0);
        TestInfo test2 = result.get(1);
        TestInfo test3 = result.get(2);
        TestInfo test4 = result.get(3);

        assertThat(test1.getAmount().intValue(), is(123));
        assertThat(test2.getAmount().intValue(), is(25));
        assertThat(test3.getAmount().intValue(), is(-50));
        assertThat(test4.getAmount().intValue(), is(-65));
    }
}
