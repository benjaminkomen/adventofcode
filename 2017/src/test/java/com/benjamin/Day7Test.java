package com.benjamin;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day7Test {

    private static final String TEST_INPUT =
            "pbga (66)\n" +
            "xhth (57)\n" +
            "ebii (61)\n" +
            "havc (66)\n" +
            "ktlj (57)\n" +
            "fwft (72) -> ktlj, cntj, xhth\n" +
            "qoyq (66)\n" +
            "padx (45) -> pbga, havc, qoyq\n" +
            "tknk (41) -> ugml, padx, fwft\n" +
            "jptl (61)\n" +
            "ugml (68) -> gyxo, ebii, jptl\n" +
            "gyxo (61)\n" +
            "cntj (57)";

    private Day7 target;

    @Before
    public void setup() {
        target = new Day7();
    }

    @Test
    public void testDeelEen1() {
        String result = target.deelEenA(TEST_INPUT);

        assertThat(result, is("tknk"));
    }

    @Test
    public void testDeelEen2() {
        int result = target.deelTweeA(TEST_INPUT);

        assertThat(result, is(60));
    }

}