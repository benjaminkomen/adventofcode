package com.benjamin;

import java.util.Arrays;

public class Day5 extends Day {

    public static void main(String[] args) {

        Day5 instance = new Day5();
        String input = instance.inputRepository.getInput(5);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    /**
     * Return the amount of steps it takes to jump out of a list of integers which numbers represent jump instructions.
     */
    protected long deelEenA(final String input) {
        int[] listOfNumbers = stringToIntegerArray(input);
        long count = 0;
        int arrayIndex = 0;
        int jumpOffset;

        while (count < Long.MAX_VALUE) {

            try {
                jumpOffset = listOfNumbers[arrayIndex]; // read the jumpOffset from the array

                listOfNumbers[arrayIndex]++; // increase the number in the list

                arrayIndex = arrayIndex + jumpOffset; // jump to the next array index
                count++; // increase the counter which counts how many times we jumped

            } catch (ArrayIndexOutOfBoundsException e) {
                return count;
            }
        }
        return 0;
    }

    /**
     * Return the amount of steps it takes to jump out of a list of integers which numbers represent jump instructions.
     * This part is only slightly modified with a conditional increase/decrease instruction.
     */
    protected long deelTweeA(final String input) {
        int[] listOfNumbers = stringToIntegerArray(input);
        long count = 0;
        int arrayIndex = 0;
        int jumpOffset;

        while (count < Long.MAX_VALUE) {

            try {
                jumpOffset = listOfNumbers[arrayIndex]; // read the jumpOffset from the array

                if (jumpOffset >= 3) {
                    listOfNumbers[arrayIndex]--; // decrease the number in the list
                } else {
                    listOfNumbers[arrayIndex]++; // increase the number in the list
                }
                arrayIndex = arrayIndex + jumpOffset; // jump to the next array index
                count++; // increase the counter which counts how many times we jumped

            } catch (ArrayIndexOutOfBoundsException e) {
                return count;
            }
        }
        return 0;
    }

    /**
     * Returns an array of integers from the input.
     */
    private int[] stringToIntegerArray(String input) {
        return Arrays.stream(input.split("\n"))
                .map(Integer::parseInt)
                .mapToInt(i -> i)
                .toArray();
    }
}
