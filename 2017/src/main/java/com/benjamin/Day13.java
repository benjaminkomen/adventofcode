package com.benjamin;

import com.benjamin.repositories.InputRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day13 {

    private InputRepository inputRepository = new InputRepository();

    public static void main(String[] args) {

        Day13 instance = new Day13();
        String input = instance.inputRepository.getInput(13);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input)); // 56900 is too low
    }

    /**
     * Returns the sum of severeties of being caught by scannes of the firewall.
     */
    protected int deelEenA(final String input) {
        List<Layer> layers = stringToLayerList(input);
        int packetPosition = -1;
        int deepestLayer = layers.stream().map(Layer::getDepth).mapToInt(i -> i).max().orElse(0);

        for (int picosecond = 0; picosecond <= deepestLayer; picosecond++) {
            packetPosition++;

            final int currentLayerDepth = packetPosition;

            Optional<Layer> layerAtCurrentPacketPosition = layers.stream()
                    .filter(l -> l.getDepth().equals(currentLayerDepth))
                    .findAny();

            boolean scannerPresentInTopRow = false;

            if (layerAtCurrentPacketPosition.isPresent()) {
                scannerPresentInTopRow = layerAtCurrentPacketPosition.get().getScannerPosition() == 1;
            }

            if (scannerPresentInTopRow) {
                layerAtCurrentPacketPosition.get().catchPacket();
            }

            layers.forEach(Layer::moveScanner);
        }

        return layers.parallelStream()
                .filter(Layer::isCaught)
                .reduce(0, (sum, layer) -> layer.getDepth() * layer.getRange(), (a, b) -> a + b);
    }

    /**
     * Returns the minimum amount of picoseonds delay to not get caught.
     */
    protected int deelTweeA(final String input) {
        List<Layer> layers = stringToLayerList(input);
        int deepestLayer = layers.stream().map(Layer::getDepth).mapToInt(i -> i).max().orElse(0);
        boolean caughtSomewhere = true;
        int delay = -1;

        while (caughtSomewhere) {
            delay++;
            layers.forEach(Layer::resetCaught);
            int packetPosition = -1;

            for (int picosecond = 0; packetPosition <= deepestLayer; picosecond++) {

                if (picosecond >= delay) {
                    packetPosition++; // start trip
                }

                final int currentLayerDepth = packetPosition;

                Optional<Layer> layerAtCurrentPacketPosition = layers.stream()
                        .filter(l -> l.getDepth().equals(currentLayerDepth))
                        .findAny();

                boolean scannerPresentInTopRow = false;

                if (layerAtCurrentPacketPosition.isPresent()) {
                    scannerPresentInTopRow = layerAtCurrentPacketPosition.get().getScannerPosition() == 1;
                }

                if (scannerPresentInTopRow) {
                    layerAtCurrentPacketPosition.get().catchPacket();
                }

                layers.forEach(Layer::moveScanner);
            }

            caughtSomewhere = layers.stream().anyMatch(Layer::isCaught);
        }

        return delay - 1;
    }

    /**
     * Turn a string into a list Layer objects.
     */
    private List<Layer> stringToLayerList(String input) {
        return Arrays.stream(input.split("\n"))
                .map(s -> s.split(": "))
                .map(this::makeLayer)
                .collect(Collectors.toList());
    }

    private Layer makeLayer(String[] array) {
        return new Layer(Integer.valueOf(array[0]), Integer.valueOf(array[1]));
    }

    private class Layer {

        private Integer depth;
        private Integer range;
        private Integer scannerPosition = 1;
        private Direction scannerDirection = Direction.DOWN;
        private boolean caught;

        private Layer() {
            //no-args constructor
        }

        private Layer(Integer depth, Integer range) {
            this.depth = depth;
            this.range = range;
        }

        public Integer getDepth() {
            return depth;
        }

        public Integer getRange() {
            return range;
        }

        public Integer getScannerPosition() {
            return scannerPosition;
        }

        public boolean isCaught() {
            return caught;
        }

        public void catchPacket() {
            this.caught = true;
        }

        public void resetCaught() {
            caught = false;
        }

        public void moveScanner() {
            if (scannerDirection == Direction.DOWN) {
                if (scannerPosition.equals(range)) {
                    scannerDirection = Direction.UP;
                    scannerPosition--;
                } else {
                    scannerPosition++;
                }
            } else if (scannerDirection == Direction.UP) {
                if (scannerPosition.equals(1)) {
                    scannerDirection = Direction.DOWN;
                    scannerPosition++;
                } else {
                    scannerPosition--;
                }
            }
        }
    }

    private enum Direction {
        DOWN, UP;
    }
}
