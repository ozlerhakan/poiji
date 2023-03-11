package com.poiji.util;

import com.poiji.annotation.ExcelCellRange;
import com.poiji.exception.IllegalCastException;
import com.poiji.exception.PoijiInstantiationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
}
