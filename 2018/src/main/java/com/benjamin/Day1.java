package com.benjamin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day1 extends Day {

    public static void main(String[] args) {

        Day1 instance = new Day1();
        String input = instance.inputRepository.getInput(1);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input)); // 479
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input)); // 66105
    }

    protected int deelEenA(final String input) {
        return Arrays.stream(input.split("\n"))
                .mapToInt(Integer::parseInt)
                .reduce(0, (a, b) -> a + b);
    }

    /**
     * Gets the price for the most ugly non-functional coding.
     */
    protected int deelTweeA(final String input) {
        final Set<Integer> history = new HashSet<>();
        Integer sum = 0;
        history.add(sum);

        final List<Integer> inputNumbers = Arrays.stream(input.split("\n"))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        int index = 0;
        Integer currentNumber;
        while (true) {
            try {
                 currentNumber = inputNumbers.get(index);
            } catch (IndexOutOfBoundsException e) {
                index = 0;
                currentNumber = inputNumbers.get(index);
            }

            index++;

            sum = sum + currentNumber;

            if (history.contains(sum)) {
                return sum;
            } else {
                history.add(sum);
            }
        }
    }
}
