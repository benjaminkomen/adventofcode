package com.benjamin;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day6Test {

    private static final String INPUT = "0\t2\t7\t0";
    private Day6 target;

    @Before
    public void setup() {
        target = new Day6();
    }

    @Test
    public void testDeelEen() {
        int result = target.deelEenA(INPUT);

        assertThat(result, is(5));
    }

    @Test
    public void testDeelTwee() {
        int result = target.deelTweeA(INPUT);

        assertThat(result, is(4));
    }

}