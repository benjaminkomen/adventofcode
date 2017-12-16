package com.benjamin;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day10Test {

    private static final List<Integer> INTEGER_LIST = new ArrayList<>(List.of(0, 1, 2, 3, 4));
    private static final String INPUT_DEEL1 = "3,4,1,5";
    private static final String INPUT_DEEL2_1 = "";
    private static final String INPUT_DEEL2_2 = "AoC 2017";
    private static final String INPUT_DEEL2_3 = "1,2,3";
    private static final String INPUT_DEEL2_4 = "1,2,4";
    private Day10 target;

    @Before
    public void setup() {
        target = new Day10();
    }

    @Test
    public void testDeelEen() {
        int result = target.deelEenA(INPUT_DEEL1, INTEGER_LIST);

        assertThat(result, is(12));
    }

    @Test
    public void testDeelTwee_1() {
        String result = target.deelTweeA(INPUT_DEEL2_1);

        assertThat(result, is("a2582a3a0e66e6e86e3812dcb672a272"));
    }

    @Test
    public void testDeelTwee_2() {
        String result = target.deelTweeA(INPUT_DEEL2_2);

        assertThat(result, is("33efeb34ea91902bb2f59c9920caa6cd"));
    }

    @Test
    public void testDeelTwee_3() {
        String result = target.deelTweeA(INPUT_DEEL2_3);

        assertThat(result, is("3efbe78a8d82f29979031a4aa0b16a9d"));
    }

    @Test
    public void testDeelTwee_4() {
        String result = target.deelTweeA(INPUT_DEEL2_4);

        assertThat(result, is("63960835bcdc130f0b66d7ff4f6a5a8e"));
    }

    @Test
    public void testStringToBytes() {
        assertThat(target.stringToBytes("1,2,3"), is(List.of(b(49), b(44), b(50), b(44), b(51))));
    }

    @Test
    public void testApplyXOR() {
        assertThat(target.applyXOR(List.of(65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22)), is(64));
    }

    @Test
    public void testConvertToHex() {
        assertThat(target.convertToHex(List.of(64, 7, 255)), is("4007ff"));
    }

    // cast an int to a byte
    private byte b(int i) {
        return (byte) i;
    }
}
