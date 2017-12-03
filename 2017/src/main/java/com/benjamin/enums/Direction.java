package com.benjamin.enums;

public enum Direction {

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
