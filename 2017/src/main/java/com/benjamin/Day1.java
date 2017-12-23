package com.benjamin;

import java.util.Arrays;

public class Day1 extends Day {

    public static void main(String[] args) {

        Day1 instance = new Day1();
        String input = instance.inputRepository.getInput(1);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    protected int deelEenA(final String input) {
        final int[] intArray = Arrays.stream(input.split("")).mapToInt(Integer::parseInt).toArray();
        int sum = 0;

        for (int i = 0; i < intArray.length; i++) {

            // special case for the last element of the array
            if (i == intArray.length - 1) {
                if (intArray[i] == intArray[0]) {
                    sum += intArray[i];
                    break;
                } else {
                    break;
                }
            } else if (intArray[i] == intArray[i + 1]) { // add one element to the sum if two consecutive elements are equal
                sum += intArray[i];
            }
        }

        return sum;
    }

    /**
     * The following method attempts to solve the puzzle using functional programming. It does not work yet, since the
     * reduce operation works different than I need :/
     */
    protected int deelEenB(final String input) {
        return Arrays.stream(input.split(""))
                .mapToInt(Integer::parseInt)
                .reduce((i, j) -> {
                    if (i == j) {
                        System.out.println("They are equal! i=" + i + " and j=" + j);
                        return i;
                    } else {
                        return i;
                    }
                })
                .orElse(0);
    }

    protected int deelTweeA(final String input) {
        final int[] intArray = Arrays.stream(input.split("")).mapToInt(Integer::parseInt).toArray();
        final int[] intArrayDoubleSize = combine(intArray, intArray);
        int sum = 0;

        for (int i = 0; i < intArrayDoubleSize.length / 2; i++) {

            final int currentDigit = intArray[i];
            final int positionComparingDigit = i + (intArray.length / 2);
            final int comparingDigit = intArrayDoubleSize[positionComparingDigit];

            if (currentDigit == comparingDigit) {
                sum += currentDigit;
            }
        }

        return sum;
    }

    private static int[] combine(int[] a, int[] b){
        int length = a.length + b.length;
        int[] result = new int[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
