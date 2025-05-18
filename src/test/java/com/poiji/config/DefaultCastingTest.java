package com.poiji.config;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class DefaultCastingTest {

    @Test
    public void valueMethodsShouldBeProtectedOrPublic() {
        Method[] allMethods = DefaultCasting.class.getMethods();
        for (Method method : allMethods) {
            if (method.getName().endsWith("Value")) {
                assertThat(
                        "Method " + method.getName() + " should be protected or public",
                        Modifier.isProtected(method.getModifiers()) || Modifier.isPublic(method.getModifiers()),
                        is(true)
                );
            }
        }
    }
}
