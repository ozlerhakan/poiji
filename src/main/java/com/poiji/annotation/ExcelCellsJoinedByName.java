package com.poiji.annotation;

import java.lang.annotation.*;

/**
 * Created by aerfus on 18/02/2024
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExcelCellsJoinedByName {

    /**
     * Specifies the column regular expression where the corresponding values are mapped from the
     * Excel data
     *
     * @return column regular expression
     */
    String expression();
}
