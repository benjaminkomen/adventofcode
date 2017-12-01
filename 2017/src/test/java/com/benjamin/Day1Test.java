package com.benjamin;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class Day1Test {

    private static final String TEST_INPUT_DEEL_EEN_1 = "1122";
    private static final String TEST_INPUT_DEEL_EEN_2 = "1111";
    private static final String TEST_INPUT_DEEL_EEN_3 = "1234";
    private static final String TEST_INPUT_DEEL_EEN_4 = "91212129";
    private static final String TEST_INPUT_DEEL_TWEE_1 = "1212";
    private static final String TEST_INPUT_DEEL_TWEE_2 = "1221";
    private static final String TEST_INPUT_DEEL_TWEE_3 = "123425";
    private static final String TEST_INPUT_DEEL_TWEE_4 = "123123";
    private static final String TEST_INPUT_DEEL_TWEE_5 = "12131415";

    private Day1 target;

    @Before
    public void setup() {
        target = new Day1();
    }

    @Test
    public void testDeelEen1() {
        int result = target.deelEenA(TEST_INPUT_DEEL_EEN_1);

        assertThat(result, Matchers.is(3));
    }

    @Test
    public void testDeelEen2() {
        int result = target.deelEenA(TEST_INPUT_DEEL_EEN_2);

        assertThat(result, Matchers.is(4));
    }

    @Test
    public void testDeelEen3() {
        int result = target.deelEenA(TEST_INPUT_DEEL_EEN_3);

        assertThat(result, Matchers.is(0));
    }

    @Test
    public void testDeelEen4() {
        int result = target.deelEenA(TEST_INPUT_DEEL_EEN_4);

        assertThat(result, Matchers.is(9));
    }

    @Test
    public void testDeelTwee1() {
        int result = target.deelTweeA(TEST_INPUT_DEEL_TWEE_1);

        assertThat(result, Matchers.is(6));
    }

    @Test
    public void testDeelTwee2() {
        int result = target.deelTweeA(TEST_INPUT_DEEL_TWEE_2);

        assertThat(result, Matchers.is(0));
    }

    @Test
    public void testDeelTwee3() {
        int result = target.deelTweeA(TEST_INPUT_DEEL_TWEE_3);

        assertThat(result, Matchers.is(4));
    }

    @Test
    public void testDeelTwee4() {
        int result = target.deelTweeA(TEST_INPUT_DEEL_TWEE_4);

        assertThat(result, Matchers.is(12));
    }

    @Test
    public void testDeelTwee5() {
        int result = target.deelTweeA(TEST_INPUT_DEEL_TWEE_5);

        assertThat(result, Matchers.is(4));
    }
}
