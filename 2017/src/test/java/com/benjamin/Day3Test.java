package com.benjamin;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day3Test {

    private Day3 target;

    @Before
    public void setup() {
        target = new Day3();
    }

    @Test
    public void testDeelEen1() {
        int result = target.deelEenA(0);

        assertThat(result, is(0));
    }

    @Test
    public void testDeelEen2() {
        int result = target.deelEenA(12);

        assertThat(result, is(3));
    }

    @Test
    public void testDeelEen3() {
        int result = target.deelEenA(23);

        assertThat(result, is(2));
    }

    @Test
    public void testDeelEen4() {
        int result = target.deelEenA(1024);

        assertThat(result, is(31));
    }
}