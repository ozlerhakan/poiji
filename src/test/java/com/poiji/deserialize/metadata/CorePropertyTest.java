package com.poiji.deserialize.metadata;

import com.poiji.bind.Poiji;
import com.poiji.deserialize.metadata.model.CorePropertyEntity;
import com.poiji.deserialize.model.byid.Person;
import com.poiji.option.PoijiOptions;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class CorePropertyTest {

    @Test
    public void readCoreProperties() {
        CorePropertyEntity deserializedProperties = Poiji.fromExcelProperties(new File("src/test/resources/corePropertiesSet.xlsx"),
                CorePropertyEntity.class);
    }

    @Test
    public void readCorePropertiesWithPassword() {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().password("testPassword").build();
        CorePropertyEntity deserializedProperties = Poiji.fromExcelProperties(new File("src/test/resources/core_properties_set_password.xlsx"),
                CorePropertyEntity.class, options);
    }
}
