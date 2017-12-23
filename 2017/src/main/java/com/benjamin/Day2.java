package com.benjamin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 extends Day {

    public static void main(String[] args) {

        Day2 instance = new Day2();
        String input = instance.inputRepository.getInput(2);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    /**
     * Compute a checksum from a "spreadsheet" of numbers.
     */
    protected int deelEenA(final String input) {
        return stringToSpreadsheet(input).stream()
                .map(row -> {
                    int max = Arrays.stream(row).max().orElse(0);
                    int min = Arrays.stream(row).min().orElse(0);
                    return max - min;
                })
                .mapToInt(i -> i)
                .sum();
    }

    /**
     * Compute a different checksum from a "spreadsheet" of numbers.
     */
    protected int deelTweeA(final String input) {
        return stringToSpreadsheet(input).stream()
                .map(row -> Arrays.stream(row)
                        .map(i -> evenDivision(row))
                        .findFirst()
                        .orElse(0))
                .mapToInt(i -> i)
                .sum();
    }

    /**
     * Given an array of numbers, divide each element with all other elements and return the division if it is even,
     * or else zero.
     */
    private int evenDivision(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            int[] otherNumbers = makeOthersArray(numbers, i);
            for (int otherNumber : otherNumbers) {
                final int modulo = numbers[i] % otherNumber;
                if (modulo == 0) {
                    return numbers[i] / otherNumber;
                }
            }
        }
        return 0;
    }

    /**
     * Turn a string into a matrix-like list of lists of integers.
     */
    private List<int[]> stringToSpreadsheet(String input) {
        return Arrays.stream(input.split("\n"))
                .map(line -> line.split(","))
                .map(array -> Arrays.stream(array)
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .collect(Collectors.toList());
    }

    /**
     * Return exactly the same array as the input, but omit one element at the given position.
     */
    protected int[] makeOthersArray(int[] numbers, int elementPosition) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            if (i != elementPosition) {
                result.add(numbers[i]);
            }
        }
        return result.stream().mapToInt(i -> i).toArray();
    }
}
