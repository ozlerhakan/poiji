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

    /**
     * Delimeter for column multiname.
     * <p>
     * Example: @ExcelCellName(value = "id,identifier", columnNameDelimeter = ",")
     * reading: column with name 'id' will be mapped into field, or if no column 'id',
     * then column 'identifier' will be mapped into field.
     * writing: field will be saved into column 'id'
     *
     * @return delimeter for column multiname.
     */
    String columnNameDelimeter() default "";
}
