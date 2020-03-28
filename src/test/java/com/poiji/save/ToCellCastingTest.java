package com.poiji.save;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test for {@link ToCellCasting}.
 */
public final class ToCellCastingTest {

    @Test
    public void getObjectCellCastingInsteadAbsent() {
        final ToCellCasting toCellCasting = new ToCellCasting();

        assertThat(toCellCasting.forType(ToCellCasting.class), is(toCellCasting.forType(Object.class)));
    }

    @Test
    public void addCellCastingRule() {
        final ToCellCasting toCellCasting = new ToCellCasting();
        final ToCellCastingRule<ToCellCasting> rule = (cell, o) -> {};
        toCellCasting.putToCellCastingRule(ToCellCasting.class, rule);

        assertThat(toCellCasting.forType(ToCellCasting.class), is(rule));
    }

    @Test
    public void rewriteCellCastingRule() {
        final ToCellCasting toCellCasting = new ToCellCasting();
        final ToCellCastingRule<Boolean> rule = (cell, o) -> {};

        assertThat(toCellCasting.forType(Boolean.class), not(is(rule)));

        toCellCasting.putToCellCastingRule(Boolean.class, rule);

        assertThat(toCellCasting.forType(Boolean.class), is(rule));
    }
}
