package com.benjamin;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day4Test {

    private static final String TEST_INPUT_DEEL_EEN = "aa bb cc dd ee\naa bb cc dd aa\naa bb cc dd aaa";
    private static final String TEST_INPUT_DEEL_TWEE = "abcde fghij\nabcde xyz ecdab\na ab abc abd abf abj\niiii oiii ooii oooi oooo\noiii ioii iioi iiio";

    private Day4 target;

    @Before
    public void setup() {
        target = new Day4();
    }

    @Test
    public void testDeelEen1() {
        long result = target.deelEenA(TEST_INPUT_DEEL_EEN);

        assertThat(result, is(2L));
    }

    @Test
    public void testDeelEen2() {
        long result = target.deelTweeA(TEST_INPUT_DEEL_TWEE);

        assertThat(result, is(3L));
    }

    @Test
    public void testIsAnagram() {
        boolean result1 = target.isAnagram("aap", "paa");
        boolean result2 = target.isAnagram("bal", "abb");
        boolean result3 = target.isAnagram("aa", "aaa");

        assertThat("test 1: aap == paa", result1);
        assertThat("test 2: ba1 != abb", !result2);
        assertThat("test 3: aa != aaa", !result3);
    }
}