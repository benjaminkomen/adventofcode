package com.benjamin;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class Day3Test {

    private Day3 target;
    private static final String INPUT1 =
            "#1 @ 1,3: 4x4\n" +
            "#2 @ 3,1: 4x4\n" +
            "#3 @ 5,5: 2x2\n";

    @BeforeEach
    public void setup() {
        target = new Day3();
    }

    @Test
    public void testDeelEen1() {
        int result = target.deelEenA(INPUT1);

        assertThat(result, Matchers.is(4));
    }

    @Test
    public void testDeelTwee1() {
        int result = target.deelTweeA(INPUT1);

        assertThat(result, Matchers.is(-1));
    }
}
