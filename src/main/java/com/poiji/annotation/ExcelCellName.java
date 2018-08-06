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

    /**
     * Specifies the column name where the corresponding value is mapped from the excel data
     *
     * @return column name
     */
    String value();
}
