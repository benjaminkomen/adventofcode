package com.benjamin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7 extends Day {

    private List<Program> programs = new ArrayList<>();

    public static void main(String[] args) {

        Day7 instance = new Day7();
        String input = instance.inputRepository.getInput(7);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    /**
     * Returns the name of the bottom program, which has no parents.
     */
    protected String deelEenA(final String input) {
        List<String> listOfStrings = stringToStringList(input);

        for (String string : listOfStrings) {
            final Program programToAdd = stringToProgram(string);

            // if it didn't exist yet, add it
            if (getProgram(programToAdd.getName()) == null) {
                programs.add(programToAdd);
            } else {
                // it already existed, but probably without known weight, add this
                Program existingProgram = getProgram(programToAdd.getName());
                existingProgram.weight = programToAdd.getWeight();
            }
        }

        return programs.stream()
                .filter(p -> p.getParentName() == null)
                .map(Program::getName)
                .findFirst()
                .orElse("");
    }

    /**
     * Returns the correct weight of the unbalanced tower.
     */
    protected int deelTweeA(final String input) {
        programs = new ArrayList<>(); // clear previous solution
        deelEenA(input);

        // add children to all programs
        for (Program program : programs) {
            List<Program> children = programs.stream()
                    .filter(p -> program.getName().equals(p.getParentName()))
                    .collect(Collectors.toList());

            program.getChildren().addAll(children);
        }

        // we except to find one unbalanced Program, but actually, if one program is unbalanced,
        // this has influence on its parents and children as well
        final List<Program> unbalancedPrograms = programs.stream()
                .filter(p -> !p.isDiscBalanced())
                .collect(Collectors.toList());

        if (unbalancedPrograms.size() > 1) {
            // we can be fairly sure that the deepest child program is the faulty one,
            // its fault propagates further to its parents
            // @todo it might be coincidence that the last element of this list is also the deepest nested program
            // we should keep track of the nesting level and select the program with the heighest nesting level here
            return unbalancedPrograms.get(unbalancedPrograms.size() - 1).weightNeededToBalanceDisc();
        } else {
            return unbalancedPrograms.get(0).weightNeededToBalanceDisc();
        }
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

        if (subProgramNames.length > 0) {
            updateParentsOfPrograms(subProgramNames, name);
        }

        return new Program(name, weight, null, new ArrayList<>());
    }

    /**
     * Find the given children in the programs list and add them if they don't exist yet.
     * <p>
     * Add the parentName name to these children.
     */
    private void updateParentsOfPrograms(String[] children, String parentName) {

        for (String child : children) {
            if (getProgram(child) == null) {
                programs.add(new Program(child, null, parentName, new ArrayList<>()));
            } else {
                Program existingProgram = getProgram(child);
                existingProgram.parentName = parentName;
            }
        }
    }

    /**
     * Returns the program in the list of programs
     */
    private Program getProgram(String programName) {
        return programs.stream()
                .filter(p -> programName.equals(p.getName()))
                .findAny()
                .orElse(null);
    }

    /**
     * Returns the program in the list of programs
     */
    private Program getProgram(Integer weight) {
        return programs.stream()
                .filter(p -> weight.equals(p.getWeightOfProgramAndChildren()))
                .findAny()
                .orElse(null);
    }

    /**
     * Returns the weight of the current program, plus the weight of its children, plus the weight of their children etc etc.
     */
    public static Integer getWeightOfProgramAndChildren(Program program) {
        if (program.getChildren().isEmpty()) {
            return program.getWeight();
        } else {
            Integer weightToReturn = program.getWeight();
            for (Program child : program.getChildren()) {
                weightToReturn = weightToReturn + getWeightOfProgramAndChildren(child);
            }
            return weightToReturn;
        }
    }

    private class Program {

        private String name;
        private Integer weight;
        private String parentName;
        private List<Program> children;

        private Program() {
            // no-args constructor
        }

        public Program(String name, Integer weight, String parentName, List<Program> children) {
            this.name = name;
            this.weight = weight;
            this.parentName = parentName;
            this.children = children;
        }

        public String getName() {
            return name;
        }

        public Integer getWeight() {
            return weight;
        }

        public String getParentName() {
            return parentName;
        }

        public List<Program> getChildren() {
            return children;
        }

        /**
         * Returns weight of the direct children of a program. Does not cascade further down.
         */
        public Integer getWeightOfChildren() {
            return children.stream()
                    .map(Program::getWeight)
                    .mapToInt(i -> i)
                    .sum();
        }

        public Integer getWeightOfProgramAndChildren() {
            return Day7.getWeightOfProgramAndChildren(this);
        }

        public boolean isDiscBalanced() {
            int weightOfFirstChild = children.stream()
                    .map(Day7::getWeightOfProgramAndChildren)
                    .findFirst()
                    .orElse(0);

            return children.stream()
                    .allMatch(p -> weightOfFirstChild == Day7.getWeightOfProgramAndChildren(p));
        }

        public int weightNeededToBalanceDisc() {
            List<Integer> weightsOfChildren = children.stream()
                    .map(Day7::getWeightOfProgramAndChildren)
                    .collect(Collectors.toList());

            final Integer wrongWeight = weightsOfChildren.stream()
                    .reduce(0, (w1, w2) -> Math.abs(w1 - w2));

            int correction = 0;

            for (Integer weight : weightsOfChildren) {

                if (weight - wrongWeight != 0) {
                    correction = weight - wrongWeight;
                    break;
                }
            }

            int correctedWeight = getProgram(wrongWeight).getWeight() + correction;

            // double check by correcting weight
            getProgram(wrongWeight).weight = correctedWeight;

            boolean doubleCheck = programs.stream()
                    .noneMatch(p -> !p.isDiscBalanced());

            if (!doubleCheck) {
                throw new IllegalStateException("The disc is not balanced yet!");
            }

            return correctedWeight;
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
