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
        return Arrays.stream(input.split("\n"))
                .mapToInt(Integer::parseInt)
                .reduce(0, (a, b) -> a + b);
    }

    protected int deelTweeA(final String input) {
        return 0;
    }
}
