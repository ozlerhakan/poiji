package com.poiji.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates, that a field contains an excel property (e.g. author, title, custom properties, ...)
 *
 * @author breucode
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExcelProperty {
    /**
     * If this value is set, the property will be read from property field with this name instead of your field name.
     *
     * @return the name of the property field to read from
     */
    String propertyName() default "";
}
