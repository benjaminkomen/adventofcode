package com.benjamin;

public class Day18 extends Day {

    public static void main(String[] args) {

        Day18 instance = new Day18();
        String input = instance.inputRepository.getInput(18);

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
