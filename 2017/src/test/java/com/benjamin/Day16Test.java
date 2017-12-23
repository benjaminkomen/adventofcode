package com.benjamin;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day16Test {

    private Day16 target;
    private static final String SEED = "abcde";
    private static final String INPUT = "s1,x3/4,pe/b";

    @Before
    public void setup() {
        target = new Day16();
    }

    @Test
    public void testDeelEen1() {
        String result = target.deelEenA(SEED, INPUT);

        assertThat(result, is("baedc"));
    }

    @Test
    public void testApplySpin1() {
        String result = target.applySpin(SEED, new Day16.DanceMove(Day16.DanceMoveType.Spin, 1));

        assertThat(result, is("eabcd"));
    }

    @Test
    public void testApplySpin2() {
        String result = target.applySpin(SEED, new Day16.DanceMove(Day16.DanceMoveType.Spin, 2));

        assertThat(result, is("deabc"));
    }

    @Test
    public void testApplySpin3() {
        String result = target.applySpin(SEED, new Day16.DanceMove(Day16.DanceMoveType.Spin, 3));

        assertThat(result, is("cdeab"));
    }

    @Test
    public void testApplySpin4() {
        String result = target.applySpin(SEED, new Day16.DanceMove(Day16.DanceMoveType.Spin, 4));

        assertThat(result, is("bcdea"));
    }

    @Test
    public void testApplyExchange() {
        String result = target.applyExchange(SEED, new Day16.DanceMove(Day16.DanceMoveType.Exchange, 0,3));

        assertThat(result, is("dbcae"));
    }

    @Test
    public void testApplyPartner() {
        String result = target.applyPartner(SEED, new Day16.DanceMove(Day16.DanceMoveType.Partner, "b","e"));

        assertThat(result, is("aecdb"));
    }

}