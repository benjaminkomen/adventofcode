package com.benjamin;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day2Test {

    private static final String TEST_INPUT_DEEL_EEN = "5,1,9,5\n7,5,3\n2,4,6,8";
    private static final String TEST_INPUT_DEEL_TWEE = "5,9,2,8\n9,4,7,3\n3,8,6,5";

    private Day2 target;

    @Before
    public void setup() {
        target = new Day2();
    }

    @Test
    public void testDeelEen() {
        int result = target.deelEenA(TEST_INPUT_DEEL_EEN);

        assertThat(result, is(18));
    }

    @Test
    public void testDeelTwee() {
        int result = target.deelTweeA(TEST_INPUT_DEEL_TWEE);

        assertThat(result, is(9));
    }

    @Test
    public void testMakeOthersArray() {
        int[] inputArray = {1, 2, 3, 4};

        int[] result = target.makeOthersArray(inputArray, 2);

        assertThat(result, is(new int[]{1, 2, 4}));
    }

}