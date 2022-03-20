package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byname.OrganisationByName;
import com.poiji.option.PoijiOptions;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class MissingRowIndexTest {

    @Test
    public void emptyLineXlsx() {
        emptyLineTest("src/test/resources/missing-row-1.xlsx");
    }

    @Test
    public void emptyLineXls() {
        emptyLineTest("src/test/resources/missing-row-1.xls");
    }

    private void emptyLineTest(String excelFilePath) {
        List<OrganisationByName> organisations = Poiji.fromExcel(
                new File(excelFilePath),
                OrganisationByName.class,
                PoijiOptions.PoijiOptionsBuilder.settings()
                        .sheetName("Organisation")
                        .build()
        );
        assertThat(organisations, notNullValue());
        assertThat(organisations.size(), is(2));
        assertThat(organisations.stream().map(OrganisationByName::getRowIndex).min(Integer::compareTo).get(), is(2));
        assertThat(organisations.stream().map(OrganisationByName::getRowIndex).max(Integer::compareTo).get(), is(3));
    }

    @Test
    public void nullLineXlsx() {
        nullLineTest("src/test/resources/missing-row-2.xlsx");
    }

    @Test
    public void nullLineXls() {
        nullLineTest("src/test/resources/missing-row-2.xls");
    }

    private void nullLineTest(String excelFilePath) {
        List<OrganisationByName> organisations = Poiji.fromExcel(
                new File(excelFilePath),
                OrganisationByName.class,
                PoijiOptions.PoijiOptionsBuilder.settings()
                        .sheetName("Organisation")
                        .build()
        );

        assertThat(organisations, notNullValue());
        assertThat(organisations.size(), is(4));
        assertThat(organisations.stream().map(OrganisationByName::getRowIndex).min(Integer::compareTo).get(), is(2));
        assertThat(organisations.stream().map(OrganisationByName::getRowIndex).max(Integer::compareTo).get(), is(5));
    }
}
