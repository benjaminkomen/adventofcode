package com.benjamin;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day14Test {

    private static final String INPUT_DEEL1 = "flqrgnkx";
    private Day14 target;

    @Before
    public void setup() {
        target = new Day14();
    }

    @Test
    public void testDeelEen() {
        long result = target.deelEenA(INPUT_DEEL1);

        assertThat(result, is(8108L));
    }

    @Test
    public void testConvertToBitRepresentation() {

        String result = target.convertToBitRepresentation("a0c2017");

        assertThat(result, is("1010000011000010000000010111"));
    }

}