package com.benjamin.objects;

public class Coordinates {

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private Coordinates() {
        // no-args constructor
    }

    private Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinates west(Coordinates coordinates) {
        return Coordinates.of(coordinates.getX() - 1, coordinates.getY());
    }

    public static Coordinates northWest(Coordinates coordinates) {
        return Coordinates.of(coordinates.getX() - 1, coordinates.getY() + 1);
    }

    public static Coordinates north(Coordinates coordinates) {
        return Coordinates.of(coordinates.getX(), coordinates.getY() + 1);
    }

    public static Coordinates northEast(Coordinates coordinates) {
        return Coordinates.of(coordinates.getX() + 1, coordinates.getY() + 1);
    }

    public static Coordinates east(Coordinates coordinates) {
        return Coordinates.of(coordinates.getX() + 1, coordinates.getY());
    }

    public static Coordinates southEast(Coordinates coordinates) {
        return Coordinates.of(coordinates.getX() + 1, coordinates.getY() - 1);
    }

    public static Coordinates south(Coordinates coordinates) {
        return Coordinates.of(coordinates.getX(), coordinates.getY() - 1);
    }

    public static Coordinates southWest(Coordinates coordinates) {
        return Coordinates.of(coordinates.getX() - 1, coordinates.getY() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static Coordinates of(int x, int y) {
        return new Coordinates(x,y);
    }
}
