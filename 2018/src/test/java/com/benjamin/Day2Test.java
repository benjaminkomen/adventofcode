package com.benjamin;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class Day2Test {

    private Day2 target;
    private static final String INPUT1 =
            "abcdef\n" +
            "bababc\n" +
            "abbcde\n" +
            "abcccd\n" +
            "aabcdd\n" +
            "abcdee\n" +
            "ababab\n";

    private static final String INPUT2 =
            "abcde\n" +
            "fghij\n" +
            "klmno\n" +
            "pqrst\n" +
            "fguij\n" +
            "axcye\n" +
            "wvxyz";

    @BeforeEach
    public void setup() {
        target = new Day2();
    }

    @Test
    public void testDeelEen1() {
        int result = target.deelEenA(INPUT1);

        assertThat(result, Matchers.is(12));
    }

    @Test
    public void testDeelEen2() {
        int result = target.deelEenB(INPUT1);

        assertThat(result, Matchers.is(12));
    }

    @Test
    public void testDeelTwee1() {
        String result = target.deelTweeA(INPUT2);

        assertThat(result, Matchers.is("fgij"));
    }
}
