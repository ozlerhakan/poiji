package com.poiji.save;

import java.util.function.BiConsumer;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test for {@link CellCasting}.
 */
public final class CellCastingTest {

    @Test
    public void getObjectCellCastingInsteadAbsent() {
        final CellCasting cellCasting = new CellCasting();

        assertThat(cellCasting.forType(CellCasting.class), is(cellCasting.forType(Object.class)));
    }

    @Test
    public void addCellCasting() {
        final CellCasting cellCasting = new CellCasting();
        final BiConsumer<Cell, Object> rule = (cell, o) -> {};
        cellCasting.addCellCasting(CellCasting.class, rule);

        assertThat(cellCasting.forType(CellCasting.class), is(rule));
    }

    @Test
    public void rewriteCellCasting() {
        final CellCasting cellCasting = new CellCasting();
        final BiConsumer<Cell, Object> rule = (cell, o) -> {};

        assertThat(cellCasting.forType(Boolean.class), not(is(rule)));

        cellCasting.addCellCasting(Boolean.class, rule);

        assertThat(cellCasting.forType(Boolean.class), is(rule));
    }
}
