package com.poiji.save;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class OrderedValues {

    private final Collection<Integer> busyOrders;

    public OrderedValues(final Collection<Integer> busyOrders) {
        this.busyOrders = busyOrders;
    }

    public <T> Map<T, Integer> toOrder(final Collection<T> unorderedValues) {
        final Map<T, Integer> ordered = new HashMap<>();
        int order = 0;
        for (final T name : unorderedValues) {
            while (busyOrders.contains(order)) {
                order++;
            }
            ordered.put(name, order++);
        }
        return ordered;
    }
}
