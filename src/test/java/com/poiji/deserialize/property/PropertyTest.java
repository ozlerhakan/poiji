package com.poiji.deserialize.property;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.property.model.PropertyEntity;
import com.poiji.exception.InvalidExcelFileExtension;
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
        PropertyEntity deserializedProperties = Poiji.fromExcelProperties(
                new File("src/test/resources/core_properties_set.xlsx"),
                PropertyEntity.class);

        assertExcelProperties(deserializedProperties, 1588771680000L);
    }

    @Test
    public void readPropertiesInputStream() throws FileNotFoundException {
        PropertyEntity deserializedProperties = Poiji.fromExcelProperties(new FileInputStream(
                new File("src/test/resources/core_properties_set.xlsx")),
                PoijiExcelType.XLSX,
                PropertyEntity.class);

        assertExcelProperties(deserializedProperties, 1588771680000L);
    }

    @Test
    public void readCorePropertiesWithPassword() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().password("testPassword").build();
        PropertyEntity deserializedProperties = Poiji.fromExcelProperties(
                new File("src/test/resources/core_properties_set_password.xlsx"),
                PropertyEntity.class, options);

        assertExcelProperties(deserializedProperties, 1588772003000L);
    }

    @Test
    public void readCorePropertiesInputStreamWithPassword() throws FileNotFoundException {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().password("testPassword").build();
        PropertyEntity deserializedProperties = Poiji.fromExcelProperties(new FileInputStream(
                new File("src/test/resources/core_properties_set_password.xlsx")),
                PoijiExcelType.XLSX,
                PropertyEntity.class, options);

        assertExcelProperties(deserializedProperties, 1588772003000L);
    }

    @Test(expected = InvalidExcelFileExtension.class)
    public void readPropertiesXls() {
        Poiji.fromExcelProperties(
                new File("src/test/resources/cars.xls"),
                PropertyEntity.class);
    }

    @Test(expected = InvalidExcelFileExtension.class)
    public void readPropertiesInputStreamXls() throws FileNotFoundException {
        Poiji.fromExcelProperties(
                new FileInputStream(new File("src/test/resources/cars.xls")),
                PoijiExcelType.XLS,
                PropertyEntity.class);
    }

    @Test(expected = InvalidExcelFileExtension.class)
    public void readPropertiesInvalidType() {
        Poiji.fromExcelProperties(
                new File("src/test/resources/cars.xl"),
                PropertyEntity.class);
    }

    private void assertExcelProperties(PropertyEntity deserializedProperties, long modified) {
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
