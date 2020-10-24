package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.ProductExcelDTO;
import com.poiji.exception.PoijiExcelType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by hakan on 2019-01-10
 */
@RunWith(Parameterized.class)
public class DeserializerCaseTest {

    private String path;
    private PoijiExcelType poijiExcelType;

    public DeserializerCaseTest(String path, PoijiExcelType type) {
        this.path = path;
        this.poijiExcelType = type;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/test.xlsx", PoijiExcelType.XLSX},
                {"src/test/resources/test.xls", PoijiExcelType.XLS},
        });
    }

    @Test
    public void shouldMapExcelToJava() {
        try (InputStream stream = new FileInputStream(new File(path))) {
            List<ProductExcelDTO> products = Poiji.fromExcel(stream, poijiExcelType, ProductExcelDTO.class);
            assertThat(products, notNullValue());
            assertThat(products.size(), not(0));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
