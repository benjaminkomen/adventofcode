package com.benjamin;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day6 {

    private static final String INPUT = "10\t3\t15\t10\t5\t15\t5\t15\t9\t2\t5\t8\t5\t2\t3\t6";

    public static void main(String[] args) {

        Day6 instance = new Day6();

        System.out.println("The answer to part 1 is: " + instance.deelEenA(INPUT));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(INPUT));
    }

    /**
     * Returns the amount of times it takes to find the same memory state again.
     */
    protected int deelEenA(final String input) {
        List<Integer> initialMemoryState = stringToIntegerArray(input);
        List<List<Integer>> memoryStates = new ArrayList<>();
        memoryStates.add(initialMemoryState);

        while (true) {
            List<Integer> newMemoryState = computeNewMemoryState(getLastElementOfList(memoryStates));

            if (memoryStates.contains(newMemoryState)) {
                return memoryStates.size();
            } else {
                memoryStates.add(newMemoryState);
            }
        }
    }

    /**
     * I don't understand the question??
     */
    protected int deelTweeA(final String input) {
        List<Integer> initialMemoryState = stringToIntegerArray(input);
        List<List<Integer>> memoryStates = new ArrayList<>();
        memoryStates.add(initialMemoryState);
        int loopCounter = 0;

        while (true) {
            List<Integer> newMemoryState = computeNewMemoryState(getLastElementOfList(memoryStates));

            if (memoryStates.contains(newMemoryState)) {
                return loopCounter;
            } else {
                memoryStates.add(newMemoryState);
                loopCounter++;
            }
        }
    }

    /**
     * Returns an array of Integers from the input.
     */
    private List<Integer> stringToIntegerArray(String input) {
        return Arrays.stream(input.split("\t"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private List<Integer> computeNewMemoryState(List<Integer> originalList) {
        List<Integer> copiedList = makeCopy(originalList);

        int maxValue = copiedList.stream()
                .mapToInt(i -> i)
                .max()
                .orElseThrow(() -> new IllegalStateException("There is no max value in this list."));

        int indexMaxValue = 0;

        // if the maxValue occurs multiple times, find the first match and set to zero
        for (int i = 0; i < copiedList.size(); i++) {
            if (copiedList.get(i) == maxValue) {
                indexMaxValue = i;
                copiedList.set(i, 0);
                break;
            }
        }

        int currentIndex = indexMaxValue + 1; // start with redistributing one position further than the previous maxValue

        // redestribute the maxValue over the elements of the list, start adding
        while (maxValue > 0) {

            // prevent array index out of bounds exception
            if (currentIndex == copiedList.size()) {
                currentIndex = 0;
            }

            final Integer currentElementValue = copiedList.get(currentIndex);
            copiedList.set(currentIndex, currentElementValue + 1);
            maxValue--;
            currentIndex++;
        }

        return copiedList;
    }

    private List<Integer> makeCopy(List<Integer> originalList) {
        return originalList.stream()
                .map(Integer::new)
                .collect(Collectors.toList());
    }

    @NotNull
    private List<Integer> getLastElementOfList(List<List<Integer>> memoryStates) {
        return memoryStates.stream()
                .reduce((a, b) -> b)
                .orElseThrow(() -> new IllegalStateException("Could not extract last element of list"));
    }
}
