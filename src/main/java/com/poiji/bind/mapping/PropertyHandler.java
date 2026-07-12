package com.poiji.bind.mapping;

import com.poiji.annotation.ExcelProperty;
import com.poiji.util.ReflectUtil;
import org.apache.poi.ooxml.POIXMLProperties;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.poiji.util.DefaultExcelPropertiesHelper.CATEGORY;
import static com.poiji.util.DefaultExcelPropertiesHelper.CONTENT_STATUS;
import static com.poiji.util.DefaultExcelPropertiesHelper.CREATED;
import static com.poiji.util.DefaultExcelPropertiesHelper.CREATOR;
import static com.poiji.util.DefaultExcelPropertiesHelper.DESCRIPTION;
import static com.poiji.util.DefaultExcelPropertiesHelper.KEYWORDS;
import static com.poiji.util.DefaultExcelPropertiesHelper.LAST_PRINTED;
import static com.poiji.util.DefaultExcelPropertiesHelper.MODIFIED;
import static com.poiji.util.DefaultExcelPropertiesHelper.REVISION;
import static com.poiji.util.DefaultExcelPropertiesHelper.SUBJECT;
import static com.poiji.util.DefaultExcelPropertiesHelper.TITLE;

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

    private static final Map<String, Function<POIXMLProperties.CoreProperties, Object>> CORE_PROPERTY_EXTRACTORS = new HashMap<>();

    static {
        CORE_PROPERTY_EXTRACTORS.put(CATEGORY, POIXMLProperties.CoreProperties::getCategory);
        CORE_PROPERTY_EXTRACTORS.put(CONTENT_STATUS, POIXMLProperties.CoreProperties::getContentStatus);
        CORE_PROPERTY_EXTRACTORS.put(CREATED, POIXMLProperties.CoreProperties::getCreated);
        CORE_PROPERTY_EXTRACTORS.put(CREATOR, POIXMLProperties.CoreProperties::getCreator);
        CORE_PROPERTY_EXTRACTORS.put(DESCRIPTION, POIXMLProperties.CoreProperties::getDescription);
        CORE_PROPERTY_EXTRACTORS.put(KEYWORDS, POIXMLProperties.CoreProperties::getKeywords);
        CORE_PROPERTY_EXTRACTORS.put(LAST_PRINTED, POIXMLProperties.CoreProperties::getLastPrinted);
        CORE_PROPERTY_EXTRACTORS.put(MODIFIED, POIXMLProperties.CoreProperties::getModified);
        CORE_PROPERTY_EXTRACTORS.put(SUBJECT, POIXMLProperties.CoreProperties::getSubject);
        CORE_PROPERTY_EXTRACTORS.put(TITLE, POIXMLProperties.CoreProperties::getTitle);
        CORE_PROPERTY_EXTRACTORS.put(REVISION, POIXMLProperties.CoreProperties::getVersion);
    }

    private void setPropertyValueOnTarget(String propertyName, POIXMLProperties poixmlProperties, Field targetField, Object targetObject){
        Function<POIXMLProperties.CoreProperties, Object> extractor = CORE_PROPERTY_EXTRACTORS.get(propertyName);

        if (extractor != null){
            Object value = extractor.apply(poixmlProperties.getCoreProperties());
            ReflectUtil.setFieldData(targetField, value, targetObject);
        }
        else if(poixmlProperties.getCustomProperties().getProperty(propertyName) != null){
            ReflectUtil.setFieldData(targetField, poixmlProperties.getCustomProperties().getProperty(propertyName).getLpwstr(), targetObject);
        }
    }
}
