package com.benjamin;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 extends Day {

    public static void main(String[] args) {

        Day3 instance = new Day3();
        String input = instance.inputRepository.getInput(3);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    protected int deelEenA(final String input) {
        List<Claim> claims = Arrays.stream(input.split("\n"))
                .map(Claim::new)
                .collect(Collectors.toList());

        Map<Coordinates, Integer> fabric = new HashMap<>();

        for (Claim claim : claims) {
            claim.coordinatesCovered()
                    .forEach(c -> fabric.merge(c, 1, (oldVal, newVal) -> oldVal + newVal));
        }

        return fabric.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(e -> 1)
                .reduce(0, (a, b) -> a + b);
    }

    protected int deelTweeA(final String input) {
        return 0;
    }

    private static class Claim {

        private final int id;
        private final int leftPosition;
        private final int topPosition;
        private final int width;
        private final int height;

        public List<Coordinates> coordinatesCovered() {
            List<Coordinates> result = new ArrayList<>();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    result.add(new Coordinates(x + leftPosition, y + topPosition));
                }
            }

            return result;
        }

        public Claim() {
            this.id = 0;
            this.leftPosition = 0;
            this.topPosition = 0;
            this.width = 0;
            this.height = 0;
        }

        public Claim(int id, int leftPosition, int topPosition, int width, int height) {
            this.id = id;
            this.leftPosition = leftPosition;
            this.topPosition = topPosition;
            this.width = width;
            this.height = height;
        }

        public Claim(String input) {
            Pattern pattern = Pattern.compile("#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)");
            Matcher matcher = pattern.matcher(input);

            if (matcher.matches()) {
                this.id = Integer.valueOf(matcher.group(1));
                this.leftPosition = Integer.valueOf(matcher.group(2));
                this.topPosition = Integer.valueOf(matcher.group(3));
                this.width = Integer.valueOf(matcher.group(4));
                this.height = Integer.valueOf(matcher.group(5));
            } else {
                this.id = 0;
                this.leftPosition = 0;
                this.topPosition = 0;
                this.width = 0;
                this.height = 0;
            }
        }
    }

    private static class Coordinates {

        private final int xCoordinate;
        private final int yCoordinate;

        public Coordinates() {
            this.xCoordinate = 0;
            this.yCoordinate = 0;
        }

        public Coordinates(int xCoordinate, int yCoordinate) {
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof Coordinates &&
                    xCoordinate == ((Coordinates) other).xCoordinate &&
                    yCoordinate == ((Coordinates) other).yCoordinate;
        }

        @Override
        public int hashCode() {
            return Objects.hash(xCoordinate, yCoordinate);
        }

        @Override
        public String toString() {
            return "x=" + xCoordinate +
                    ", y=" + yCoordinate;
        }
    }
}
