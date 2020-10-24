package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byname.CellFormatModel;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class XLSCellFormatTest {

    @Test
    public void shouldRetrieveCellValueWithoutFormatXLS() {
        List<CellFormatModel> result = Poiji.fromExcel(new File("src/test/resources/employees_format.xls"), CellFormatModel.class);

        assertThat(result, notNullValue());
        CellFormatModel cellFormatModel = result.get(1);
        assertThat(cellFormatModel.getAge(), is(40));
    }

}
