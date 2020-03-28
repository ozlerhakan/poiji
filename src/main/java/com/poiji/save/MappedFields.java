package com.poiji.save;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelReadOnly;
import com.poiji.annotation.ExcelUnknownCells;
import com.poiji.bind.mapping.SheetNameExtractor;
import com.poiji.exception.PoijiException;
import com.poiji.option.PoijiOptions;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.poiji.annotation.ExcelCellName.ABSENT_ORDER;

public final class MappedFields {

    private final Class<?> entity;
    private String sheetName;
    private final Map<Field, Integer> orders;
    private final Map<Field, String> names;
    private final List<Field> unknownCells;
    private final Map<String, Integer> unknownOrders;
    private final PoijiOptions options;

    public MappedFields(final Class<?> entity, final PoijiOptions options) {
        this.entity = entity;
        orders = new HashMap<>();
        names = new HashMap<>();
        unknownCells = new ArrayList<>();
        this.unknownOrders = new LinkedHashMap<>();
        this.options = options;
    }

    public MappedFields parseEntity() {
        SheetNameExtractor.getSheetName(entity, options).ifPresent(sheetName -> this.sheetName = sheetName);
        final Field[] declaredFields = entity.getDeclaredFields();
        final List<Field> unordered = new ArrayList<>();
        for (final Field field : declaredFields) {
            if (field.getAnnotation(ExcelReadOnly.class) == null){
                if (field.getAnnotation(ExcelCell.class) != null) {
                    final Integer excelOrder = field.getAnnotation(ExcelCell.class).value();
                    final String name = field.getName();
                    orders.put(field, excelOrder);
                    names.put(field, name);
                    field.setAccessible(true);
                } else if (field.getAnnotation(ExcelUnknownCells.class) != null) {
                    unknownCells.add(field);
                    field.setAccessible(true);
                } else {
                    final ExcelCellName annotation = field.getAnnotation(ExcelCellName.class);
                    if (annotation != null) {
                        final String delimeter = annotation.columnNameDelimeter();
                        final String excelName = delimeter.isEmpty()
                            ? annotation.value()
                            : annotation.value().substring(0, annotation.value().indexOf(delimeter));
                        final int order = annotation.order();
                        if (order == ABSENT_ORDER) {
                            unordered.add(field);
                        } else {
                            orders.put(field, order);
                        }
                        names.put(field, excelName);
                        field.setAccessible(true);
                    }
                }
            }
        }
        orders.putAll(new OrderedValues(orders.values()).toOrder(unordered));
        return this;
    }

    public <T> void addUnknownColumnNamesFromData(final Collection<T> data) {
        unknownOrders.putAll(extractUnknownColumnNamesFromData(data));
    }

    private <T> Map<String, Integer> extractUnknownColumnNamesFromData(final Collection<T> data) {
        final Collection<java.lang.String> unknownNames = new HashSet<>();
        for (final Field unknownCell : unknownCells) {
            for (T instance : data) {
                try {
                    final Map<String, String> unknownCells = (Map<String, String>) unknownCell.get(instance);
                    if (unknownCells != null) {
                        unknownNames.addAll(unknownCells.keySet());
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new PoijiException(e.getMessage(), e);
                }

            }
        }
        return new OrderedValues(orders.values()).toOrder(unknownNames);
    }

    public String getSheetName() {
        return sheetName;
    }

    public Map<Field, Integer> getOrders() {
        return orders;
    }

    public Map<Field, String> getNames() {
        return names;
    }

    public List<Field> getUnknownCells() {
        return unknownCells;
    }

    public Map<String, Integer> getUnknownOrders() {
        return unknownOrders;
    }
}
