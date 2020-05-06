package com.poiji.deserialize.metadata;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.metadata.model.CorePropertyEntity;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class PropertyTest {

    @Test
    public void readProperties() {
        CorePropertyEntity deserializedProperties = Poiji.fromExcelProperties(
                new File("src/test/resources/core_properties_set.xlsx"),
                CorePropertyEntity.class);

        assertExcelProperties(deserializedProperties, 1588771680000L);
    }

    @Test
    public void readPropertiesInputStream() throws FileNotFoundException {
        CorePropertyEntity deserializedProperties = Poiji.fromExcelProperties(new FileInputStream(
                new File("src/test/resources/core_properties_set.xlsx")),
                PoijiExcelType.XLSX,
                CorePropertyEntity.class);

        assertExcelProperties(deserializedProperties, 1588771680000L);
    }

    @Test
    public void readCorePropertiesWithPassword() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().password("testPassword").build();
        CorePropertyEntity deserializedProperties = Poiji.fromExcelProperties(
                new File("src/test/resources/core_properties_set_password.xlsx"),
                CorePropertyEntity.class, options);

        assertExcelProperties(deserializedProperties, 1588772003000L);
    }

    @Test
    public void readCorePropertiesInputStreamWithPassword() throws FileNotFoundException {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().password("testPassword").build();
        CorePropertyEntity deserializedProperties = Poiji.fromExcelProperties(new FileInputStream(
                new File("src/test/resources/core_properties_set_password.xlsx")),
                PoijiExcelType.XLSX,
                CorePropertyEntity.class, options);

        assertExcelProperties(deserializedProperties, 1588772003000L);
    }

    private void assertExcelProperties(CorePropertyEntity deserializedProperties, long modified) {
        assertThat(deserializedProperties.getTitle(), is("TestTitle"));
        assertThat(deserializedProperties.getCategory(), is("TestCategory"));
        assertThat(deserializedProperties.getContentStatus(), is("TestStatus"));
        assertThat(deserializedProperties.getCreator(), is("TestAuthor"));
        assertThat(deserializedProperties.getCreated(), is(new Date(1588689638000L)));
        assertThat(deserializedProperties.getDescription(), is("TestDescription"));
        assertThat(deserializedProperties.getKeywords(), is("TestKeywords"));
        assertThat(deserializedProperties.getLastPrinted(), is(new Date(1588768892000L)));
        assertThat(deserializedProperties.getModified(), is(new Date(modified)));
        assertThat(deserializedProperties.getSubject(), is("TestSubject"));
        assertThat(deserializedProperties.getCustomProperty(), is("customValue"));
        //Only testing, if field is null, as newer Excel versions don't set this field any more.
        // An older excel version is required to set this field.
        assertThat(deserializedProperties.getRevision(), is(nullValue()));
    }
}
