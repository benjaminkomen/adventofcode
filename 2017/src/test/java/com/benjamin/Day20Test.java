package com.benjamin;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day20Test {

    private Day20 target;
    private static final int INPUT = 65;

    @Before
    public void setup() {
        target = new Day20();
    }

    @Test
    public void testDeelEen1() {
        String result = target.deelEenA(INPUT);

        assertThat(result, is(588));
    }

    @Ignore
    @Test
    public void testDeelTwee1() {
        String result = target.deelTweeA(INPUT);

        assertThat(result, is(309));
    }

}