package com.poiji.util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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