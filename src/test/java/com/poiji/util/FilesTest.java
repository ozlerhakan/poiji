package com.poiji.util;

import java.io.File;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class FilesTest {

    private Files files;

    @Before
    public void setUp() throws Exception {
        files = Files.getInstance();

    }

    @Test
    public void getInstance() throws Exception {

        assertNotNull(files);
    }

    @Test
    public void getExtension() throws Exception {

        assertThat(files.getExtension("cars.xl"),is(".xl"));
        assertThat(files.getExtension("cars.xls"),is(".xls"));
        assertThat(files.getExtension("cars.xlsx"),is(".xlsx"));
    }

}