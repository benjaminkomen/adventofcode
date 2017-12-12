package com.benjamin;

import com.benjamin.repositories.InputRepository;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day12 {

    private InputRepository inputRepository = new InputRepository();
    private List<Day12.Program> programs = new ArrayList<>();

    public static void main(String[] args) {

        Day12 instance = new Day12();
        String input = instance.inputRepository.getInput(12);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    /**
     *
     */
    protected int deelEenA(final String input) {
        List<String> listOfStrings = stringToStringList(input);

        for (String string : listOfStrings) {
            final Program programToAdd = stringToProgram(string);

            // if it didn't exist yet, add it
            Program possiblyExistingProgramToAdd = getProgram(programToAdd.getId());
            if (possiblyExistingProgramToAdd == null) {
                programs.add(programToAdd);
            } else {
                // if it already existed, we should update its connectedProgramIds
                for (Integer connId : programToAdd.getConnectedProgramIds()) {
                    possiblyExistingProgramToAdd.addConnectedProgramId(connId);
                }
            }
        }

        Program rootProgram = getProgram(0);
        if (rootProgram == null) {
            return 0;
        } else {
            return calculateAmountOfConnectedPrograms(rootProgram);
        }
    }

    /**
     *
     */
    protected int deelTweeA(final String input) {
        assert (!programs.isEmpty());

        programs = programs.stream().sorted().collect(Collectors.toList());

        List<List<Program>> groups = new ArrayList<>();

        for (Program program : programs) {
            List<Program> group = calculateGroupOfConnectedPrograms(program);

            if (!groups.contains(group)) {
                groups.add(group);
            }
        }

        return groups.size();
    }

    /**
     * Turn a string into a list strings
     */
    private List<String> stringToStringList(String input) {
        return Arrays.stream(input.split("\n"))
                .collect(Collectors.toList());
    }

    private Program stringToProgram(String string) {
        Pattern pattern1 = Pattern.compile("^(\\d+) <-> (.*?)$");
        Matcher matcher1 = pattern1.matcher(string);

        Integer id = matcher1.matches()
                ? Integer.valueOf(matcher1.group(1))
                : null;

        int[] connectedProgramIdsArray = matcher1.matches()
                ? Arrays.stream(matcher1.group(2).split(", ")).map(Integer::valueOf).mapToInt(i -> i).toArray()
                : new int[0];

        if (connectedProgramIdsArray.length > 0) {
            updateConnectedPrograms(connectedProgramIdsArray, id);
        }

        List<Integer> connectedProgramIdsList = Arrays.stream(connectedProgramIdsArray).boxed().collect(Collectors.toList());

        return new Program(id, connectedProgramIdsList);
    }

    private void updateConnectedPrograms(int[] connecteds, Integer id) {

        for (Integer connectedId : connecteds) {

            Program existingProgram = getProgram(connectedId);

            if (existingProgram == null) {
                programs.add(new Program(connectedId, new ArrayList<>(List.of(id))));
            } else {
                existingProgram.addConnectedProgramId(id);
            }
        }
    }

    /**
     * Returns the program in the list of programs
     */
    private Program getProgram(Integer programId) {
        return programs.stream()
                .filter(p -> programId.equals(p.getId()))
                .findAny()
                .orElse(null);
    }

    /**
     * A breadth-first algorithm.
     * See: https://www.redblobgames.com/pathfinding/tower-defense/implementation.html
     */
    private Integer calculateAmountOfConnectedPrograms(Program program) {
        Set<Program> visitedPrograms = new HashSet<>();
        ArrayDeque<Program> frontier = new ArrayDeque<>();
        frontier.push(program);
        program.setVisited(true);
        visitedPrograms.add(program);

        while (!frontier.isEmpty()) {
            Program currentProgram = frontier.pop();

            for (Integer nextProgramId : currentProgram.getConnectedProgramIds()) {
                Program nextProgram = getProgram(nextProgramId);

                if (nextProgram != null && !nextProgram.isVisited()) {
                    nextProgram.setVisited(true);
                    visitedPrograms.add(nextProgram);
                    frontier.push(nextProgram);
                }
            }
        }

        programs.stream().forEach(p -> p.setVisited(false));

        return visitedPrograms.size();
    }

    private List<Program> calculateGroupOfConnectedPrograms(Program program) {
        List<Program> visitedPrograms = new ArrayList<>();
        ArrayDeque<Program> frontier = new ArrayDeque<>();
        frontier.push(program);
        program.setVisited(true);
        visitedPrograms.add(program);

        while (!frontier.isEmpty()) {
            Program currentProgram = frontier.pop();

            for (Integer nextProgramId : currentProgram.getConnectedProgramIds()) {
                Program nextProgram = getProgram(nextProgramId);

                if (nextProgram != null && !nextProgram.isVisited()) {
                    nextProgram.setVisited(true);
                    visitedPrograms.add(nextProgram);
                    frontier.push(nextProgram);
                }
            }
        }

        visitedPrograms = visitedPrograms.stream().sorted().collect(Collectors.toList());

        programs.stream().forEach(p -> p.setVisited(false));

        return visitedPrograms;
    }

    private class Program implements Comparable<Program> {

        private Integer id;
        private boolean visited;
        private List<Integer> connectedProgramIds;

        private Program() {
            // no-args constructor
        }

        public Program(Integer id, List<Integer> connectedProgramIds) {
            this.id = id;
            this.connectedProgramIds = connectedProgramIds;
        }

        public Integer getId() {
            return id;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public List<Integer> getConnectedProgramIds() {
            return connectedProgramIds;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Program program = (Program) o;
            return Objects.equals(id, program.id);
        }

        @Override
        public int hashCode() {

            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "Program{" +
                    "id=" + id +
                    ", connectedProgramIds=" + connectedProgramIds +
                    '}';
        }

        /**
         * Add the given id to the list of connectedProgramIds, unless it is already present.
         */
        public void addConnectedProgramId(Integer id) {
            if (!containsConnectedProgramId(id)) {
                this.connectedProgramIds.add(id);
            }
        }

        /**
         * True if the list of connectedProgramIds contains the given id, else false.
         */
        private boolean containsConnectedProgramId(Integer id) {
            return getConnectedProgramIds().stream()
                    .anyMatch(e -> e.equals(id));
        }

        @Override
        public int compareTo(@NotNull Program other) {
            return this.getId() - other.getId();
        }
    }
}
