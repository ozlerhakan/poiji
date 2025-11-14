package com.poiji.util;

import com.poiji.exception.PoijiInstantiationException;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Additional tests for ReflectUtil record support edge cases
 */
public class ReflectUtilRecordEdgeCaseTest {

    // Test record with primitive types
    public record PrimitiveRecord(
            boolean boolValue,
            byte byteValue,
            short shortValue,
            int intValue,
            long longValue,
            float floatValue,
            double doubleValue,
            char charValue
    ) {}

    // Test record with nullable types
    public record NullableRecord(
            String name,
            Integer age,
            Double salary
    ) {}

    // Test record with MultiValuedMap
    public record MultiValueMapRecord(
            String id,
            MultiValuedMap<String, String> values
    ) {}

    @Test
    public void shouldHandleAllPrimitiveTypesWithDefaults() {
        // Test that all primitive types get proper default values
        Map<String, Object> values = new HashMap<>();
        // Don't provide any values, should use defaults
        
        PrimitiveRecord record = ReflectUtil.newRecordInstance(PrimitiveRecord.class, values);
        
        assertThat(record, notNullValue());
        assertThat(record.boolValue(), is(false));
        assertThat(record.byteValue(), is((byte) 0));
        assertThat(record.shortValue(), is((short) 0));
        assertThat(record.intValue(), is(0));
        assertThat(record.longValue(), is(0L));
        assertThat(record.floatValue(), is(0.0f));
        assertThat(record.doubleValue(), is(0.0));
        assertThat(record.charValue(), is('\0'));
    }

    @Test
    public void shouldHandleNullableFieldsInRecords() {
        // Test records with null object fields
        Map<String, Object> values = new HashMap<>();
        values.put("name", null);
        values.put("age", null);
        values.put("salary", null);
        
        NullableRecord record = ReflectUtil.newRecordInstance(NullableRecord.class, values);
        
        assertThat(record, notNullValue());
        assertThat(record.name() == null, is(true));
        assertThat(record.age() == null, is(true));
        assertThat(record.salary() == null, is(true));
    }

    @Test
    public void shouldCreateEmptyMultiValuedMapForNullField() {
        // Test that null MultiValuedMap fields get initialized with empty map
        Map<String, Object> values = new HashMap<>();
        values.put("id", "test-id");
        // Don't provide values field - should create empty MultiValuedMap
        
        MultiValueMapRecord record = ReflectUtil.newRecordInstance(MultiValueMapRecord.class, values);
        
        assertThat(record, notNullValue());
        assertThat(record.id(), is("test-id"));
        assertThat(record.values(), notNullValue());
        assertThat(record.values().isEmpty(), is(true));
    }

    @Test
    public void shouldHandleProvidedMultiValuedMap() {
        // Test that provided MultiValuedMap is used correctly
        Map<String, Object> values = new HashMap<>();
        values.put("id", "test-id");
        
        MultiValuedMap<String, String> providedMap = new ArrayListValuedHashMap<>();
        providedMap.put("key1", "value1");
        providedMap.put("key1", "value2");
        values.put("values", providedMap);
        
        MultiValueMapRecord record = ReflectUtil.newRecordInstance(MultiValueMapRecord.class, values);
        
        assertThat(record, notNullValue());
        assertThat(record.id(), is("test-id"));
        assertThat(record.values(), notNullValue());
        assertThat(record.values().get("key1").size(), is(2));
    }

    @Test
    public void shouldHandlePartiallyPopulatedPrimitiveRecord() {
        // Test record with some primitive values provided and others using defaults
        Map<String, Object> values = new HashMap<>();
        values.put("intValue", 42);
        values.put("longValue", 100L);
        values.put("boolValue", true);
        // Other primitives should use defaults
        
        PrimitiveRecord record = ReflectUtil.newRecordInstance(PrimitiveRecord.class, values);
        
        assertThat(record, notNullValue());
        assertThat(record.boolValue(), is(true));
        assertThat(record.intValue(), is(42));
        assertThat(record.longValue(), is(100L));
        // Defaults for unprovided fields
        assertThat(record.byteValue(), is((byte) 0));
        assertThat(record.shortValue(), is((short) 0));
        assertThat(record.floatValue(), is(0.0f));
        assertThat(record.doubleValue(), is(0.0));
        assertThat(record.charValue(), is('\0'));
    }

    @Test
    public void shouldHandleEmptyMapForRecord() {
        // Test creating record with completely empty map
        Map<String, Object> values = new HashMap<>();
        
        try {
            NullableRecord record = ReflectUtil.newRecordInstance(NullableRecord.class, values);
            assertThat(record, notNullValue());
            // All fields should be null for object types
            assertThat(record.name() == null, is(true));
            assertThat(record.age() == null, is(true));
            assertThat(record.salary() == null, is(true));
        } catch (PoijiInstantiationException e) {
            fail("Should handle empty map for records with nullable fields");
        }
    }
}
