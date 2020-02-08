package com.poiji.bind.mapping;

import com.poiji.annotation.ExcelSheet;
import com.poiji.option.PoijiOptions;
import java.util.Optional;

/**
 * Utility class to extract the sheet name.
 */
public class SheetNameExtractor {
    /**
     * Extracts the sheet name from either the annotated value {@link ExcelSheet} from the model class or from the sheet name set
     * in the Poiji Options. Poiji first looks at {@link ExcelSheet} then {@link PoijiOptions}.
     *
     * @param type    The class instance of the object model.
     * @param options The Poiji options.
     * @param <T>     The type of the object model.
     * @return an Optional sheet name
     */
    public static <T> Optional<String> getSheetName(Class<T> type, PoijiOptions options) {
        if (type.isAnnotationPresent(ExcelSheet.class)) {
            ExcelSheet excelSheet = type.getAnnotation(ExcelSheet.class);
            String annotatedSheetName = excelSheet.value();
            return Optional.ofNullable(annotatedSheetName);
        }

        String configuredSheetName = options.getSheetName();
        return Optional.ofNullable(configuredSheetName);
    }
}
