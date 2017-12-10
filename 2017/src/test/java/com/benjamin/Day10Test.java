package com.benjamin;

import com.benjamin.objects.CircularList;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day10Test {

    private static final String INPUT = "3,4,1,5";
    private static final List<Integer> INTEGER_LIST = new CircularList<>(List.of(0, 1, 2, 3, 4));
    private Day10 target;

    @Before
    public void setup() {
        target = new Day10();
    }

    @Test
    public void testDeelEen() {
        int result = target.deelEenA(INPUT, INTEGER_LIST);

        assertThat(result, is(99));
    }

    @Ignore
    @Test
    public void testDeelTwee() {
        int result = target.deelTweeA(INPUT);

        assertThat(result, is(99));
    }

}