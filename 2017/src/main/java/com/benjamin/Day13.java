package com.benjamin;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day13 extends Day {

    public static void main(String[] args) {

        Day13 instance = new Day13();
        String input = instance.inputRepository.getInput(13);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeB(input));
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
     *
     * @deprecated use {@link #deelTweeB(String)}, which is A LOT faster.
     */
    @Deprecated
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
     * More optimised way of looking.
     */
    protected int deelTweeB(final String input) {
        List<Layer> layers = stringToLayerList(input);
        int deepestLayer = layers.stream().map(Layer::getDepth).mapToInt(i -> i).max().orElse(0);
        boolean caughtSomewhere = true;

        // increase delay and loop until we found a delay for which we are not caught
        for (int delay = 0; caughtSomewhere; delay++) {

            // for the given delay, check every layer to see if we would be caught there
            inner:
            for (int picosecond = 0; picosecond <= deepestLayer; picosecond++) {

                final int currentLayerDepth = picosecond;

                Optional<Layer> currentLayerOptional = layers.stream()
                        .filter(l -> l.getDepth().equals(currentLayerDepth))
                        .findAny();

                if (currentLayerOptional.isPresent()) {
                    Layer currentLayer = currentLayerOptional.get();
                    int scannerPosition = (picosecond + delay) % (2 * (currentLayer.getRange() - 1));

                    caughtSomewhere = scannerPosition == 0;
                }

                // if there are e.g. 100 layers, but we are already caught at layer 5, it is not necessary to check any further
                if (caughtSomewhere) {
                    break inner;
                }
            }

            // at this point we have looped over all the layers for the given delay and we are not caught anywhere,
            // so we found the correct delay and we can return it!
            if (!caughtSomewhere) {
                return delay;
            }
        }
        return 0;
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
