package com.benjamin;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class Day1Test {

    private Day1 target;

    @BeforeEach
    public void setup() {
        target = new Day1();
    }

    @Test
    public void testDeelEen1() {
        int result = target.deelEenA("");

        assertThat(result, Matchers.is(3));
    }

    @Test
    public void testDeelTwee1() {
        int result = target.deelTweeA("");

        assertThat(result, Matchers.is(6));
    }
}
