package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCellsJoinedByName;
import org.apache.commons.collections4.MultiValuedMap;

/**
 * A record class to test @ExcelCellsJoinedByName support
 */
public record AlbumRecord(
        @ExcelCellsJoinedByName(expression = "Artist")
        MultiValuedMap<String, String> artists,

        @ExcelCellsJoinedByName(expression = "Track[0-9]+")
        MultiValuedMap<String, String> tracks
) {
}
