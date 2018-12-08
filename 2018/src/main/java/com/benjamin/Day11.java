package com.benjamin;

public class Day11 extends Day {

    public static void main(String[] args) {

        Day11 instance = new Day11();
        String input = instance.inputRepository.getInput(11);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    protected int deelEenA(final String input) {
        return 0;
    }

    protected int deelTweeA(final String input) {
        return 0;
    }
}
