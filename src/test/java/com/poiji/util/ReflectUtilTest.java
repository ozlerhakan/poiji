package com.poiji.util;

import com.poiji.exception.PoijiExcelType;
import com.poiji.exception.PoijiInstantiationException;
import org.junit.BeforeClass;
import org.junit.Test;

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

    static class PackageModel {
    }

    static class PackageModelWithPrivateConstructor {
        private PackageModelWithPrivateConstructor() {
        }
    }

    static private class PrivateModel {
    }

}
