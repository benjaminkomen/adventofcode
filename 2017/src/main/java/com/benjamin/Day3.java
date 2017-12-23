package com.benjamin;

import com.benjamin.objects.Coordinates;
import com.benjamin.objects.Memory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day3 extends Day {

    private static final int INPUT = 312051;
    private static final Coordinates ORIGIN = Coordinates.of(0, 0);

    public static void main(String[] args) {

        Day3 instance = new Day3();

        System.out.println("The answer to part 1 is: " + instance.deelEenA(INPUT));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA());
    }

    /**
     * Compute the Manhattan Distance, from a memory location in a specific grid.
     */
    protected int deelEenA(int input) {
        return Stream.of(input)
                .map(this::getCoordinates)
                .map(this::getManhattanDistance)
                .findFirst()
                .orElse(0);
    }

    /**
     *
     */
    protected int deelTweeA() {
        List<Memory> memories = new ArrayList<>();
        int currentMemoryValue = 1;
        memories.add(Memory.of(1, getCoordinates(1), currentMemoryValue)); // initial condition
        int id = 2;

        do {
            final Coordinates coordinates = getCoordinates(id);
            currentMemoryValue = computeCurrentMemoryValue(memories, coordinates);
            memories.add(Memory.of(id, coordinates, currentMemoryValue));
            id++;
        } while (currentMemoryValue <= INPUT);

        return currentMemoryValue;
    }

    private int computeCurrentMemoryValue(List<Memory> memories, Coordinates coordinates) {
        int valueWest = memories.stream()
                .filter(m -> m.getCoordinates().equals(Coordinates.west(coordinates)))
                .map(Memory::getValue)
                .findFirst()
                .orElse(0);

        int valueNorthWest = memories.stream()
                .filter(m -> m.getCoordinates().equals(Coordinates.northWest(coordinates)))
                .map(Memory::getValue)
                .findFirst()
                .orElse(0);

        int valueNorth = memories.stream()
                .filter(m -> m.getCoordinates().equals(Coordinates.north(coordinates)))
                .map(Memory::getValue)
                .findFirst()
                .orElse(0);

        int valueNorthEast = memories.stream()
                .filter(m -> m.getCoordinates().equals(Coordinates.northEast(coordinates)))
                .map(Memory::getValue)
                .findFirst()
                .orElse(0);

        int valueEast = memories.stream()
                .filter(m -> m.getCoordinates().equals(Coordinates.east(coordinates)))
                .map(Memory::getValue)
                .findFirst()
                .orElse(0);

        int valueSouthEast = memories.stream()
                .filter(m -> m.getCoordinates().equals(Coordinates.southEast(coordinates)))
                .map(Memory::getValue)
                .findFirst()
                .orElse(0);

        int valueSouth = memories.stream()
                .filter(m -> m.getCoordinates().equals(Coordinates.south(coordinates)))
                .map(Memory::getValue)
                .findFirst()
                .orElse(0);

        int valueSouthWest = memories.stream()
                .filter(m -> m.getCoordinates().equals(Coordinates.southWest(coordinates)))
                .map(Memory::getValue)
                .findFirst()
                .orElse(0);

        return valueWest + valueNorthWest + valueNorth + valueNorthEast +
                valueEast + valueSouthEast + valueSouth + valueSouthWest;
    }

    /**
     * Returns the Coordinates of a given memory location, in a spiral pattern grid.
     */
    private Coordinates getCoordinates(Integer memoryLocation) {
        int stepsToTake = memoryLocation - 1; // because memoryLocation 1 takes only 0 steps we need to subtract one
        int spriralStepsToTake = 1; // we start by going 1 step east and north, next loop 2 steps west and south etc.
        int x = 0;
        int y = 0;
        Direction direction = Direction.EAST;

        for (int stepsTaken = 0; stepsTaken < stepsToTake; ) {

            // walk a given amount of spiralSteps in the x-direction
            for (int spiralStepsTaken = 0; spiralStepsTaken < spriralStepsToTake && stepsTaken < stepsToTake; stepsTaken++, spiralStepsTaken++) {
                x = moveXCoordinate(x, direction);
            }

            // update the direction for the next set of steps
            direction = Direction.transition(direction);

            // walk a given amount of spiralSteps in the y-direction
            for (int spiralStepsTaken = 0; spiralStepsTaken < spriralStepsToTake && stepsTaken < stepsToTake; stepsTaken++, spiralStepsTaken++) {
                y = moveYCoordinate(y, direction);
            }

            // update the direction for the next set of steps
            direction = Direction.transition(direction);

            spriralStepsToTake++;
        }

        return Coordinates.of(x, y);
    }

    /**
     * Returns the Manhattan Distance from the origin located at (x,y) = (0,0).
     */
    private int getManhattanDistance(Coordinates coordinates) {
        return Math.abs(coordinates.getX() - ORIGIN.getX()) +
                Math.abs(coordinates.getY() - ORIGIN.getY());
    }

    private int moveXCoordinate(int x, Direction direction) {
        return direction == Direction.EAST
                ? ++x
                : direction == Direction.WEST
                ? --x
                : x;
    }

    private int moveYCoordinate(int y, Direction direction) {
        return direction == Direction.NORTH
                ? ++y
                : direction == Direction.SOUTH
                ? --y
                : y;
    }

    private enum Direction {

        NORTH,
        EAST,
        SOUTH,
        WEST;

        /**
         * Given a direction, return what the transition to the next direction is.
         */
        public static Direction transition(Direction direction) {

            switch (direction) {
                case EAST:
                    return NORTH;
                case NORTH:
                    return WEST;
                case WEST:
                    return SOUTH;
                case SOUTH:
                    return EAST;
                default:
                    throw new IllegalStateException("Unknown direction " + direction);
            }
        }
    }
}
