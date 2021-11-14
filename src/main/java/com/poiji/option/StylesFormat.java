package com.poiji.option;

import com.poiji.bind.mapping.PoijiNumberFormat;

class StylesFormat {

    private StylesFormat() {
    }

    public static PoijiNumberFormat loadDefaultStyles() {
        PoijiNumberFormat numberFormat = new PoijiNumberFormat();
        numberFormat.putNumberFormat((short) 47, "mm/dd/yyyy hh.mm aa");
        numberFormat.putNumberFormat((short) 14, "dd/mm/yyyy");
        return numberFormat;
    }
}
