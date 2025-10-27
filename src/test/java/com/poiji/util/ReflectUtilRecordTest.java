package com.poiji.util;

import com.poiji.exception.PoijiInstantiationException;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Tests for ReflectUtil record support
 */
public class ReflectUtilRecordTest {

    // Simple POJO for testing
    public static class SimplePojo {
        private String name;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
    }

    @Test
    public void shouldReturnFalseForNonRecordClass() {
        // Test that isRecord returns false for regular classes
        assertFalse(ReflectUtil.isRecord(SimplePojo.class));
        assertFalse(ReflectUtil.isRecord(String.class));
        assertFalse(ReflectUtil.isRecord(Integer.class));
    }

    @Test
    public void shouldThrowExceptionWhenCreatingRecordInstanceFromNonRecord() {
        // Test that newRecordInstance throws exception for non-record types
        Map<String, Object> values = new HashMap<>();
        values.put("name", "test");
        
        try {
            ReflectUtil.newRecordInstance(SimplePojo.class, values);
            fail("Expected PoijiInstantiationException");
        } catch (PoijiInstantiationException e) {
            // Expected
            assertThat(e.getMessage().contains("is not a record"), is(true));
        }
    }

    @Test
    public void shouldCreatePojoInstanceWithNewInstanceOf() {
        // Test that regular class instantiation still works
        SimplePojo pojo = ReflectUtil.newInstanceOf(SimplePojo.class);
        assertThat(pojo != null, is(true));
    }
}
