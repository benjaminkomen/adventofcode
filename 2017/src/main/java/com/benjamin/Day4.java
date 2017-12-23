package com.benjamin;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 extends Day {

    public static void main(String[] args) {

        Day4 instance = new Day4();
        String input = instance.inputRepository.getInput(4);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    /**
     * Returns the amount of valid passphrases, without duplicate strings.
     */
    protected long deelEenA(final String input) {
        return stringToWordLists(input).stream()
                .filter(list -> list.size() == new HashSet<>(list).size())
                .count();
    }

    /**
     * Returns the amount of valid passphrases, without any anagrams.
     */
    protected long deelTweeA(final String input) {
        return stringToWordLists(input).stream()
                .filter(list -> countAnagrams(list) == 0)
                .count();
    }

    /**
     * Turn a string into a list of lists of strings.
     */
    private List<List<String>> stringToWordLists(String input) {
        return Arrays.stream(input.split("\n"))
                .map(line -> line.split(" "))
                .map(array -> Arrays.stream(array)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    /**
     * Returns the amount of anagrams in the given list.
     */
    private long countAnagrams(List<String> list) {
        if (tail(list).isEmpty()) {
            return 0;
        } else {
            return tail(list).stream()
                    .filter(s -> isAnagram(head(list), s))
                    .count()
                    + countAnagrams(tail(list));
        }
    }

    /**
     * Returns true if the first string is an anagram of the second string, false otherwise.
     */
    protected boolean isAnagram(String first, String second) {
        char[] charactersOfFirstString = first.toCharArray();
        char[] charactersOfSecondString = second.toCharArray();

        Arrays.sort(charactersOfFirstString);
        Arrays.sort(charactersOfSecondString);

        return Arrays.equals(charactersOfFirstString, charactersOfSecondString);
    }

    private static <T> T head(@NotNull List<T> list) {
        return list.get(0);
    }

    private static <T> List<T> tail(@NotNull List<T> list) {
        return list.subList(1, list.size());
    }
}
