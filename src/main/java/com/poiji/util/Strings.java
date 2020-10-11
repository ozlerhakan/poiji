package com.poiji.util;

import com.poiji.option.PoijiOptions;

public final class Strings {

    private Strings() {
    }

    public static String getTitleName(PoijiOptions options, String value) {
        String titleName = options.getCaseInsensitive()
            ? value.toLowerCase()
            : value;
        if (options.getIgnoreWhitespaces())
            return titleName.trim();
        return titleName;
    }

}
