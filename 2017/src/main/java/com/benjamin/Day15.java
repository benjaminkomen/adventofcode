package com.benjamin;

import java.util.ArrayList;
import java.util.List;

public class Day15 {

    private static final int INPUT_GENERATOR_A = 699;
    private static final int INPUT_GENERATOR_B = 124;

    public static void main(String[] args) {

        Day15 instance = new Day15();

        System.out.println("The answer to part 1 is: " + instance.deelEenA(INPUT_GENERATOR_A, INPUT_GENERATOR_B));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(INPUT_GENERATOR_A, INPUT_GENERATOR_B));
    }

    /**
     * Loop 40 million times and count how often the lowest 16 bits of two generated numbers match.
     */
    protected int deelEenA(int seedGeneratorA, int seedGeneratorB) {

        int pairsMatch = 0;
        long previousResultGeneratorA = seedGeneratorA;
        long previousResultGeneratorB = seedGeneratorB;

        for (int i = 0; i < 40_000_000; i++) {
            long resultGeneratorA = part1GeneratorA(previousResultGeneratorA);
            long resultGeneratorB = part1GeneratorB(previousResultGeneratorB);

            if (judgeResult2(resultGeneratorA, resultGeneratorB)) {
                pairsMatch++;
            }

            previousResultGeneratorA = resultGeneratorA;
            previousResultGeneratorB = resultGeneratorB;
        }

        return pairsMatch;
    }

    /**
     *
     */
    protected int deelTweeA(int seedGeneratorA, int seedGeneratorB) {
        int pairsMatch = 0;
        long previousResultGeneratorA = seedGeneratorA;
        long previousResultGeneratorB = seedGeneratorB;
        List<Long> resultGeneratorAToJudge = new ArrayList<>();
        List<Long> resultGeneratorBToJudge = new ArrayList<>();

        // First make a long list of numbers to compare
        while (resultGeneratorAToJudge.size() < 5_000_000 || resultGeneratorBToJudge.size() < 5_000_000) {
            long resultGeneratorA = part1GeneratorA(previousResultGeneratorA);
            long resultGeneratorB = part1GeneratorB(previousResultGeneratorB);

            // if the result is dividable by 4, we should queue it to judge
            if (resultGeneratorA % 4 == 0) {
                resultGeneratorAToJudge.add(resultGeneratorA);
            }

            // if the result is dividable by 8, we should queue to judge it
            if (resultGeneratorB % 8 == 0) {
                resultGeneratorBToJudge.add(resultGeneratorB);
            }

            previousResultGeneratorA = resultGeneratorA;
            previousResultGeneratorB = resultGeneratorB;
        }

        int sizeA = resultGeneratorAToJudge.size();
        int sizeB = resultGeneratorBToJudge.size();
        int minSize = Math.min(sizeA, sizeB);

        for (int i = 0; i < minSize; i++) {
            if (judgeResult2(resultGeneratorAToJudge.get(i), resultGeneratorBToJudge.get(i))) {
                pairsMatch++;
            }
        }

        return pairsMatch;
    }

    /**
     * Generates a number based on a previous number.
     */
    private long part1GeneratorA(long previousNumber) {
        return (previousNumber * 16807) % 2147483647;
    }

    /**
     * Generates a number based on a previous number.
     */
    private long part1GeneratorB(long previousNumber) {
        return (previousNumber * 48271) % 2147483647;
    }

    /**
     * Returns true if the lowest 16 bits of both numbers match. Since in a short only 16 bits fit, we can downcast the
     * long value to short, which automatically only returns the first 16 bits.
     */
    private boolean judgeResult(long resultGeneratorA, long resultGeneratorB) {
        short lowest16BitsA = (short) resultGeneratorA;
        short lowest16BitsB = (short) resultGeneratorB;
        return lowest16BitsA == lowest16BitsB;
    }

    /**
     * As an alternative for {@link #judgeResult), a method which can be generalized to any number of bits.
     * The mask removes any bits which are not the 16 lowest bits. The mask makes them zero.
     */
    private boolean judgeResult2(long resultGeneratorA, long resultGeneratorB) {
        int mask = 0xFFFF;

        long lowest16BitsA = resultGeneratorA & mask;
        long lowest16BitsB = resultGeneratorB & mask;
        return lowest16BitsA == lowest16BitsB;
    }
}
