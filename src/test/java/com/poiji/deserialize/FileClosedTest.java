package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.ProductExcelDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class FileClosedTest {

    private String path;

    public FileClosedTest(String path) {
        this.path = path;
    }

    @Parameters
    public static Object[] excel() {
        return new Object[]{"src/test/resources/test.xls", "src/test/resources/test.xlsx"};
    }

    @Test
    public void shouldMapExcelToJava() {
        File file = new File(path);
        System.out.println(file.toString());
        List<ProductExcelDTO> products = Poiji.fromExcel(file, ProductExcelDTO.class);
        assertThat(products, notNullValue());
        assertThat(products.size(), not(0));
        assertThat(file.renameTo(file), is(true));
    }

}
