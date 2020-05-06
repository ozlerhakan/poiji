package com.poiji.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotations allows you to put every unknown cell (neither mapped by name, nor by index) into a {@code Map<String,String>}
 *
 * @author breucode
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExcelUnknownCells {
}
