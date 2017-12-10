package com.benjamin;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(INPUT, null));
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
     * Returns the Knot Hash from an input string.
     */
    protected String deelTweeA(final String input, List<Integer> list) {

        if (list == null) {
            list = Stream.iterate(0, n -> n + 1)
                    .limit(256)
                    .collect(Collectors.toList());
        }

        int currentPosition = 0;
        int skipSize = 0;
        int counter = 0;

        List<Byte> lengths = stringToBytes(input);
        lengths.addAll(List.of(b(17), b(31), b(73), b(47), b(23)));

        while (counter < 64) {
            counter++;
            for (Byte length : lengths) {

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
        }

        List<Integer> denseHash = convertSparseHashToDenseHash(list);

        return convertToHex(denseHash);
    }

    /**
     * Turn a string into a list of integers.
     */
    private List<Integer> stringToIntegers(String input) {
        return Arrays.stream(input.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    /**
     * Turn a string into a list of bytes.
     */
    protected List<Byte> stringToBytes(String input) {
        List<Byte> result = new ArrayList<>();
        byte[] bytes = input.getBytes(StandardCharsets.US_ASCII);

        for (byte b : bytes) {
            result.add(b);
        }

        return result;
    }

    /**
     * cast an int to a byte
     */
    private byte b(int i) {
        return (byte) i;
    }

    /**
     * Convert a sparse hash of 256 numbers to a dense hash of 16 numbers.
     */
    private List<Integer> convertSparseHashToDenseHash(List<Integer> list) {
        return new ArrayList<>(list.stream()
                .collect(Collectors.groupingBy(el -> list.indexOf(el) / 16))
                .values())
                .stream()
                .map(this::applyXOR)
                .collect(Collectors.toList());
    }

    /**
     * Return a single number which is the XOR result of a list of given numbers.
     */
    protected Integer applyXOR(List<Integer> list) {
        return list.stream()
                .reduce(0, (a, b) -> a ^ b);
    }

    /**
     * Convert a list of integers to a hexadecimal represented string.
     */
    protected String convertToHex(List<Integer> list) {
        return list.stream()
                .map(s -> String.format("%02x", s)) // note that Integer::toHexString won't work because it leaves out leading zeroes.
                .collect(Collectors.joining());
    }
}
