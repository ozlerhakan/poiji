package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Sample;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.poiji.util.Data.unmarshallingSamples;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.fail;

/**
 * Created by hakan on 8/08/2018.
 */
@RunWith(Parameterized.class)
public class NullValueTest {

    private String path;
    private boolean preferNullOverDefault;
    private List<Sample> expectedSamples;
    private Class<?> expectedException;

    public NullValueTest(String path,
                         List<Sample> expectedSamples,
                         boolean preferNullOverDefault,
                         Class<?> expectedException) {
        this.path = path;
        this.expectedSamples = expectedSamples;
        this.preferNullOverDefault = preferNullOverDefault;
        this.expectedException = expectedException;
    }

    @Parameterized.Parameters(name = "{index}: ({0})={1}")
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/sample.xlsx", unmarshallingSamples(), false, null},
                {"src/test/resources/sample.xls", unmarshallingSamples(), true, null},
        });
    }

    @Test
    public void shouldMapEmptyCells() {

        try {

            PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings().preferNullOverDefault(this.preferNullOverDefault).build();
            List<Sample> samples = Poiji.fromExcel(new File(path), Sample.class, poijiOptions);

            assertThat(samples, notNullValue());
            assertThat(samples.size(), not(0));
            assertThat(samples.size(), is(expectedSamples.size()));

            Sample actualSample1 = samples.get(0);
            Sample actualSample2 = samples.get(1);

            Sample expectedSample1 = expectedSamples.get(0);
            Sample expectedSample2 = expectedSamples.get(1);

            assertThat(actualSample1.toString(), is(expectedSample1.toString()));
            assertThat(actualSample2.toString(), is(expectedSample2.toString()));

        } catch (Exception e) {
            if (expectedException == null) {
                fail(e.getMessage());
            } else {
                assertThat(e, instanceOf(expectedException));
            }
        }
    }

}
