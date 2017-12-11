package com.benjamin;

import com.benjamin.objects.HexCoordinates;
import com.benjamin.repositories.InputRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 {

    private InputRepository inputRepository = new InputRepository();
    private static final HexCoordinates ORIGIN = HexCoordinates.of(0, 0, 0);

    public static void main(String[] args) {

        Day11 instance = new Day11();
        String input = instance.inputRepository.getInput(11);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    /**
     * Returns the fewest number of steps to reach some coordinate after walking to it.
     */
    protected int deelEenA(final String input) {
        final HexCoordinates destination = stringToIntegers(input).stream()
                .reduce(ORIGIN, (coords, direction) -> takeStep(direction, coords), HexCoordinates::add);

        return HexCoordinates.distance(ORIGIN, destination);
    }

    private HexCoordinates takeStep(Direction direction, HexCoordinates currentPosition) {
        switch (direction) {
            case NORTH:
                return HexCoordinates.north(currentPosition);
            case NORTH_EAST:
                return HexCoordinates.northEast(currentPosition);
            case SOUTH_EAST:
                return HexCoordinates.southEast(currentPosition);
            case SOUTH:
                return HexCoordinates.south(currentPosition);
            case SOUTH_WEST:
                return HexCoordinates.southWest(currentPosition);
            case NORTH_WEST:
                return HexCoordinates.northWest(currentPosition);
            default:
                throw new IllegalStateException("Unknown direction " + direction);
        }
    }

    /**
     * Returns the maximum number of steps you get away from the starting point during a walk.
     */
    protected int deelTweeA(final String input) {

        final List<Direction> directions = stringToIntegers(input);
        HexCoordinates currentCoordinates = ORIGIN;
        int maxDistance = 0;

        for (Direction direction : directions) {
            currentCoordinates = takeStep(direction, currentCoordinates);
            int currentDistance = HexCoordinates.distance(ORIGIN, currentCoordinates);

            maxDistance = currentDistance > maxDistance
                    ? currentDistance
                    : maxDistance;
        }

        return maxDistance;
    }

    /**
     * Turn a string into a list of directions.
     */
    private List<Direction> stringToIntegers(String input) {
        return Arrays.stream(input.split(","))
                .map(Direction::fromString)
                .collect(Collectors.toList());
    }

    private enum Direction {

        NORTH,
        NORTH_EAST,
        SOUTH_EAST,
        SOUTH,
        SOUTH_WEST,
        NORTH_WEST;

        public static Direction fromString(String input) {

            switch (input) {
                case "n":
                    return NORTH;
                case "ne":
                    return NORTH_EAST;
                case "se":
                    return SOUTH_EAST;
                case "s":
                    return SOUTH;
                case "sw":
                    return SOUTH_WEST;
                case "nw":
                    return NORTH_WEST;
                default:
                    throw new IllegalStateException("Unknown direction " + input);
            }
        }
    }
}
