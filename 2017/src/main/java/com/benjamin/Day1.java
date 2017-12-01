package com.benjamin;

import com.benjamin.repositories.InputRepository;

import java.util.stream.Stream;

public class Day1 {

    private InputRepository inputRepository = new InputRepository();

    public static void main(String[] args) {

        Day1 instance = new Day1();
        String input = instance.inputRepository.getInput();

        System.out.println(instance.bereken(input));
    }

    protected int bereken(String input) {
        return Stream.of(input)
                .mapToInt(Integer::new)
                .sum();
    }
}
