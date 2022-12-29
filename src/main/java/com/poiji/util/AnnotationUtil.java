package com.poiji.util;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.config.Formatting;
import com.poiji.exception.HeaderMissingException;
import com.poiji.option.PoijiOptions;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * Created by hakan on 2.05.2020
 */
public final class AnnotationUtil {

        private AnnotationUtil() {
        }

        /**
         * Validate that all headers specified via @ExcelCellName annotations are
         * present in the list of header names.
         * <p>
         * Validation is only performed if it is set in the PoijiOptions
         * 
         * @param options      poijoption
         * @param formatting   formatting
         * @param modelType    class model
         * @param titleToIndex tiletoindex
         * @param indexToTitle indextoTitle
         * @param <T>          model Type
         * @throws HeaderMissingException if one or more headers are missing
         */
        public static <T> void validateMandatoryNameColumns(PoijiOptions options,
                        Formatting formatting,
                        Class<T> modelType,
                        Map<String, Integer> titleToIndex,
                        Map<Integer, String> indexToTitle) {
                Collection<ExcelCellName> excelCellNames = ReflectUtil.findRecursivePoijiAnnotations(modelType,
                                ExcelCellName.class);
                Collection<ExcelCell> excelCells = ReflectUtil.findRecursivePoijiAnnotations(modelType,
                                ExcelCell.class);

                BiPredicate<String, String> comparator = String::equals;

                Set<Integer> missingExcelCellHeaders = excelCells.stream()
                                .filter(excelCell -> indexToTitle.get(excelCell.value()) == null)
                                .filter(excelCell -> options.getHeaderCount() != 0)
                                .filter(ExcelCell::mandatoryHeader)
                                .map(ExcelCell::value)
                                .collect(Collectors.toSet());

                Set<String> missingExcelCellNameHeaders = excelCellNames.stream()
                                .filter(excelCell -> options.getHeaderCount() != 0)
                                .filter(excelCellName -> titleToIndex.keySet().stream()
                                                .noneMatch(title -> comparator.test(
                                                                formatting.transform(options, excelCellName.value()),
                                                                title)))
                                .filter(ExcelCellName::mandatoryHeader)
                                .map(ExcelCellName::value)
                                .collect(Collectors.toSet());

                long totalMissingColumns = missingExcelCellNameHeaders.size() + missingExcelCellHeaders.size();
                if (totalMissingColumns != 0) {
                        String message = "Some headers are missing in the sheet: ";
                        if (!missingExcelCellNameHeaders.isEmpty()) {
                                message += missingExcelCellNameHeaders;
                        }
                        if (!missingExcelCellHeaders.isEmpty()) {
                                StringBuilder missingMessage = new StringBuilder();
                                missingExcelCellHeaders.stream()
                                                .map(i -> String.join(" ", " missing index column on",
                                                                String.valueOf(i)))
                                                .forEach(missingMessage::append);
                                message += missingMessage;
                        }
                        throw new HeaderMissingException(message);
                }
        }
}
