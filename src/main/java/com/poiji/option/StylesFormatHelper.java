package com.poiji.option;

import com.poiji.bind.mapping.PoijiNumberFormat;

class StylesFormatHelper {

    private StylesFormatHelper() {
    }

    static PoijiNumberFormat loadDefaultStyles() {
        PoijiNumberFormat numberFormat = new PoijiNumberFormat();
        numberFormat.putNumberFormat((short) 47, "mm/dd/yyyy hh.mm aa");
        numberFormat.putNumberFormat((short) 14, "dd/mm/yyyy");
        return numberFormat;
    }
}
