package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byname.DataRowModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class EmptyRowTest {

    private String path;

    public EmptyRowTest(String path) {
        super();
        this.path = path;
    }

    @Parameters
    public static Object[] excel() {
        return new Object[]{"src/test/resources/synonyms-test.xlsx"};
    }

    @Test
    public void shouldRetrieveDataRow() {

        List<DataRowModel> rows = Poiji.fromExcel(new File(path), DataRowModel.class);
        assertThat(rows, notNullValue());
        assertThat(rows.size(), is(101));
    }
}
