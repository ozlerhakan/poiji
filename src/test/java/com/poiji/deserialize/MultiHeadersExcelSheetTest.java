package com.poiji.deserialize;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.model.byid.PersonCreditInfo;
import com.poiji.option.PoijiOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class MultiHeadersExcelSheetTest {

    private String path;

    public MultiHeadersExcelSheetTest(String path) {
        this.path = path;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"src/test/resources/test-multi-headers.xlsx"},
                {"src/test/resources/test-multi-headers.xls"}
        });
    }

    @Test
    public void shouldRetrieveDataWith2LevelHeaders() {
        try {
            PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                    .headerCount(2)
                    .trimCellValue(true)
                    .build();
            List<PersonCreditInfo> actualPersonalCredits =
                    Poiji.fromExcel(new File(path), PersonCreditInfo.class, options);

            assertThat(actualPersonalCredits, notNullValue());
            assertThat(actualPersonalCredits.size(), not(0));
            assertThat(actualPersonalCredits.size(), is(3));

            PersonCreditInfo personCreditInfo1 = actualPersonalCredits.get(0);
            PersonCreditInfo personCreditInfo2 = actualPersonalCredits.get(1);

            PersonCreditInfo.PersonInfo expectedPerson1 = personCreditInfo1.getPersonInfo();
            PersonCreditInfo.CardInfo expectedCard1 = personCreditInfo1.getCardInfo();
            PersonCreditInfo.PersonInfo expectedPerson2 = personCreditInfo2.getPersonInfo();
            PersonCreditInfo.CardInfo expectedCard2 = personCreditInfo2.getCardInfo();

            assertThat(expectedPerson1.getAge(), is(21));
            assertThat(expectedPerson2.getCity(), is("Greenbelt"));
            assertThat(expectedPerson1.getName(), is("John Doe"));
            assertThat(expectedPerson2.getState(), is("Maryland"));
            assertThat(expectedPerson1.getZipCode(), is("22349"));

            assertThat(expectedCard1.getType(), is("VISA"));
            assertThat(expectedCard2.getLast4Digits(), is("2345"));
            assertThat(expectedCard1.getExpirationDate(), is("Jan-21"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
