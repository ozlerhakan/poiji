package com.poiji.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a field declaration contains excel metadata.
 *
 * Created by hakan on 17/01/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExcelCell {

    /**
     * Specifies the column index where the corresponding value is mapped from the
     * excel data
     *
     * @return column index
     */
    int value();

    /**
     * Specifies whether the header is required or not.
     *
     * @return mandatory header signal. Default is false.
     */
    boolean mandatoryHeader() default false;

    /**
     * Specifies whether the cell value of the header is required in each row.
     *
     * @return mandatory cell signal. Default is false.
     */
    boolean mandatoryCell() default false;
}
