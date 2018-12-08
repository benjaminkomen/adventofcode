package com.benjamin;

public class Day13 extends Day {

    public static void main(String[] args) {

        Day13 instance = new Day13();
        String input = instance.inputRepository.getInput(13);

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
