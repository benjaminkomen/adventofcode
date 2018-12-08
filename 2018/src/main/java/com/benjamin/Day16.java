package com.benjamin;

public class Day16 extends Day {

    private static final String SEED = "abcdefghijklmnop";

    public static void main(String[] args) {

        Day16 instance = new Day16();
        String input = instance.inputRepository.getInput(16);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(SEED));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(SEED));
    }

    protected int deelEenA(final String input) {
        return 0;
    }

    protected int deelTweeA(final String input) {
        return 0;
    }
}
