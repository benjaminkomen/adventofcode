package com.benjamin.objects;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CircularListTest {

    private CircularList<Integer> target;

    @Before
    public void setup() {
        target = new CircularList<>(List.of(0,1,2,3,4,5,6,7,8,9,10));
    }

    @Test
    public void testNormalListBehaviour() {
        assertThat(target.get(0), is(0));
        assertThat(target.get(1), is(1));
        assertThat(target.get(10), is(10));
    }

    @Test
    public void testCircularListBehaviour() {
        assertThat(target.get(-1), is(10));
        assertThat(target.get(-5), is(6));
        assertThat(target.get(-15), is(7));
        assertThat(target.get(21), is(10));
    }
}