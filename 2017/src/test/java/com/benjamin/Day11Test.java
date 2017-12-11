package com.benjamin;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day11Test {

    private Day11 target;

    @Before
    public void setup() {
        target = new Day11();
    }

    @Test
    public void testDeelEen1() {
        int result = target.deelEenA("ne,ne,ne");

        assertThat(result, is(3));
    }

    @Test
    public void testDeelEen2() {
        int result = target.deelEenA("ne,ne,sw,sw");

        assertThat(result, is(0));
    }

    @Test
    public void testDeelEen3() {
        int result = target.deelEenA("ne,ne,s,s");

        assertThat(result, is(2));
    }

    @Test
    public void testDeelEen4() {
        int result = target.deelEenA("se,sw,se,sw,sw");

        assertThat(result, is(3));
    }
}