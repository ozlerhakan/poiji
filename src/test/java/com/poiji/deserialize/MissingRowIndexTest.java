package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.Person;
import com.poiji.deserialize.model.byname.Organisation;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

public class MissingRowIndexTest {

    @Test
    public void emptyLine() {

        List<Organisation> organisations = Poiji.fromExcel(
                new File("src/test/resources/missing-row-1.xlsx"),
                Organisation.class,
                PoijiOptions.PoijiOptionsBuilder.settings()
                        .sheetName("Organisation")
                        .build()
        );
        assertThat(organisations, notNullValue());
        assertThat(organisations.size(), is(2));
        assertThat(organisations.stream().map(Organisation::getRowIndex).min(Integer::compareTo).get(), is(2));
        assertThat(organisations.stream().map(Organisation::getRowIndex).max(Integer::compareTo).get(), is(3));
    }

    @Test
    public void nullLine() {

        List<Organisation> organisations = Poiji.fromExcel(
                new File("src/test/resources/missing-row-2.xlsx"),
                Organisation.class,
                PoijiOptions.PoijiOptionsBuilder.settings()
                        .sheetName("Organisation")
                        .build()
        );

        assertThat(organisations, notNullValue());
        assertThat(organisations.size(), is(4));
        assertThat(organisations.stream().map(Organisation::getRowIndex).min(Integer::compareTo).get(), is(2));
        assertThat(organisations.stream().map(Organisation::getRowIndex).max(Integer::compareTo).get(), is(5));
    }
}
