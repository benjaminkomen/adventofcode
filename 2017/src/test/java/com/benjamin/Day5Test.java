package com.benjamin;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day5Test {

    private static final String TEST_INPUT =
            "0\n" +
            "3\n" +
            "0\n" +
            "1\n" +
            "-3";

    private Day5 target;

    @Before
    public void setup() {
        target = new Day5();
    }

    @Test
    public void testDeelEen1() {
        long result = target.deelEenA(TEST_INPUT);

        assertThat(result, is(5L));
    }

    @Test
    public void testDeelEen2() {
        long result = target.deelTweeA(TEST_INPUT);

        assertThat(result, is(10L));
    }
}