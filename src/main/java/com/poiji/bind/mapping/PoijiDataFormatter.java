package com.poiji.bind.mapping;

import com.poiji.option.PoijiOptions;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.NumberToTextConverter;

/**
 * Created by hakan on 16.05.2021
 */
public class PoijiDataFormatter extends DataFormatter {

    PoijiOptions options;

    public PoijiDataFormatter(PoijiOptions options) {
        super();
        this.options = options;
    }

    @Override
    public String formatRawCellContents(double value, int formatIndex, String formatString, boolean use1904Windowing) {
        String cellContents = super.formatRawCellContents(value, formatIndex, formatString, use1904Windowing);

        if (options.isReturnRawValues()) {
            return NumberToTextConverter.toText(value);
        } else {
            return cellContents;
        }
    }
}
