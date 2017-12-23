package com.benjamin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 extends Day {

    private static final String SEED = "abcdefghijklmnop";

    public static void main(String[] args) {

        Day16 instance = new Day16();
        String input = instance.inputRepository.getInput(16);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(SEED, input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(SEED, input));
    }

    /**
     * Start with a string, apply a certain amount of dancing instructions and return the output String.
     */
    protected String deelEenA(String seed, final String input) {
        int initialLength = seed.length();
        List<DanceMove> danceMoves = stringToDanceMoveList(input);

        seed = applyDanceMoves(seed, danceMoves);

        // double check that our dancing did not change the length of the String.
        if (initialLength != seed.length()) {
            throw new IllegalStateException(String.format("The length of the input was %s," +
                    " but the length of the result is %s.", initialLength, seed.length()));
        }

        return seed;
    }

    /**
     * The same as part 1, but 1 billion times. The trick is to memoize all input-output relations you encounter, thus
     * after only ~56 "expensive" loops, you have saved all answers in the Map and can quickly lookup from it for the
     * rest of the loops.
     */
    protected String deelTweeA(String inputSeed, final String input) {

        List<DanceMove> danceMoves = stringToDanceMoveList(input);

        Map<String, String> memoizedDances = new HashMap<>(); // key-value pairs of inputSeed-outputSeed
        String outputSeed;

        for (int i = 0; i < 1_000_000_000; i++) {
            if (memoizedDances.containsKey(inputSeed)) {
                outputSeed = memoizedDances.get(inputSeed);
            } else {
                outputSeed = applyDanceMoves(inputSeed, danceMoves);
                memoizedDances.put(inputSeed, outputSeed);
            }

            inputSeed = outputSeed;
        }

        return inputSeed;
    }

    private String applyDanceMoves(String seed, List<DanceMove> danceMoves) {
        for (DanceMove danceMove : danceMoves) {
            switch (danceMove.danceMoveType) {
                case Spin:
                    seed = applySpin(seed, danceMove);
                    break;
                case Exchange:
                    seed = applyExchange(seed, danceMove);
                    break;
                case Partner:
                    seed = applyPartner(seed, danceMove);
                    break;
            }
        }
        return seed;
    }

    /**
     * Move the last part of the string to the front.
     */
    protected String applySpin(String seed, DanceMove danceMove) {
        String firstPart = seed.substring(0, seed.length() - danceMove.spinAmount);
        String lastPart = seed.substring(seed.length() - danceMove.spinAmount, seed.length());
        return lastPart + firstPart;
    }

    /**
     * Swap places of 2 characters at given index positions.
     */
    protected String applyExchange(String seed, DanceMove danceMove) {
        char[] inputChars = seed.toCharArray();
        char[] outputChars = inputChars.clone();
        outputChars[danceMove.exchangePositionB] = inputChars[danceMove.exchangePositionA];
        outputChars[danceMove.exchangePositionA] = inputChars[danceMove.exchangePositionB];

        return String.valueOf(outputChars);
    }

    /**
     * Swap places of 2 given characters.
     */
    protected String applyPartner(String seed, DanceMove danceMove) {
        char[] inputChars = seed.toCharArray();
        char[] outputChars = inputChars.clone();

        outputChars[seed.indexOf(danceMove.partnerPositionB)] = inputChars[seed.indexOf(danceMove.partnerPositionA)];
        outputChars[seed.indexOf(danceMove.partnerPositionA)] = inputChars[seed.indexOf(danceMove.partnerPositionB)];

        return String.valueOf(outputChars);
    }

    /**
     * Turn a string into a list DanceMove objects.
     */
    private List<DanceMove> stringToDanceMoveList(String input) {
        return Arrays.stream(input.split(","))
                .map(this::makeDanceMove)
                .collect(Collectors.toList());
    }

    /**
     * Factory method to create a DanceMove object from a given input String.
     */
    private DanceMove makeDanceMove(String string) {
        if (string.startsWith("s")) {
            Pattern pattern = Pattern.compile("^s([0-9]+)$");
            Matcher matcher = pattern.matcher(string);

            Integer spinAmount = matcher.matches()
                    ? Integer.valueOf(matcher.group(1))
                    : null;

            return new DanceMove(DanceMoveType.Spin, spinAmount);
        } else if (string.startsWith("x")) {
            Pattern pattern = Pattern.compile("^x([0-9]+)\\/([0-9]+)$");
            Matcher matcher = pattern.matcher(string);

            Integer exchangePositionA = matcher.matches()
                    ? Integer.valueOf(matcher.group(1))
                    : null;

            Integer exchangePositionB = matcher.matches()
                    ? Integer.valueOf(matcher.group(2))
                    : null;

            return new DanceMove(DanceMoveType.Exchange, exchangePositionA, exchangePositionB);
        } else if (string.startsWith("p")) {
            Pattern pattern = Pattern.compile("^p([a-z]+)\\/([a-z]+)$");
            Matcher matcher = pattern.matcher(string);

            String partnerPositionA = matcher.matches()
                    ? matcher.group(1)
                    : null;

            String partnerPositionB = matcher.matches()
                    ? matcher.group(2)
                    : null;

            return new DanceMove(DanceMoveType.Partner, partnerPositionA, partnerPositionB);
        } else {
            throw new IllegalStateException(String.format("Unsupported input %s", string));
        }
    }

    public static class DanceMove {
        private DanceMoveType danceMoveType;
        private Integer spinAmount;
        private Integer exchangePositionA;
        private Integer exchangePositionB;
        private String partnerPositionA;
        private String partnerPositionB;

        private DanceMove() {
            // no-args constructor
        }

        public DanceMove(DanceMoveType danceMoveType, Integer spinAmount) {
            this.danceMoveType = danceMoveType;
            this.spinAmount = spinAmount;
        }

        public DanceMove(DanceMoveType danceMoveType, Integer exchangePositionA, Integer exchangePositionB) {
            this.danceMoveType = danceMoveType;
            this.exchangePositionA = exchangePositionA;
            this.exchangePositionB = exchangePositionB;
        }

        public DanceMove(DanceMoveType danceMoveType, String partnerPositionA, String partnerPositionB) {
            this.danceMoveType = danceMoveType;
            this.partnerPositionA = partnerPositionA;
            this.partnerPositionB = partnerPositionB;
        }
    }

    public enum DanceMoveType {
        Spin, Exchange, Partner;
    }
}
