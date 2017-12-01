package com.benjamin;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class Day1Test {

    private static final String TEST_INPUT_1 = "1122";

    private Day1 target;

    @Before
    public void setup() {
        target = new Day1();
    }

    @Test
    public void testBereken1() {
        int result1 = target.bereken(TEST_INPUT_1);

        assertThat(result1, Matchers.is(3));

    }
}
