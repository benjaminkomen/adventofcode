package com.benjamin;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day17Test {

    private Day17 target;
    private static final int INPUT = 3;

    @Before
    public void setup() {
        target = new Day17();
    }

    @Test
    public void testDeelEen1() {
        int result = target.deelEenA(INPUT);

        assertThat(result, is(638));
    }

    @Ignore
    @Test
    public void testDeelTwee1() {
        int result = target.deelTweeA(INPUT);

        assertThat(result, is(-1));
    }
}