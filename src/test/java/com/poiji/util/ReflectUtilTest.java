package com.poiji.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeTrue;

import java.lang.reflect.ReflectPermission;

import org.junit.BeforeClass;
import org.junit.Test;

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

    static class PackageModel {
    }

    static class PackageModelWithPrivateConstructor {
        private PackageModelWithPrivateConstructor() {
        }
    }

    static private class PrivateModel {
    }

}
