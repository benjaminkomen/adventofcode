package com.benjamin;

import com.benjamin.objects.CircularList;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 {

    private static final String INPUT = "102,255,99,252,200,24,219,57,103,2,226,254,1,0,69,216";

    public static void main(String[] args) {

        Day10 instance = new Day10();

        System.out.println("The answer to part 1 is: " + instance.deelEenA(INPUT, null));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(INPUT));
    }

    /**
     *
     */
    protected int deelEenA(final String input, List<Integer> list) {

        if (list == null) {
            list = Stream.iterate(0, n -> n + 1)
                    .limit(255)
                    .collect(Collectors.toCollection(CircularList::new));
        }

        int currentPosition = 0;
        int skipSize = 0;

        List<Integer> lengths = stringToIntegers(input);

        for (Integer length : lengths) {
            // reverse subList
            List<Integer> subListToReverse = list.subList(currentPosition, length);
            subListToReverse.sort(Comparator.reverseOrder());

            // move current position
            currentPosition = currentPosition + length + skipSize;

            // increase skipSize
            skipSize++;
        }

        return 0;
    }

    /**
     *
     */
    protected int deelTweeA(final String input) {
        return 0;
    }

    /**
     * Turn a string into a list of strings.
     */
    private List<Integer> stringToIntegers(String input) {
        return Arrays.stream(input.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }
}
