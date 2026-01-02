package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelCellsJoinedByName;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

/**
 * Model to test processEmptyCell feature with both ExcelCell and ExcelCellsJoinedByName.
 * This model has fields that may have empty cells to verify proper handling.
 */
public record PersonWithGapsRecord (

    @ExcelCell(0)
    String id,

    @ExcelCell(1)
    String firstName,

    @ExcelCell(2)
    String middleName,

    @ExcelCell(3)
    String lastName,

    @ExcelCellName("Email")
    String email,

    @ExcelCellsJoinedByName(expression = "Phone.*")
    MultiValuedMap<String, String> phones
){}
