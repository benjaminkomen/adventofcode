package com.benjamin;

import com.benjamin.objects.Coordinates;

import java.util.stream.Stream;

public class Day3 {

    private static final int INPUT_DEEL_EEN = 312051;
    private static final Coordinates ORIGIN = new Coordinates(0, 0);

    public static void main(String[] args) {

        Day3 instance = new Day3();

        System.out.println("The answer to part 1 is: " + instance.deelEenA(INPUT_DEEL_EEN));
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
     * Returns the Coordinates of a given memory location, in a spiral pattern grid.
     */
    private Coordinates getCoordinates(Integer memoryLocation) {
        int stepsToTake = memoryLocation - 1; // because memoryLocation 1 takes only 0 steps we need to subtract one
        int spriralStepsToTake = 1; // we start by going 1 step east and north, next loop 2 steps west and south etc.
        int x = 0;
        int y = 0;
        Direction direction = Direction.EAST;

        for (int stepsTaken = 0; stepsTaken < stepsToTake;) {

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

        return new Coordinates(x, y);
    }

    /**
     * Returns the Manhattan Distance from the origin located at (x,y) = (0,0).
     */
    private int getManhattanDistance(Coordinates coordinates) {
        return Math.abs(coordinates.getX() - ORIGIN.getX()) +
                Math.abs(coordinates.getY() - ORIGIN.getY());
    }

    /**
     *
     */
    protected int deelTweeA() {
        return 0;
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
