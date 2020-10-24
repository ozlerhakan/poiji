package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byname.OrgWithUnknownCellsByName;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class IgnoreWhitespacesTest {

    private String path;

    public IgnoreWhitespacesTest(String path) {
        this.path = path;
    }

    @Parameterized.Parameters
    public static List<String> excel() {
        return asList("src/test/resources/ignore_whitespaces.xlsx", "src/test/resources/ignore_whitespaces.xls");
    }

    @Test
    public void ignoreWhitespaceColumnNames() {
        List<OrgWithUnknownCellsByName> organisations = Poiji.fromExcel(
                new File(path),
                OrgWithUnknownCellsByName.class,
                PoijiOptions.PoijiOptionsBuilder.settings()
                        .sheetName("Organisation")
                        .caseInsensitive(true)
                        .ignoreWhitespaces(true)
                        .build()
        );

        assertThat(organisations, notNullValue());
        assertThat(organisations.size(), is(2));

        OrgWithUnknownCellsByName firstRow = organisations.stream()
                .filter(org -> org.getId().equals("CrEaTe"))
                .findFirst()
                .get();
        assertThat(firstRow.getUnknownCells().size(), is(1));
        assertThat(firstRow.getUnknownCells().get("region"), is("EMEA"));


        OrgWithUnknownCellsByName secondRow = organisations.stream()
                .filter(org -> org.getId().equals("8d9e6430-8626-4556-8004-079085d2df2d"))
                .findFirst()
                .get();
        assertThat(secondRow.getUnknownCells().size(), is(1));
        assertThat(secondRow.getUnknownCells().get("region"), is("NA"));
    }

}
