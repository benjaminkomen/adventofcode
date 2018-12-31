package com.benjamin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day4Test {

    private Day4 target;
    private static final String INPUT1 =
            "[1518-11-01 00:00] Guard #10 begins shift\n" +
                    "[1518-11-01 00:05] falls asleep\n" +
                    "[1518-11-01 00:25] wakes up\n" +
                    "[1518-11-01 00:30] falls asleep\n" +
                    "[1518-11-01 00:55] wakes up\n" +
                    "[1518-11-01 23:58] Guard #99 begins shift\n" +
                    "[1518-11-02 00:40] falls asleep\n" +
                    "[1518-11-02 00:50] wakes up\n" +
                    "[1518-11-03 00:05] Guard #10 begins shift\n" +
                    "[1518-11-03 00:24] falls asleep\n" +
                    "[1518-11-03 00:29] wakes up\n" +
                    "[1518-11-04 00:02] Guard #99 begins shift\n" +
                    "[1518-11-04 00:36] falls asleep\n" +
                    "[1518-11-04 00:46] wakes up\n" +
                    "[1518-11-05 00:03] Guard #99 begins shift\n" +
                    "[1518-11-05 00:45] falls asleep\n" +
                    "[1518-11-05 00:55] wakes up\n";
    private static final String INPUT2 =
            "[1518-02-15 23:57] Guard #433 begins shift\n" +
                    "[1518-02-16 00:28] falls asleep\n" +
                    "[1518-02-16 00:45] wakes up\n" +
                    "[1518-02-16 00:50] falls asleep\n" +
                    "[1518-02-16 00:55] wakes up\n" +
                    "[1518-02-17 00:00] Guard #677 begins shift\n" +
                    "[1518-02-17 00:38] falls asleep\n" +
                    "[1518-02-17 00:42] wakes up\n" +
                    "[1518-02-18 00:02] Guard #277 begins shift\n" +
                    "[1518-02-18 00:06] falls asleep\n" +
                    "[1518-02-18 00:57] wakes up\n" +
                    "[1518-02-19 00:01] Guard #109 begins shift\n" +
                    "[1518-02-19 00:21] falls asleep\n" +
                    "[1518-02-19 00:34] wakes up\n" +
                    "[1518-02-20 00:02] Guard #239 begins shift\n" +
                    "[1518-02-20 00:13] falls asleep\n" +
                    "[1518-02-20 00:48] wakes up\n" +
                    "[1518-02-20 23:56] Guard #1097 begins shift\n" +
                    "[1518-02-21 00:08] falls asleep\n" +
                    "[1518-02-21 00:17] wakes up\n" +
                    "[1518-02-21 00:21] falls asleep\n" +
                    "[1518-02-21 00:45] wakes up\n" +
                    "[1518-02-21 23:57] Guard #677 begins shift\n" +
                    "[1518-02-22 00:18] falls asleep\n" +
                    "[1518-02-22 00:38] wakes up\n" +
                    "[1518-02-22 00:47] falls asleep\n" +
                    "[1518-02-22 00:48] wakes up\n" +
                    "[1518-02-22 00:56] falls asleep\n" +
                    "[1518-02-22 00:58] wakes up";

    @BeforeEach
    public void setup() {
        target = new Day4();
    }

    @Test
    public void testDeelEen1() {
        int result = target.deelEenA(INPUT1);

        assertThat(result, is(240));
    }

    @Test
    public void testDeelEen2() {
        int result = target.deelEenA(INPUT2);

        assertThat(result, is(1662));
    }

    /**
     * Guard 1 is 6 minutes asleep, guard 2 is 9 minutes asleep; expected guard 2
     */
    @Test
    public void testGetMostAsleepGuardId() {
        Map<Integer, Integer> minutesAsleep1 = Map.of(1, 1, 2, 1, 3, 1, 4, 1, 5, 2);
        Map<Integer, Integer> minutesAsleep2 = Map.of(1, 2, 2, 3, 3, 2, 4, 1, 5, 1);
        Map<Integer, Map<Integer, Integer>> map = Map.of(1, minutesAsleep1, 2, minutesAsleep2);
        Integer result = target.getMostAsleepGuardId(map);
        assertThat(result, is(2));
    }

    /**
     * Guard 1 is most asleep during minute 5
     */
    @Test
    public void testGetMinuteMostAsleep() {
        Map<Integer, Integer> minutesAsleep1 = Map.of(1, 1, 2, 1, 3, 1, 4, 1, 5, 2);
        Map<Integer, Integer> minutesAsleep2 = Map.of(1, 2, 2, 3, 3, 2, 4, 1);
        Map<Integer, Map<Integer, Integer>> map = Map.of(1, minutesAsleep1, 2, minutesAsleep2);
        Integer result = target.getMinuteMostAsleep(map, 1);
        assertThat(result, is(5));
    }

    @Test
    public void testDeelTwee1() {
        int result = target.deelTweeA(INPUT1);

        assertThat(result, is(4455));
    }
}
