package com.benjamin;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day9Test {

    private static final String TEST_INPUT_1 = "{}";
    private static final String TEST_INPUT_2 = "{{{}}}";
    private static final String TEST_INPUT_3 = "{{},{}}";
    private static final String TEST_INPUT_4 = "{{{},{},{{}}}}";
    private static final String TEST_INPUT_5 = "{<{},{},{{}}>}";
    private static final String TEST_INPUT_6 = "{<a>,<a>,<a>,<a>}";
    private static final String TEST_INPUT_7 = "{{<a>},{<a>},{<a>},{<a>}}";
    private static final String TEST_INPUT_8 = "{{<!>},{<!>},{<!>},{<a>}}";
    private static final String TEST_INPUT_9 = "{{<ab>},{<ab>},{<ab>},{<ab>}}";
    private static final String TEST_INPUT_10 = "{{<!!>},{<!!>},{<!!>},{<!!>}}";
    private static final String TEST_INPUT_11 = "{{<a!>},{<a!>},{<a!>},{<ab>}}";
    private static final String TEST_INPUT_12 = "<>";
    private static final String TEST_INPUT_13 = "<random characters>";
    private static final String TEST_INPUT_14 = "<<<<>";
    private static final String TEST_INPUT_15 = "<{!>}>";
    private static final String TEST_INPUT_16 = "<!!>";
    private static final String TEST_INPUT_17 = "<!!!>>";
    private static final String TEST_INPUT_18 = "<{o\"i!a,<{i<a>";

    private Day9 target;

    @Before
    public void setup() {
        target = new Day9();
    }

    @Test
    public void testDeelEen1_1() throws Exception { // passed
        int result = target.deelEenA(TEST_INPUT_1);

        assertThat(result, is(1));
    }

    @Test
    public void testDeelEen1_2() throws Exception { // passed
        int result = target.deelEenA(TEST_INPUT_2);

        assertThat(result, is(6));
    }

    @Test
    public void testDeelEen1_3() throws Exception { // passed
        int result = target.deelEenA(TEST_INPUT_3);

        assertThat(result, is(5));
    }

    @Test
    public void testDeelEen1_4() throws Exception { // passed
        int result = target.deelEenA(TEST_INPUT_4);

        assertThat(result, is(16));
    }

    @Test
    public void testDeelEen1_5() throws Exception { // passed
        int result = target.deelEenA(TEST_INPUT_5);

        assertThat(result, is(1));
    }

    @Test
    public void testDeelEen1_6() throws Exception { // passed
        int result = target.deelEenA(TEST_INPUT_6);

        assertThat(result, is(1));
    }

    @Test
    public void testDeelEen1_7() throws Exception { // passed
        int result = target.deelEenA(TEST_INPUT_7);

        assertThat(result, is(9));
    }

    @Test
    public void testDeelEen1_8() throws Exception { // passed
        int result = target.deelEenA(TEST_INPUT_8);

        assertThat(result, is(3));
    }

    @Test
    public void testDeelEen1_9() throws Exception { // passed
        int result = target.deelEenA(TEST_INPUT_9);

        assertThat(result, is(9));
    }

    @Test
    public void testDeelEen1_10() throws Exception { // passed
        int result = target.deelEenA(TEST_INPUT_10);

        assertThat(result, is(9));
    }

    @Test
    public void testDeelEen1_11() throws Exception { // passed
        int result = target.deelEenA(TEST_INPUT_11);

        assertThat(result, is(3));
    }

    @Test
    public void testDeelEen2_12() throws Exception { // passed
        int result = target.deelTweeA(TEST_INPUT_12);

        assertThat(result, is(0));
    }

    @Test
    public void testDeelEen2_13() throws Exception { // passed
        int result = target.deelTweeA(TEST_INPUT_13);

        assertThat(result, is(17));
    }

    @Test
    public void testDeelEen2_14() throws Exception { // passed
        int result = target.deelTweeA(TEST_INPUT_14);

        assertThat(result, is(3));
    }

    @Test
    public void testDeelEen2_15() throws Exception { // passed
        int result = target.deelTweeA(TEST_INPUT_15);

        assertThat(result, is(2));
    }

    @Test
    public void testDeelEen2_16() throws Exception { // passed
        int result = target.deelTweeA(TEST_INPUT_16);

        assertThat(result, is(0));
    }

    @Test
    public void testDeelEen2_17() throws Exception { // passed
        int result = target.deelTweeA(TEST_INPUT_17);

        assertThat(result, is(0));
    }

    @Test
    public void testDeelEen2_18() throws Exception { // passed
        int result = target.deelTweeA(TEST_INPUT_18);

        assertThat(result, is(10));
    }
}