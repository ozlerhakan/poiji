package com.poiji.annotation;

import java.lang.annotation.*;

/**
 * Created by ar on 8/03/2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExcelRow {
}
