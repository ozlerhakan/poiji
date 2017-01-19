package com.poiji.internal.marshaller;

import com.poiji.exception.IllegalCastException;
import com.poiji.exception.PoijiInstantiationException;
import com.poiji.internal.PoiWorkbook;
import com.poiji.internal.PoijiOptions;
import com.poiji.internal.annotation.Index;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This is the main class that converts the excel sheet fromExcel Java object
 * <br>
 * Created by hakan on 16/01/2017.
 */
public final class Unmarshaller implements Deserializer {

    private final PoiWorkbook poiWorkbook;
    private final DataFormatter df = new DataFormatter();

    private Unmarshaller(final PoiWorkbook poiWorkbook) {
        this.poiWorkbook = poiWorkbook;
    }

    public <T> List<T> deserialize(Class<T> type, PoijiOptions options) {
        final Sheet datatypeSheet = poiWorkbook.workbook().getSheetAt(0);

        final List<T> list = new ArrayList<>(datatypeSheet.getLastRowNum() + 1 - options.skip());
        Supplier<Stream<Field>> fields = () -> Stream.of(type.getDeclaredFields());

        for (Row currentRow : datatypeSheet) {

            if (skip(currentRow, options.skip()))
                continue;

            T t = deserialize0(currentRow, type, fields);
            list.add(t);
        }

        return list;
    }

    private <T> T deserialize0(Row currentRow, Class<T> type, Supplier<Stream<Field>> fields) {

        T instance;
        try {
            instance = type.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new PoijiInstantiationException("Cannot create a new instance of " + type.getName());
        }

        fields.get().forEach(field -> {

            Annotation[] annotations = field.getDeclaredAnnotations();
            if (annotations.length != 0) {
                Index index = (Index) annotations[0];
                Cell cell = currentRow.getCell(index.cell());

                if (index.column() == cell.getColumnIndex()) {

                    if (!field.isAccessible())
                        field.setAccessible(true);
                    Class<?> fieldType = field.getType();

                    String value = df.formatCellValue(cell);
                    Object o = castValue(fieldType, value);

                    try {
                        field.set(instance, o);
                    } catch (IllegalAccessException e) {
                        throw new IllegalCastException("Unexpected cast type {" + value + "} of field" + field.getName());
                    }
                }
            }

        });

        return instance;
    }

    private Object castValue(Class<?> fieldType, String value) {
        try {
            Object o;
            if (fieldType.getName().equals("int")) {
                o = new Integer(Objects.equals(value, "") ? "0" : value);

            } else if (fieldType.getName().equals("long")) {
                o = new Long(Objects.equals(value, "") ? "0" : value);

            } else if (fieldType.getName().equals("double")) {
                o = new Double(Objects.equals(value, "") ? "0" : value);

            } else if (fieldType.getName().equals("float")) {
                o = new Float(Objects.equals(value, "") ? "0" : value);

            } else if (fieldType.getName().equals("boolean")) {
                o = Boolean.valueOf(value);

            } else if (fieldType.getName().equals("byte")) {
                o = new Byte(Objects.equals(value, "") ? "0" : value);

            } else if (fieldType.getName().equals("short")) {
                o = new Short(Objects.equals(value, "") ? "0" : value);

            } else if (fieldType.getName().equals("char")) {
                value = Objects.equals(value, "") ? " " : value;
                o = value.charAt(0);

            } else
                o = value;
            return o;
        } catch (Exception e) {
            throw new IllegalCastException("Unexpected cast value {" + value + "} of type " + fieldType.getName());
        }
    }

    private boolean skip(final Row currentRow, int skip) {
        return currentRow.getRowNum() + 1 <= skip;
    }

    public static Deserializer instance(PoiWorkbook workbook) {
        return new Unmarshaller(workbook);
    }
}
