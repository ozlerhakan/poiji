package com.poiji.bind.mapping;

import com.poiji.option.PoijiOptions;
import com.poiji.util.ReflectUtil;
import java.util.function.Consumer;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

/**
 * This class handles the processing of a .xlsx file,
 * and generates a list of instances of a given type
 * <p>
 * Created by hakan on 22/10/2017
 */
final class PoijiHandler<T> implements SheetContentsHandler {

    private T instance;
    private Consumer<? super T> consumer;
    private int internalRow;
    private int internalCount;
    private int limit;

    private Class<T> type;
    private PoijiOptions options;

    private final ReadMappedFields mappedFields;

    PoijiHandler(
        Class<T> type, PoijiOptions options, Consumer<? super T> consumer, final ReadMappedFields mappedFields
    ) {
        this.type = type;
        this.options = options;
        this.consumer = consumer;
        this.limit = options.getLimit();

        this.mappedFields = mappedFields;
    }

    @Override
    public void startRow(int rowNum) {
        if (rowNum + 1 > options.skip()) {
            internalCount += 1;
            instance = ReflectUtil.newInstanceOf(type);
        }
    }

    @Override
    public void endRow(int rowNum) {

        if (internalRow != rowNum)
			return;

        if (rowNum + 1 > options.skip()) {
            consumer.accept(instance);
        }
    }

    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        CellAddress cellAddress = new CellAddress(cellReference);
        int row = cellAddress.getRow();

        int headers = options.getHeaderStart();
        int column = cellAddress.getColumn();

        if (row <= headers) {
            mappedFields.parseColumnName(column, formattedValue);
        }

        if (row + 1 <= options.skip()) {
            return;
        }

        if (limit != 0 && internalCount > limit) {
            return;
        }

        internalRow = row;

        mappedFields.setCellInInstance(internalRow, column, formattedValue, instance);
    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
        //no-op
    }
}
