package com.benjamin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
     * Shuffle lists in a weird pattern and then return the multiplication of the first two elements.
     */
    protected int deelEenA(final String input, List<Integer> list) {

        if (list == null) {
            list = Stream.iterate(0, n -> n + 1)
                    .limit(256)
                    .collect(Collectors.toList());
        }

        int currentPosition = 0;
        int skipSize = 0;

        List<Integer> lengths = stringToIntegers(input);

        for (Integer length : lengths) {

            // rotate to zero, so the subList won't have to access indices out of bounds
            Collections.rotate(list, -currentPosition);

            // reverse the sublist
            Collections.reverse(list.subList(0, length));

            // rotate back to correctness
            Collections.rotate(list, currentPosition);

            // move current position
            currentPosition = (currentPosition + length + skipSize) % list.size();

            // increase skipSize
            skipSize++;
        }

        return list.get(0) * list.get(1); //2162 is too low
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
