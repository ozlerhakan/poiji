package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.TestIllegalAccessInfo;
import com.poiji.exception.IllegalCastException;
import org.junit.Test;

import java.io.File;
import java.util.List;

@SuppressWarnings("unused")
public class IllegalAcessTest {

    @Test(expected = IllegalCastException.class)
    public void shouldThrowExceptionXLS() {
        List<TestIllegalAccessInfo> result = Poiji.fromExcel(new File("src/test/resources/test-format.xls"), TestIllegalAccessInfo.class);
    }

    @Test(expected = IllegalCastException.class)
    public void shouldThrowExceptionXLSX() {
        List<TestIllegalAccessInfo> result = Poiji.fromExcel(new File("src/test/resources/test-format.xlsx"), TestIllegalAccessInfo.class);
    }
}
