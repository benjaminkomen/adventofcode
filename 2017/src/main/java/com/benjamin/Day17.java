package com.benjamin;

import java.util.ArrayList;
import java.util.List;

public class Day17 extends Day {

    private static final int INPUT = 359;

    public static void main(String[] args) {

        Day17 instance = new Day17();

        System.out.println("The answer to part 1 is: " + instance.deelEenA(INPUT));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(INPUT));
    }

    /**
     * Spinlock algorithm.
     */
    protected int deelEenA(int stepsPerInsert) {

        List<Integer> circularBuffer = new ArrayList<>();
        circularBuffer.add(0);
        int currentPosition = 0;

        for (int currentValue = 1; currentValue < 2018; currentValue++) {
            currentPosition = ((currentPosition + stepsPerInsert) % currentValue) + 1;
            circularBuffer.add(currentPosition, currentValue);
        }

        return circularBuffer.get(currentPosition + 1);
    }

    /**
     * Stole it a bit from https://www.reddit.com/r/adventofcode/comments/7kc0xw/2017_day_17_solutions/drda34x/
     * The question was confusing.
     */
    protected int deelTweeA(int stepsPerInsert) {
        int currentPosition = 0;
        int lastSeen = -1;

        for (int currentValue = 1; currentValue <= 50_000_000; currentValue++) {
            currentPosition = ((currentPosition + stepsPerInsert) % currentValue);

            if (currentPosition++ == 0) {
                lastSeen = currentValue;
            }
        }

        return lastSeen;
    }
}
