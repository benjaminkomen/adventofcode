package com.benjamin;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day18Test {

    private Day18 target;
    private static final String INPUT =
            "set a 1\n" +
            "add a 2\n" +
            "mul a a\n" +
            "mod a 5\n" +
            "snd a\n" +
            "set a 0\n" +
            "rcv a\n" +
            "jgz a -1\n" +
            "set a 1\n" +
            "jgz a -2";

    @Before
    public void setup() {
        target = new Day18();
    }

    @Test
    public void testDeelEen1() {
        int result = target.deelEenA(INPUT);

        assertThat(result, is(4));
    }

    @Ignore
    @Test
    public void testDeelTwee1() {
        String result = target.deelTweeA(INPUT);

        assertThat(result, is(309));
    }

}