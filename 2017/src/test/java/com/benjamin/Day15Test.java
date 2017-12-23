package com.benjamin;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day15Test {

    private Day15 target;
    private static final int INPUT_GENERATOR_A = 65;
    private static final int INPUT_GENERATOR_B = 8921;

    @Before
    public void setup() {
        target = new Day15();
    }

    @Test
    public void testDeelEen1() {
        int result = target.deelEenA(INPUT_GENERATOR_A, INPUT_GENERATOR_B);

        assertThat(result, is(588));
    }

    @Test
    public void testDeelTwee1() {
        int result = target.deelTweeA(INPUT_GENERATOR_A, INPUT_GENERATOR_B);

        assertThat(result, is(309));
    }

}