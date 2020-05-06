package com.poiji.bind.mapping;

import com.poiji.annotation.ExcelProperty;
import com.poiji.util.ReflectUtil;
import org.apache.poi.ooxml.POIXMLProperties;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public final class PropertyHandler {

    public static <T> T unmarshal(Class<T> type, POIXMLProperties poixmlProperties) {

        T unmarshalledObject = ReflectUtil.newInstanceOf(type);

        Stream.of(type.getDeclaredFields())
                .filter(field -> field.getAnnotation(ExcelProperty.class) != null)
                .forEach(excelPropertyField -> {
                    String propertyName = getPropertyName(excelPropertyField);

                    setPropertyValueOnTarget(propertyName, poixmlProperties, excelPropertyField, unmarshalledObject);
                });

        return unmarshalledObject;
    }

    private static String getPropertyName(Field excelPropertyField) {
        String propertyName = excelPropertyField.getAnnotation(ExcelProperty.class).propertyName();

        if (propertyName.isEmpty()) {
            propertyName = excelPropertyField.getName();
        }

        return propertyName;
    }

    private static void setPropertyValueOnTarget(String propertyName, POIXMLProperties poixmlProperties, Field targetField, Object targetObject) {
        switch (propertyName) {
            case "category":
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getCategory(), targetObject);
                break;
            case "contentStatus":
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getContentStatus(), targetObject);
                break;
            case "created":
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getCreated(), targetObject);
                break;
            case "creator":
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getCreator(), targetObject);
                break;
            case "description":
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getDescription(), targetObject);
                break;
            case "keywords":
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getKeywords(), targetObject);
                break;
            case "lastPrinted":
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getLastPrinted(), targetObject);
                break;
            case "modified":
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getModified(), targetObject);
                break;
            case "subject":
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getSubject(), targetObject);
                break;
            case "title":
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getTitle(), targetObject);
                break;
            case "revision":
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getRevision(), targetObject);
                break;
            default:
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCustomProperties().getProperty(propertyName).getLpwstr(), targetObject);
                break;
        }
    }
}
