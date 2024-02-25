package com.poiji.util;

import com.poiji.annotation.ExcelCellsJoinedByName;
import com.poiji.exception.PoijiExcelType;
import com.poiji.exception.PoijiInstantiationException;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ReflectPermission;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeTrue;

public class ReflectUtilTest {

    @BeforeClass
    public static void ensureSecurityManagerPermits() {
        SecurityManager securityManager = System.getSecurityManager();
        boolean permitted = true;
        if (securityManager != null) {
            try {
                securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
            } catch (SecurityException e) {
                permitted = false;
            }
        }
        assumeTrue(permitted);
    }

    @Test
    public void newInstanceOfPackageModel() {
        PackageModel obj = ReflectUtil.newInstanceOf(PackageModel.class);
        assertNotNull(obj);
    }

    @Test
    public void newInstanceOfPackageModelWithPrivateConstructor() {
        PackageModelWithPrivateConstructor obj = ReflectUtil.newInstanceOf(PackageModelWithPrivateConstructor.class);
        assertNotNull(obj);
    }

    @Test
    public void newInstanceOfPrivateModel() {
        PrivateModel obj = ReflectUtil.newInstanceOf(PrivateModel.class);
        assertNotNull(obj);
    }

    @Test(expected = PoijiInstantiationException.class)
    public void newInstanceOfEnum() {
        ReflectUtil.newInstanceOf(PoijiExcelType.class);
    }

    @Test(expected = ClassCastException.class)
    public void illegalCast() throws NoSuchFieldException, SecurityException {
        PackageModel pm = new PackageModel();
        Object o = "new_value";
        Field field = PackageModel.class.getDeclaredField("field");

        ReflectUtil.putFieldMultiValueMapData(field, "Artist", o, pm);
    }

    static class PackageModel {
        @ExcelCellsJoinedByName(expression = "Artist")
        private String field = "";
    }

    static class PackageModelWithPrivateConstructor {
        private PackageModelWithPrivateConstructor() {
        }
    }

    static private class PrivateModel {
    }

}
