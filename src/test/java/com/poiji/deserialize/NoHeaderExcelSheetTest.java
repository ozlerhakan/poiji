package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.TeamMemberNoHeader;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class NoHeaderExcelSheetTest {

    private String path;

    public NoHeaderExcelSheetTest(String path) {
        super();
        this.path = path;
    }


    @Parameters
    public static Iterable<Object[]> excel() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/no-header-excel-sheet.xls"},
                {"src/test/resources/no-header-excel-sheet.xlsx"}
        });
    }

    @Test
    public void shouldRetrieveDataWithNoHeaderLimit1Row() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .headerStart(0)
                .headerCount(0)
                .limit(1)
                .trimCellValue(true)
                .build();

        List<TeamMemberNoHeader> teamMembers = Poiji.fromExcel(new File(path), TeamMemberNoHeader.class, options);

        assertNotNull(teamMembers);
        assertThat(teamMembers.size(), is(1));
        TeamMemberNoHeader member = teamMembers.get(0);
        assertThat(member.getFunction(), is("Rider"));
        assertThat(member.getLastName(), is("ANDERSEN"));
        assertThat(member.getFirstName(), is("Michael Valgren"));
    }

    @Test
    public void shouldRetrieveDataWithNoHeaderLimit1RowAndSkip1Row() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .headerStart(0)
                .headerCount(0)
                .skip(1)
                .limit(1)
                .trimCellValue(true)
                .build();

        List<TeamMemberNoHeader> teamMembers = Poiji.fromExcel(new File(path), TeamMemberNoHeader.class, options);

        assertNotNull(teamMembers);
        assertThat(teamMembers.size(), is(1));
        TeamMemberNoHeader member = teamMembers.get(0);
        assertThat(member.getFunction(), is("Rider"));
        assertThat(member.getLastName(), is("AAEN"));
        assertThat(member.getFirstName(), is("Jonas"));
    }
}
