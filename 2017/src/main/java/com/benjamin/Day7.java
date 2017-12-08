package com.benjamin;

import com.benjamin.repositories.InputRepository;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7 {

    private InputRepository inputRepository = new InputRepository();

    public static void main(String[] args) {

        Day7 instance = new Day7();
        String input = instance.inputRepository.getInput(7);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    /**
     *
     */
    protected int deelEenA(final String input) {
        List<String> listOfStrings = stringToStringList(input);
        List<Program> tree = new ArrayList<>();
//        Set<Program> unallocatedPrograms = new HashSet<>();

        final List<Program> programs = listOfStrings.stream()
                .map(this::stringToProgram)
                .collect(Collectors.toList());

        final List<Program> programsWithoutSubprograms = programs.stream()
                .filter(p -> p.getSubPrograms().isEmpty())
                .collect(Collectors.toList());

        final List<Program> programsWithSubprograms = programs.stream()
                .filter(p -> !p.getSubPrograms().isEmpty())
                .collect(Collectors.toList());

        // put program with weight in correct place in programs with subprograms
        programsWithSubprograms.stream()
                .flatMap(p -> p.getSubPrograms().stream())
                .forEach(p -> {
                    programsWithoutSubprograms.stream()
                            .filter(pr -> pr.getName().equals(p.getName()))
                            .findFirst()
                            .orElse(p);
                });

        return tree.size();
    }

    /**
     *
     */
    protected int deelTweeA(final String input) {
        return 0;
    }

    /**
     * Turn a string into a list strings
     */
    private List<String> stringToStringList(String input) {
        return Arrays.stream(input.split("\n"))
                .collect(Collectors.toList());
    }

    private Program stringToProgram(String string) {
        Pattern pattern1 = Pattern.compile("(\\w+)\\s\\((\\d+)\\)");
        Pattern pattern2 = Pattern.compile("^(\\w+)\\s\\((\\d+)\\)[\\s]*->[\\s]*(.*)");
        Matcher matcher1 = pattern1.matcher(string);
        Matcher matcher2 = pattern2.matcher(string);
        String name = matcher2.matches()
                ? matcher2.group(1)
                : matcher1.matches()
                ? matcher1.group(1)
                : null;
        Integer weight = Integer.valueOf(matcher2.matches()
                ? matcher2.group(2)
                : matcher1.matches()
                ? matcher1.group(2)
                : "");

        String[] subProgramNames = matcher2.matches()
                ? matcher2.group(3).split(", ")
                : new String[0];

        List<Program> subPrograms = Arrays.stream(subProgramNames)
                .map(s -> new Program(s, 0, null))
                .collect(Collectors.toList());

        return new Program(name, weight, subPrograms);
    }

    private void addProgramToTree(Program program, List<Program> tree) {
        if (!program.getSubPrograms().isEmpty()) {
            // the first one
            if (tree.isEmpty()) {
                tree.add(program);
                return;
            }
            // now try to add the program at its actual position in the tree
            if (findProgramInTree(program, tree)) {
                putProgramInTree(program, tree);
                return;
            }

            // also try to add the subprograms at their actual position in the tree, higher in hierarchy than the current tree root
            for (Program subProgram : program.getSubPrograms()) {
                if (findProgramInTree(subProgram, tree)) {
                    putProgramInTree(subProgram, tree);
                    return;
                }
            }
        }
    }

    private boolean findProgramInTree(Program programToFind, List<Program> tree) {
        for (Program program : tree) {

            // no subPrograms means we are at a leaf with no further branches
            if (program.getSubPrograms() == null || program.getSubPrograms().isEmpty()) {
                return program.getName().equals(programToFind.getName());
            } else {
                //
                for (Program subProgram : program.getSubPrograms()) {
                    findProgramInTree(subProgram, program.getSubPrograms());
                }
            }
        }
        return false;
    }

    private void putProgramInTree(Program program, List<Program> tree) {

    }

    private class Program {

        private String name;
        private Integer weight;
        private List<Program> subPrograms;

        private Program() {
            // no-args constructor
        }

        public Program(String name, Integer weight, List<Program> subPrograms) {
            this.name = name;
            this.weight = weight;
            this.subPrograms = subPrograms;
        }

        public String getName() {
            return name;
        }

        public Integer getWeight() {
            return weight;
        }

        public List<Program> getSubPrograms() {
            return subPrograms;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Program program = (Program) o;

            return name != null ? name.equals(program.name) : program.name == null;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }
}
