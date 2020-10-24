package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.TeamMember;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class EmptyExcelSheetTest {

    private String path;

    public EmptyExcelSheetTest(String path) {
        super();
        this.path = path;
    }


    @Parameters
    public static Object[] excel() {
        return new Object[]{"src/test/resources/old-excel-sheet-format-example.xlsx"};
    }

    @Test
    public void shouldRetrieveDataRow() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .headerStart(1)
                .limit(1)
                .trimCellValue(true)
                .build();

        List<TeamMember> teamMembers = Poiji.fromExcel(new File(path), TeamMember.class, options);

        assertNotNull(teamMembers);
        assertThat(teamMembers.size(), is(1));
        TeamMember member = teamMembers.get(0);
        assertThat(member.getFunction(), is("Rider"));
        assertThat(member.getLastName(), is("ANDERSEN"));
        assertThat(member.getFirstName(), is("Michael Valgren"));
        assertThat(member.getTeamName(), is("TEAM DIMENSION DATA"));
    }
}
