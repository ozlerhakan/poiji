package com.poiji.deserialize.model.byname;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
import com.poiji.annotation.ExcelUnknownCells;

import java.util.Map;

/**
 * A record class to test @ExcelUnknownCells support
 */
public record OrgWithUnknownCellsRecord(
        @ExcelRow
        int rowIndex,

        @ExcelCellName(OrgWithUnknownCellsByName.HEADER_ORGANISATION_ID)
        String id,

        @ExcelCellName(OrgWithUnknownCellsByName.HEADER_ORGANISATION_EXTERNAL_ID)
        String externalId,

        @ExcelUnknownCells
        Map<String, String> unknownCells,

        @ExcelCellName(OrgWithUnknownCellsByName.HEADER_ORGANISATION_NAME)
        String name,

        @ExcelCellName(OrgWithUnknownCellsByName.HEADER_CUSTOMER_EXTERNAL_ID)
        String customerExternalId
) {
}
