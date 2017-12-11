package com.benjamin.objects;

import java.util.Objects;

public class HexCoordinates {

    private int x;
    private int y;
    private int z;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    private HexCoordinates() {
        // no-args constructor
    }

    private HexCoordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public HexCoordinates add(HexCoordinates other) {
        return HexCoordinates.of(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public static HexCoordinates north(HexCoordinates coordinates) {
        return HexCoordinates.of(coordinates.getX(), coordinates.getY() + 1, coordinates.getZ() - 1);
    }

    public static HexCoordinates northEast(HexCoordinates coordinates) {
        return HexCoordinates.of(coordinates.getX() + 1, coordinates.getY(), coordinates.getZ() - 1);
    }

    public static HexCoordinates southEast(HexCoordinates coordinates) {
        return HexCoordinates.of(coordinates.getX() + 1, coordinates.getY() - 1, coordinates.getZ());
    }

    public static HexCoordinates south(HexCoordinates coordinates) {
        return HexCoordinates.of(coordinates.getX(), coordinates.getY() - 1, coordinates.getZ() + 1);
    }

    public static HexCoordinates southWest(HexCoordinates coordinates) {
        return HexCoordinates.of(coordinates.getX() - 1, coordinates.getY(), coordinates.getZ() + 1);
    }

    public static HexCoordinates northWest(HexCoordinates coordinates) {
        return HexCoordinates.of(coordinates.getX() - 1, coordinates.getY() + 1, coordinates.getZ());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HexCoordinates that = (HexCoordinates) o;
        return x == that.x &&
                y == that.y &&
                z == that.z;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "HexCoordinates{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public static HexCoordinates of(int x, int y, int z) {
        return new HexCoordinates(x,y, z);
    }

    public static int distance(HexCoordinates firstCoordinates, HexCoordinates secondCoordinates) {
        return (
                Math.abs(firstCoordinates.getX() - secondCoordinates.getX()) +
                Math.abs(firstCoordinates.getY() - secondCoordinates.getY()) +
                Math.abs(firstCoordinates.getZ() - secondCoordinates.getZ())
                ) / 2;
    }
}
