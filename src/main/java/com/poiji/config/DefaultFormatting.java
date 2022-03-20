package com.poiji.config;

import com.poiji.option.PoijiOptions;

public class DefaultFormatting implements Formatting {

    @Override
    public String transform(PoijiOptions options, String value) {
        String valueNorm = value;
        if (options.getCaseInsensitive()) {
            valueNorm = valueNorm.toLowerCase();
        }
        if (options.getIgnoreWhitespaces()) {
            valueNorm = valueNorm.trim();
        }
        return valueNorm;
    }

}
