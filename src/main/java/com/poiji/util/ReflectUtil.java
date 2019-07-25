package com.poiji.util;

import java.lang.reflect.Constructor;

import com.poiji.exception.PoijiInstantiationException;

public class ReflectUtil {
    public static <T> T newInstanceOf(Class<T> type) {
        T obj;
        try {
            Constructor<T> constructor = type.getDeclaredConstructor();
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            obj = constructor.newInstance();
        } catch (Exception ex) {
            throw new PoijiInstantiationException("Cannot create a new instance of " + type.getName(), ex);
        }

        return obj;
    }
}
