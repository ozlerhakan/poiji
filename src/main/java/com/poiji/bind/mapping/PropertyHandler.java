package com.poiji.bind.mapping;

import com.poiji.annotation.ExcelProperty;
import com.poiji.util.ReflectUtil;
import org.apache.poi.ooxml.POIXMLProperties;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static com.poiji.util.DefaultExcelProperties.CATEGORY;
import static com.poiji.util.DefaultExcelProperties.CONTENT_STATUS;
import static com.poiji.util.DefaultExcelProperties.CREATED;
import static com.poiji.util.DefaultExcelProperties.CREATOR;
import static com.poiji.util.DefaultExcelProperties.DESCRIPTION;
import static com.poiji.util.DefaultExcelProperties.KEYWORDS;
import static com.poiji.util.DefaultExcelProperties.LAST_PRINTED;
import static com.poiji.util.DefaultExcelProperties.MODIFIED;
import static com.poiji.util.DefaultExcelProperties.REVISION;
import static com.poiji.util.DefaultExcelProperties.SUBJECT;
import static com.poiji.util.DefaultExcelProperties.TITLE;

public final class PropertyHandler {

    /**
     * Creates an instance of {@code type} and deserializes the {@code poixmlProperties} into the fields annotated with {@link ExcelProperty}
     * @param type              The type to deserialize into
     * @param poixmlProperties  The properties to read from
     * @param <T>               The type to deserialize into
     * @return                  An instance of {@code type}
     */
    <T> T unmarshal(Class<T> type, POIXMLProperties poixmlProperties) {

        T unmarshalledObject = ReflectUtil.newInstanceOf(type);

        Stream.of(type.getDeclaredFields())
                .filter(field -> field.getAnnotation(ExcelProperty.class) != null)
                .forEach(excelPropertyField -> {
                    String propertyName = getPropertyName(excelPropertyField);

                    setPropertyValueOnTarget(propertyName, poixmlProperties, excelPropertyField, unmarshalledObject);
                });

        return unmarshalledObject;
    }

    private String getPropertyName(Field excelPropertyField) {
        String propertyName = excelPropertyField.getAnnotation(ExcelProperty.class).propertyName();

        if (propertyName.isEmpty()) {
            propertyName = excelPropertyField.getName();
        }

        return propertyName;
    }

    private void setPropertyValueOnTarget(String propertyName, POIXMLProperties poixmlProperties, Field targetField, Object targetObject) {
        switch (propertyName) {
            case CATEGORY:
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getCategory(), targetObject);
                break;
            case CONTENT_STATUS:
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getContentStatus(), targetObject);
                break;
            case CREATED:
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getCreated(), targetObject);
                break;
            case CREATOR:
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getCreator(), targetObject);
                break;
            case DESCRIPTION:
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getDescription(), targetObject);
                break;
            case KEYWORDS:
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getKeywords(), targetObject);
                break;
            case LAST_PRINTED:
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getLastPrinted(), targetObject);
                break;
            case MODIFIED:
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getModified(), targetObject);
                break;
            case SUBJECT:
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getSubject(), targetObject);
                break;
            case TITLE:
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getTitle(), targetObject);
                break;
            case REVISION:
                ReflectUtil.setFieldData(targetField, poixmlProperties.getCoreProperties().getRevision(), targetObject);
                break;
            default:
                if (poixmlProperties.getCustomProperties().getProperty(propertyName) != null) {
                    ReflectUtil.setFieldData(targetField, poixmlProperties.getCustomProperties().getProperty(propertyName).getLpwstr(), targetObject);
                }
                break;
        }
    }
}
