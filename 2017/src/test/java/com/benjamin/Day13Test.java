package com.benjamin;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day13Test {

    private Day13 target;
    private static final String INPUT =
                    "0: 3\n" +
                    "1: 2\n" +
                    "4: 4\n" +
                    "6: 4";

    @Before
    public void setup() {
        target = new Day13();
    }

    @Test
    public void testDeelEen1() {
        int result = target.deelEenA(INPUT);

        assertThat(result, is(24));
    }

    @Test
    public void testDeelTwee1() {
        target.deelEenA(INPUT);
        int result = target.deelTweeA(INPUT);

        assertThat(result, is(10));
    }

}