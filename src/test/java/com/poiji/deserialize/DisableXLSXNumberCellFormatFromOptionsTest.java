package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.TestInfoXLSX;
import com.poiji.option.PoijiOptions;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class DisableXLSXNumberCellFormatFromOptionsTest {

    @Test
    public void shouldDisableNumberAllCellFormatFromPoijiOptions() {

        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .disableXLSXNumberCellFormat()
                .build();

        List<TestInfoXLSX> result = Poiji.fromExcel(new File("src/test/resources/test-format.xlsx"), TestInfoXLSX.class, options);

        assertNotNull(result);
        assertThat(result.size(), is(4));

        TestInfoXLSX test1 = result.get(0);
        TestInfoXLSX test2 = result.get(1);
        TestInfoXLSX test3 = result.get(2);
        TestInfoXLSX test4 = result.get(3);

        assertThat(test1.getAmount().intValue(), is(123));
        assertThat(test2.getAmount().intValue(), is(25));
        assertThat(test3.getAmount().intValue(), is(-50));
        assertThat(test4.getAmount().intValue(), is(-65));
    }
}
