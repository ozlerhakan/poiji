package com.poiji.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hakan on 16/03/2018
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExcelCellName {

    int ABSENT_ORDER = -1;

    /**
     * Specifies the column name where the corresponding value is mapped from the excel data
     *
     * @return column name
     */
    String value();

    /**
     * Specifies the column order in saved file
     *
     * @return column order
     */
    int order() default ABSENT_ORDER;
}
