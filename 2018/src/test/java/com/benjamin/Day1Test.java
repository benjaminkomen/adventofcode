package com.benjamin;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class Day1Test {

    private Day1 target;

    @BeforeEach
    public void setup() {
        target = new Day1();
    }

    @Test
    public void testDeelEen1() {
        assertThat(target.deelEenA("+1\n-2\n+3\n+1"), Matchers.is(3));
        assertThat(target.deelEenA("+1\n+1\n+1"), Matchers.is(3));
        assertThat(target.deelEenA("+1\n+1\n-2"), Matchers.is(0));
        assertThat(target.deelEenA("-1\n-2\n-3"), Matchers.is(-6));
    }

    @Test
    public void testDeelTwee1() {
        assertThat(target.deelTweeA("+1\n-2\n+3\n+1"), Matchers.is(2));
        assertThat(target.deelTweeA("+1\n-1"), Matchers.is(0));
        assertThat(target.deelTweeA("+3\n+3\n+4\n-2\n-4"), Matchers.is(10));
        assertThat(target.deelTweeA("-6\n+3\n+8\n+5\n-6"), Matchers.is(5));
        assertThat(target.deelTweeA("+7\n+7\n-2\n-7\n-4"), Matchers.is(14));
    }
}
