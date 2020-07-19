package com.poiji.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a field/column ignores its custom cell format in a xls excel file.
 *
 * Created by hakan on 18/07/2020.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface DisableCellFormatXLS {

    /**
     * true disable cell format of a cell, otherwise false
     *
     * @return disable cell format or not
     */
    boolean value() default true;
}