package com.benjamin.objects;

public class Memory {

    private int id;
    private Coordinates coordinates;
    private int value;

    private Memory() {
        // no-args constructor
    }

    private Memory(int id, Coordinates coordinates, int value) {
        this.id = id;
        this.coordinates = coordinates;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Memory{" +
                "id=" + id +
                ", coordinates=" + coordinates +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Memory memory = (Memory) o;

        return id == memory.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public static Memory of(int id, Coordinates coordinates, int value) {
        return new Memory(id, coordinates, value);
    }
}
