package com.benjamin;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class Day6Test {

    private Day6 target;
    private static final String INPUT1 = "";

    @BeforeEach
    public void setup() {
        target = new Day6();
    }

    @Test
    public void testDeelEen1() {
        int result = target.deelEenA(INPUT1);

        assertThat(result, Matchers.is(-1));
    }

    @Test
    public void testDeelTwee1() {
        int result = target.deelTweeA(INPUT1);

        assertThat(result, Matchers.is(-1));
    }
}
