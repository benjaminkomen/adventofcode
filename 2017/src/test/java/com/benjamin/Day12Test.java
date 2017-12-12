package com.benjamin;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day12Test {

    private Day12 target;
    private static final String INPUT =
            "0 <-> 2\n" +
            "1 <-> 1\n" +
            "2 <-> 0, 3, 4\n" +
            "3 <-> 2, 4\n" +
            "4 <-> 2, 3, 6\n" +
            "5 <-> 6\n" +
            "6 <-> 4, 5";

    @Before
    public void setup() {
        target = new Day12();
    }

    @Test
    public void testDeelEen1() {
        int result = target.deelEenA(INPUT);

        assertThat(result, is(6));
    }

    @Test
    public void testDeelTwee1() {
        target.deelEenA(INPUT);
        int result = target.deelTweeA(INPUT);

        assertThat(result, is(2));
    }
}