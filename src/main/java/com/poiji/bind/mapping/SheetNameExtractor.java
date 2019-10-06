package com.poiji.bind.mapping;

import com.poiji.annotation.ExcelSheet;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;

import java.util.Optional;

/**
 * Utility class to extract the sheet name.
 */
class SheetNameExtractor {
    /**
     * Extracts the sheet name from either the annotated value {@link ExcelSheet} from the model class or from the sheet name set
     * in the Poiji Options. Returns Optional.empty when sheet name is not configured at either place.
     * @param type The class instance of the object model.
     * @param options The Poiji options.
     * @param <T> The type of the object model.
     * @return an Optional sheet name
     * @throws PoijiException when the configured sheet name in the PoijiOptions and the annotated sheet name do not match.
     */
    public static <T> Optional<String> getSheetName(Class<T> type, PoijiOptions options) {
        String annotatedSheetName = null;
        String configuredSheetName = options.getSheetName();

        if (type.isAnnotationPresent(ExcelSheet.class)) {
            ExcelSheet excelSheet = type.getAnnotation(ExcelSheet.class);
            annotatedSheetName = excelSheet.value();
        }
        if (annotatedSheetName != null && configuredSheetName != null
                && !annotatedSheetName.equals(options.getSheetName())) {
            throw new PoijiException(String.format("The configured sheet name in PoijiOptions (%s) and the annotated sheet name "
                    + "(%s) do not match", configuredSheetName, annotatedSheetName));
        }

        if (annotatedSheetName != null) {
            return Optional.of(annotatedSheetName);
        }
        return Optional.ofNullable(configuredSheetName);
    }
}
