package com.benjamin;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day14 {

    private static final String INPUT = "ljoxqyyw";
    private Day10 day10 = new Day10();

    public static void main(String[] args) {

        Day14 instance = new Day14();

        System.out.println("The answer to part 1 is: " + instance.deelEenA(INPUT));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(INPUT));
    }

    /**
     * Returns the amount of used squares.
     */
    protected long deelEenA(final String input) {
        return determineHashInputs(input).stream()
                .map(day10::makeKnotHash)
                .map(this::convertToBitRepresentation)
                .flatMap(s -> Stream.of(s.split("")))
                .filter(s -> s.equals("1"))
                .mapToInt(Integer::valueOf)
                .count();
    }

    /**
     * Returns the amount of distinct regions.
     */
    protected long deelTweeA(final String input) {

        Map<Integer, Integer> regions = new HashMap<>();

        List<String> lines = determineHashInputs(input).stream()
                .map(day10::makeKnotHash)
                .map(this::convertToBitRepresentation)
                .collect(Collectors.toList());

        int groupCounter = 2; // we start at 2 to distinguish from the zeroes and ones

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            String[] lineArray = line.split("");

            for (int j = 0; j < lineArray.length; j++) {
                String lineElement = lineArray[j];

                if (Integer.valueOf(lineElement) == 1) {

                    if (i == 0 && Arrays.stream(lineArray).noneMatch(s -> s.equals("2"))) {
                        // first group
                        lineArray[j] = String.valueOf(groupCounter);
                    } else {
                        groupCounter++;
                        lineArray[j] = String.valueOf(groupCounter);
                    }
                }
            }
            lines.set(i, Arrays.asList(lineArray).stream().collect(Collectors.joining("")));
        }

        return lines.stream()
                .flatMap(s -> Stream.of(s.split("")))
                .mapToInt(Integer::valueOf)
                .max()
                .orElse(0);
    }

    protected String convertToBitRepresentation(String inputString) {

        int count = 0;
        BigInteger bi = new BigInteger(inputString, 16);
        count += bi.bitCount();

        return Arrays.stream(inputString.toLowerCase().split(""))
                .collect(StringBuilder::new,
                        (sb, s) -> {
                            switch (s) {
                                case "0": sb.append("0000"); break;
                                case "1": sb.append("0001"); break;
                                case "2": sb.append("0010"); break;
                                case "3": sb.append("0011"); break;
                                case "4": sb.append("0100"); break;
                                case "5": sb.append("0101"); break;
                                case "6": sb.append("0110"); break;
                                case "7": sb.append("0111"); break;
                                case "8": sb.append("1000"); break;
                                case "9": sb.append("1001"); break;
                                case "a": sb.append("1010"); break;
                                case "b": sb.append("1011"); break;
                                case "c": sb.append("1100"); break;
                                case "d": sb.append("1101"); break;
                                case "e": sb.append("1110"); break;
                                case "f": sb.append("1111"); break;
                                default: throw new IllegalStateException("Input is not a valid hexadecimal character");
                            }
                        },
                        (sb1, sb2) -> sb1.append(sb2.toString()))
                .toString();
    }

    private List<String> determineHashInputs(final String input) {
        return IntStream.iterate(0, i -> i + 1)
                .limit(128)
                .boxed()
                .map(number -> input + "-" + number)
                .collect(Collectors.toList());
    }
}
