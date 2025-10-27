package com.poiji.util;

import com.poiji.annotation.ExcelCellRange;
import com.poiji.exception.IllegalCastException;
import com.poiji.exception.PoijiInstantiationException;
import org.apache.commons.collections4.MultiValuedMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class ReflectUtil {

    private ReflectUtil() {
    }

    public static <T> T newInstanceOf(Class<T> type) {
        T obj;
        try {
            Constructor<T> constructor = type.getDeclaredConstructor();
            if (!constructor.canAccess(null)) {
                constructor.setAccessible(true);
            }
            obj = constructor.newInstance();
        } catch (Exception ex) {
            throw new PoijiInstantiationException("Cannot create a new instance of " + type.getName(), ex);
        }

        return obj;
    }

    /**
     * Creates an instance of a record class using its canonical constructor with provided values.
     * This method uses reflection to maintain compatibility with Java 11 while supporting records on Java 17+.
     * 
     * @param <T> the type of the record
     * @param type the record class
     * @param recordValues a map of field names to their values
     * @return a new instance of the record
     */
    public static <T> T newRecordInstance(Class<T> type, Map<String, Object> recordValues) {
        if (!isRecord(type)) {
            throw new PoijiInstantiationException("Type " + type.getName() + " is not a record",
                    new IllegalArgumentException("Expected a record type"));
        }

        try {
            // Use reflection to call getRecordComponents() to maintain Java 11 compatibility
            Method getRecordComponentsMethod = Class.class.getMethod("getRecordComponents");
            Object[] components = (Object[]) getRecordComponentsMethod.invoke(type);
            
            Class<?>[] parameterTypes = new Class<?>[components.length];
            Object[] args = new Object[components.length];

            // Get methods from RecordComponent class using reflection
            Class<?> recordComponentClass = Class.forName("java.lang.reflect.RecordComponent");
            Method getNameMethod = recordComponentClass.getMethod("getName");
            Method getTypeMethod = recordComponentClass.getMethod("getType");

            for (int i = 0; i < components.length; i++) {
                Object component = components[i];
                String componentName = (String) getNameMethod.invoke(component);
                Class<?> componentType = (Class<?>) getTypeMethod.invoke(component);
                
                parameterTypes[i] = componentType;
                Object value = recordValues.get(componentName);
                
                // If value is null, use default values for primitives or create empty MultiValuedMap
                if (value == null) {
                    if (componentType.isPrimitive()) {
                        value = getDefaultValue(componentType);
                    } else if (componentType.getName().contains("MultiValuedMap")) {
                        // Create empty MultiValuedMap for null values
                        try {
                            value = Class.forName("org.apache.commons.collections4.multimap.ArrayListValuedHashMap")
                                    .getDeclaredConstructor()
                                    .newInstance();
                        } catch (Exception e) {
                            // If we can't create ArrayListValuedHashMap, leave as null
                        }
                    }
                }
                
                args[i] = value;
            }

            Constructor<T> constructor = type.getDeclaredConstructor(parameterTypes);
            if (!constructor.canAccess(null)) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance(args);
        } catch (Exception ex) {
            throw new PoijiInstantiationException("Cannot create a new instance of record " + type.getName(), ex);
        }
    }

    /**
     * Returns the default value for a primitive type.
     */
    private static Object getDefaultValue(Class<?> type) {
        if (type == boolean.class) {
            return false;
        } else if (type == byte.class) {
            return (byte) 0;
        } else if (type == short.class) {
            return (short) 0;
        } else if (type == int.class) {
            return 0;
        } else if (type == long.class) {
            return 0L;
        } else if (type == float.class) {
            return 0.0f;
        } else if (type == double.class) {
            return 0.0;
        } else if (type == char.class) {
            return '\0';
        }
        return null;
    }

    /**
     * Checks if a class is a record using reflection to maintain Java 11 compatibility.
     * Records are only available in Java 16+, so this method will return false on earlier versions.
     * 
     * @param type the class to check
     * @return true if the class is a record, false otherwise
     */
    public static boolean isRecord(Class<?> type) {
        try {
            // Use reflection to call isRecord() method to maintain Java 11 compatibility
            Method isRecordMethod = Class.class.getMethod("isRecord");
            return (Boolean) isRecordMethod.invoke(type);
        } catch (NoSuchMethodException e) {
            // isRecord() method doesn't exist, we're running on Java < 16
            return false;
        } catch (Exception e) {
            // Some other error occurred
            return false;
        }
    }

    /**
     * Finds a particular annotation on a class and checks subtypes marked with
     * ExcelCellRange recursively.
     * <p>
     * Recursively does not refer to super classes.
     */
    static <T, A extends Annotation> Collection<A> findRecursivePoijiAnnotations(Class<T> typeToInspect,
            Class<A> annotationType) {
        List<A> annotations = new ArrayList<>();

        for (Field field : typeToInspect.getDeclaredFields()) {
            Annotation excelCellRange = field.getAnnotation(ExcelCellRange.class);
            if (excelCellRange != null) {
                annotations.addAll(findRecursivePoijiAnnotations(field.getType(), annotationType));
            } else {
                A fieldAnnotation = field.getAnnotation(annotationType);
                if (fieldAnnotation != null) {
                    annotations.add(fieldAnnotation);
                }
            }
        }

        return annotations;
    }

    public static void setFieldData(Field field, Object o, Object instance) {
        try {
            field.setAccessible(true);
            field.set(instance, o);
        } catch (IllegalAccessException e) {
            throw new IllegalCastException("Unexpected cast type {" + o + "} of field" + field.getName());
        }
    }

    @SuppressWarnings("unchecked")
    public static void putFieldMultiValueMapData(Field field, String columnName, Object o, Object instance) {
        try {
            field.setAccessible(true);
            MultiValuedMap<String, Object> multiValuedMap = (MultiValuedMap<String, Object>) field.get(instance);
            multiValuedMap.put(columnName, o);
        } catch (IllegalAccessException | IllegalAccessError e) {
            throw new IllegalCastException("Unexpected cast type {" + o + "} of field" + field.getName());
        }
    }
}
