package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.AutoImport;
import com.poiji.option.PoijiOptions;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NullReferenceTest {

    @Test
    public void shouldReturnEmptyWhenCellReferenceNull() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().disableXLSXNumberCellFormat().build();

        List<AutoImport> result = Poiji.fromExcel(new File("src/test/resources/autos2.xlsx"), AutoImport.class, options);

        assertNotNull(result);
        assertThat(result.size(), is(20));
        assertNull(result.get(0).getPmercado());

    }
}
