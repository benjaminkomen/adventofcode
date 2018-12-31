package com.benjamin;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

public class Day5 extends Day {

    public static void main(String[] args) {

        Day5 instance = new Day5();
        String input = instance.inputRepository.getInput(5);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input)); // 9296
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    protected int deelEenA(final String input) {
        return Arrays.stream(input.split("\n"))
                .map(this::performReactions)
                .map(String::length)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Could not determine length of polymer."));
    }

    /**
     * @return polymer after performing all possible reactions
     */
    private String performReactions(String input) {

        String result = input;
        long start = System.currentTimeMillis();

        // we are done when all reactions are performed
        do {
            result = performAnyReaction(result);
        } while (result.length() != performAnyReaction(result).length());

        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.printf("Execution time: %s seconds", formatter.format((System.currentTimeMillis() - start) / 1000d));
        return result;
    }

    /**
     * Does one pass over the inputString and performs the first reaction possible.
     * TODO this is not very efficient (74,84 seconds), performing more reactions in one pass would be better,
     * but than you'd need to change the array you are iterating over, to preserve correct indices..
     */
    private String performAnyReaction(String input) {
        StringBuilder result = new StringBuilder(input);
        final String[] units = input.split("");

        for (int i = 1; i < units.length; i++) {
            String previousUnit = units[i - 1];
            String currentUnit = units[i];

            // destroy two adjacent units of the same type and opposite polarity
            // must be of the same type, so equal ignoring case (e.g. a == A, but also a == a)
            // must have opposite polarity, so be not equal, not ignoring case (e.g. a != A)
            if (previousUnit.equalsIgnoreCase(currentUnit) && !previousUnit.equals(currentUnit)) {
                return result.delete(i - 1, i + 1).toString();
            }
        }

        return result.toString();
    }

    protected int deelTweeA(final String input) {
        return 0;
    }
}
