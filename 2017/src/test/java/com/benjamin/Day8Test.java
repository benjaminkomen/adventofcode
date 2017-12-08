package com.benjamin;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day8Test {

    private static final String TEST_INPUT =
            "b inc 5 if a > 1\n" +
            "a inc 1 if b < 5\n" +
            "c dec -10 if a >= 1\n" +
            "c inc -20 if c == 10";

    private Day8 target;

    @Before
    public void setup() {
        target = new Day8();
    }

    @Test
    public void testDeelEen1() throws Exception {
        int result = target.deelEenA(TEST_INPUT);

        assertThat(result, is(1));
    }

    @Test
    public void testDeelEen2() throws Exception {
        int result = target.deelTweeA(TEST_INPUT);

        assertThat(result, is(10));
    }

}