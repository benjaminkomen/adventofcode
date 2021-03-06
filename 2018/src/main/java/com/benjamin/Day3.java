package com.benjamin;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 extends Day {

    public static void main(String[] args) {

        Day3 instance = new Day3();
        String input = instance.inputRepository.getInput(3);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input)); // 108961
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input)); // 681
    }

    protected int deelEenA(final String input) {
        final Map<Coordinates, Set<Integer>> fabric = fillFabricWithClaims(input);

        return fabric.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .map(e -> 1)
                .reduce(0, (a, b) -> a + b);
    }

    protected int deelTweeA(final String input) {
        final Map<Coordinates, Set<Integer>> fabric = fillFabricWithClaims(input);

        Set<Integer> claimIdsOfOverlappingClaim = fabric.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toSet());

        List<Claim> claims = makeClaims(input);

        return getClaimIdByCoordinates(claims, claimIdsOfOverlappingClaim);
    }

    @NotNull
    private Map<Coordinates, Set<Integer>> fillFabricWithClaims(String input) {
        List<Claim> claims = makeClaims(input);

        Map<Coordinates, Set<Integer>> fabric = new HashMap<>();

        for (Claim claim : claims) {
            claim.getCoordinates()
                    .forEach(c -> fabric.merge(c, Set.of(claim.id), (oldVal, newVal) ->
                            Stream.concat(oldVal.stream(), newVal.stream()).collect(Collectors.toSet()))
                    );
        }
        return fabric;
    }

    @NotNull
    private List<Claim> makeClaims(String input) {
        return Arrays.stream(input.split("\n"))
                .map(Claim::new)
                .collect(Collectors.toList());
    }

    private int getClaimIdByCoordinates(List<Claim> claims, Set<Integer> claimIdsOfOverlappingClaim) {
        return claims.stream()
                .map(claim -> claim.id)
                .filter(claimId -> !claimIdsOfOverlappingClaim.contains(claimId))
                .findAny()
                .orElse(0);
    }

    private static class Claim {

        private final int id;
        private final int leftPosition;
        private final int topPosition;
        private final int width;
        private final int height;

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

        public List<Coordinates> getCoordinates() {
            List<Coordinates> result = new ArrayList<>();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    result.add(new Coordinates(x + leftPosition, y + topPosition));
                }
            }

            return result;
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
