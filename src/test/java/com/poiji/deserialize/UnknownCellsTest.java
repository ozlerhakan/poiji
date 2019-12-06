package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.OrgWithUnknownCells;
import com.poiji.deserialize.model.byname.OrgWithUnknownCellsByName;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class UnknownCellsTest {

    private String path;

    public UnknownCellsTest(String path) {
        this.path = path;
    }

    @Parameterized.Parameters
    public static List<String> excel() {
        return Arrays.asList(
                "src/test/resources/unknown-cells.xlsx",
                "src/test/resources/unknown-cells.xls"
        );
    }

    @Test
    public void byName() {
        List<OrgWithUnknownCellsByName> organisations = Poiji.fromExcel(
                new File(path),
                OrgWithUnknownCellsByName.class,
                PoijiOptions.PoijiOptionsBuilder.settings()
                        .sheetName("Organisation")
                        .build()
        );

        assertThat(organisations, notNullValue());
        assertThat(organisations.size(), is(2));

        OrgWithUnknownCellsByName firstRow = organisations.stream()
                .filter(org -> org.getId().equals("CrEaTe"))
                .findFirst()
                .get();
        assertThat(firstRow.getUnknownCells().size(), is(1));
        assertThat(firstRow.getUnknownCells().get("Region"), is("EMEA"));


        OrgWithUnknownCellsByName secondRow = organisations.stream()
                .filter(org -> org.getId().equals("8d9e6430-8626-4556-8004-079085d2df2d"))
                .findFirst()
                .get();
        assertThat(secondRow.getUnknownCells().size(), is(1));
        assertThat(secondRow.getUnknownCells().get("Region"), is("NA"));
    }

    @Test
    public void byIndex() {
        List<OrgWithUnknownCells> organisations = Poiji.fromExcel(
                new File(path),
                OrgWithUnknownCells.class,
                PoijiOptions.PoijiOptionsBuilder.settings()
                        .sheetName("Organisation")
                        .build()
        );

        assertThat(organisations, notNullValue());
        assertThat(organisations.size(), is(2));

        OrgWithUnknownCells firstRow = organisations.stream()
                .filter(org -> org.getId().equals("CrEaTe"))
                .findFirst()
                .get();
        assertThat(firstRow.getUnknownCells().size(), is(1));
        assertThat(firstRow.getUnknownCells().get("Region"), is("EMEA"));


        OrgWithUnknownCells secondRow = organisations.stream()
                .filter(org -> org.getId().equals("8d9e6430-8626-4556-8004-079085d2df2d"))
                .findFirst()
                .get();
        assertThat(secondRow.getUnknownCells().size(), is(1));
        assertThat(secondRow.getUnknownCells().get("Region"), is("NA"));
    }
}
