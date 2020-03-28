package com.poiji.save;

import java.util.function.BiConsumer;
import org.apache.poi.ss.usermodel.Cell;

public interface ToCellCastingRule<T> extends BiConsumer<Cell, T> {
}
