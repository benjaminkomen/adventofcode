package com.benjamin;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Day2 extends Day {

    public static void main(String[] args) {

        Day2 instance = new Day2();
        String input = instance.inputRepository.getInput(2);

        System.out.println("The answer to part 1 is: " + instance.deelEenB(input)); // 8610
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input)); // iosnxmfkpabcjpdywvrtahluy
    }

    /**
     * It works, but has a for-loop and mutates variables..
     */
    protected int deelEenA(final String input) {

        int exactlyTwoLetters = 0;
        int exactlyThreeLetters = 0;

        for (String boxID : input.split("\n")) {
            Map<Character, Integer> letterFrequency = generateLetterFrequency(boxID);
            if (hasExactlyTwoLetters(letterFrequency)) {
                exactlyTwoLetters++;
            }

            if (hasExactlyThreeLetters(letterFrequency)) {
                exactlyThreeLetters++;
            }
        }

        return exactlyTwoLetters * exactlyThreeLetters;
    }

    /**
     * More functional-style code
     */
    protected int deelEenB(final String input) {

        return Arrays.stream(input.split("\n"))
                .map(this::generateLetterFrequency)
                .map(letterFrequency -> {
                    final int exactlyTwoLetters = hasExactlyTwoLetters(letterFrequency) ? 1 : 0;
                    final int exactlyThreeLetters = hasExactlyThreeLetters(letterFrequency) ? 1 : 0;
                    return new Counters(exactlyTwoLetters, exactlyThreeLetters);
                })
                .reduce(Counters.empty(), Counters::add)
                .sum();
    }

    /**
     * Count how many times each character occurs in a given string.
     */
    @NotNull
    private Map<Character, Integer> generateLetterFrequency(String boxID) {
        Map<Character, Integer> letterFrequency = new HashMap<>();

        Arrays.stream(boxID.split(""))
                .map(s -> s.charAt(0))
                .forEach(character -> letterFrequency.merge(character, 1, (oldCount, newCount) -> oldCount + newCount));
        return letterFrequency;
    }

    private boolean hasExactlyTwoLetters(Map<Character, Integer> letterFrequency) {
        return letterFrequency.entrySet().stream()
                .anyMatch(e -> e.getValue() == 2);
    }

    private boolean hasExactlyThreeLetters(Map<Character, Integer> letterFrequency) {
        return letterFrequency.entrySet().stream()
                .anyMatch(e -> e.getValue() == 3);
    }

    protected String deelTweeA(final String input) {

        final String[] boxIDs = input.split("\n");

        for (String boxID: boxIDs) {
            for (String otherBoxID: boxIDs) {
                int amountOfDifferentCharacters = amountOfDifferentCharacters(boxID, otherBoxID);

                if (amountOfDifferentCharacters == 1) {
                    return charactersInCommon(boxID, otherBoxID);
//                    System.out.println("The following characters are in common: " + charactersInCommon(boxID, otherBoxID));
                }
            }
        }
        return "";
    }

    /**
     * @return amount of different characters between strings. This will be zero if both strings
     * are equal, the length of the strings if they are all different and something in between 0 and length of string
     * if some are equal and same are not.
     */
    private int amountOfDifferentCharacters(String first, String second) {

        if (first.length() != second.length()) {
            throw new IllegalStateException(String.format("Both strings must be of equal length," +
                    " but first was %s and second was %s", first.length(), second.length()));
        }

        int amountOfDifferentCharacters = 0;

        String[] firstSplit = first.split("");
        String[] secondSplit = second.split("");

        for (int i = 0; i < firstSplit.length; i++) {
            if (!Objects.equals(firstSplit[i], secondSplit[i])) {
                amountOfDifferentCharacters++;
            }
        }

        return amountOfDifferentCharacters;
    }

    private String charactersInCommon(String first, String second) {

        if (first.length() != second.length()) {
            throw new IllegalStateException(String.format("Both strings must be of equal length," +
                    " but first was %s and second was %s", first.length(), second.length()));
        }

        StringBuilder result = new StringBuilder();
        String[] firstSplit = first.split("");
        String[] secondSplit = second.split("");

        for (int i = 0; i < firstSplit.length; i++) {
            if (Objects.equals(firstSplit[i], secondSplit[i])) {
                result.append(firstSplit[i]);
            }
        }

        return result.toString();
    }

    /**
     * A Tuple-like object to store the count of letterFrequencies.
     */
    private static class Counters {
        private final int twoLetters;
        private final int threeLetters;

        private Counters() {
            this.twoLetters = 0;
            this.threeLetters = 0;
        }

        public Counters(int twoLetters, int threeLetters) {
            this.twoLetters = twoLetters;
            this.threeLetters = threeLetters;
        }

        public static Counters empty() {
            return new Counters();
        }

        public static Counters add(Counters oldCounters, Counters newCounters) {
            return new Counters(oldCounters.twoLetters + newCounters.twoLetters,
                    oldCounters.threeLetters + newCounters.threeLetters);
        }

        public int sum() {
            return this.twoLetters * this.threeLetters;
        }
    }
}
