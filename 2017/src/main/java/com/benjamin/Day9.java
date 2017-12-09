package com.benjamin;

import com.benjamin.repositories.InputRepository;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 {

    private InputRepository inputRepository = new InputRepository();
    private List<Group> groups = new ArrayList<>();

    public static void main(String[] args) {

        Day9 instance = new Day9();
        String input = instance.inputRepository.getInput(9);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    /**
     * Returns the total score of all the groups in the input.
     */
    protected int deelEenA(final String input) {
        final List<String> characters = stringToStrings(input);

        int counterOpeningCurlyBrackets = 0;
        int nestingDepth = 0;
        boolean garbageMode = false; // we are reading garbage, ignore normal input
        boolean cancelMode = false; // we are canceling any next character
        int cancelModeCounter = 0; // how many times has cancelMode been on? It only goes for one loop!

        for (String character : characters) {

            // increase the counter every loop the cancelMode is on
            if (cancelMode) {
                cancelModeCounter++;
            }

            // close cancelMode if it's done, reset its parameters.
            if (cancelModeCounter == 2) {
                cancelMode = false;
                cancelModeCounter = 0;
            }

            if ("{".equals(character) && !garbageMode && !cancelMode) {
                counterOpeningCurlyBrackets++;
                nestingDepth++;
                groups.add(new Group(nestingDepth, determineScore(nestingDepth)));
            }

            if ("}".equals(character) && !garbageMode && !cancelMode) {
                counterOpeningCurlyBrackets--;
                nestingDepth--;
            }

            if ("<".equals(character) && !garbageMode && !cancelMode) {
                garbageMode = true;
            }

            if (">".equals(character) && !cancelMode) {
                garbageMode = false;
            }

            if ("!".equals(character) && !cancelMode) {
                cancelMode = true;
            }
        }

        if (counterOpeningCurlyBrackets != 0) {
            throw new IllegalStateException(String.format("The brackets are unbalanced, still %s unclosed " +
                    "curly brackets!", counterOpeningCurlyBrackets));
        }

        // return the sum of scores for all groups
        return groups.stream()
                .map(Group::getScore)
                .mapToInt(i -> i)
                .sum();
    }

    /**
     * Returns the length of removed garbage.
     */
    protected int deelTweeA(final String input) {
        groups = new ArrayList<>(); // erase input from part 1
        final List<String> characters = stringToStrings(input);

        int counterOpeningCurlyBrackets = 0;
        int nestingDepth = 0;
        boolean garbageMode = false; // we are reading garbage, ignore normal input
        boolean cancelMode = false; // we are canceling any next character
        int cancelModeCounter = 0; // how many times has cancelMode been on? It only goes for one loop!
        int removedGarbageCharacters = 0;

        for (String character : characters) {

            // increase the counter every loop the cancelMode is on
            if (cancelMode) {
                cancelModeCounter++;
            }

            // close cancelMode if it's done, reset its parameters.
            if (cancelModeCounter == 2) {
                cancelMode = false;
                cancelModeCounter = 0;
            }

            if ("{".equals(character) && !garbageMode && !cancelMode) {
                counterOpeningCurlyBrackets++;
                nestingDepth++;
                groups.add(new Group(nestingDepth, determineScore(nestingDepth)));
            } else if ("}".equals(character) && !garbageMode && !cancelMode) {
                counterOpeningCurlyBrackets--;
                nestingDepth--;
            } else if ("<".equals(character) && !garbageMode && !cancelMode) {
                garbageMode = true;
            } else if (">".equals(character) && !cancelMode) {
                garbageMode = false;
            } else if ("!".equals(character) && !cancelMode) {
                cancelMode = true;
            } else if (garbageMode && !cancelMode) {
                removedGarbageCharacters++;
            }
        }

        if (counterOpeningCurlyBrackets != 0) {
            throw new IllegalStateException(String.format("The brackets are unbalanced, still %s unclosed " +
                    "curly brackets!", counterOpeningCurlyBrackets));
        }

        return removedGarbageCharacters;
    }

    /**
     * Returns the score of the group with one nestingDepth lower.
     */
    private Integer determineScore(int nestingDepth) {
        return groups.stream()
                .filter(g -> g.getNestingDepth().equals(nestingDepth - 1))
                .map(Group::getScore)
                .map(score -> score + 1)
                .findFirst()
                .orElse(1);
    }

    /**
     * Turn a string into a list of strings.
     */
    private List<String> stringToStrings(String input) {
        return Arrays.stream(input.split(""))
                .collect(Collectors.toList());
    }

    private class Group {

        private Integer nestingDepth;
        private Integer score;

        private Group() {
            // no-args constructor
        }

        public Group(Integer nestingDepth, Integer score) {
            this.nestingDepth = nestingDepth;
            this.score = score;
        }

        public Integer getNestingDepth() {
            return nestingDepth;
        }

        public Integer getScore() {
            return score;
        }
    }
}
