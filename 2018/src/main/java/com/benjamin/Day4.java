package com.benjamin;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 extends Day {

    private static final Pattern PATTERN = Pattern.compile("^\\[(.*?)] (wakes up|Guard #(\\d+) begins shift|falls asleep)$");
    private static final DateTimeFormatter LOCALDATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private List<Record> records = new ArrayList<>();

    public static void main(String[] args) {

        Day4 instance = new Day4();
        String input = instance.inputRepository.getInput(4);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input)); // 35623
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    protected int deelEenA(final String input) {
        this.records = makeRecords(input);
        this.records = addMissingGuardIds(this.records);
        Map<Integer, Map<Integer, Integer>> minutesAsleepPerGuardId = determineMinutesAsleepPerGuardId(this.records);
        Integer mostAsleepGuardId = getMostAsleepGuardId(minutesAsleepPerGuardId);
        Integer minuteMostAsleep = getMinuteMostAsleep(minutesAsleepPerGuardId, mostAsleepGuardId);
        return mostAsleepGuardId * minuteMostAsleep;
    }

    @NotNull
    private List<Record> makeRecords(String input) {
        return Arrays.stream(input.split("\n"))
                .map(Record::new)
                .sorted(Comparator.comparing(Record::getTimestamp))
                .collect(Collectors.toList());
    }

    private List<Record> addMissingGuardIds(List<Record> records) {
        List<Record> newRecords = new ArrayList<>(records); // shallow copy only
        for (int i = 0; i < newRecords.size(); i++) {
            Record record = newRecords.get(i);
            if (record.getGuardId() == null) {
                record.guardId = records.subList(0, i).stream()
                        .sorted(Collections.reverseOrder(Comparator.comparing(Record::getTimestamp)))
                        .map(Record::getGuardId)
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElse(null);
            }
        }

        return newRecords;
    }

    /**
     * Map of: GuardId with a Map of minute and frequency of being asleep during that minute
     */
    private Map<Integer, Map<Integer, Integer>> determineMinutesAsleepPerGuardId(List<Record> records) {
        Map<Integer, Map<Integer, Integer>> minutesAsleepPerGuardId = new HashMap<>();

        // loop over the records and capture consecutive FallAsleep and WakeUp actions
        for (int i = 0; i < records.size(); i++) {
            Record record = records.get(i);
            if (record.getAction() == GuardAction.WakeUp) {
                Record previousRecord = records.get(i - 1);
                Integer guardId = record.getGuardId();

                // loop over the minutes a guard is asleep and add them to the map
                for (int j = previousRecord.getTimestamp().getMinute(); j < record.getTimestamp().getMinute(); j++) {
                    Map<Integer, Integer> frequencyAsleepPerMinute = new HashMap<>();
                    frequencyAsleepPerMinute.put(j, 1);
                    minutesAsleepPerGuardId.merge(guardId, frequencyAsleepPerMinute,
                            (oldFreqMap, newFreqMap) -> {
                                newFreqMap.forEach((k, v) -> {
                                    if (!oldFreqMap.containsKey(k)) {
                                        // this minute is not registered yet, add it
                                        oldFreqMap.put(k, v);
                                    } else {
                                        // this minute was already registered, add the new value to the old value
                                        oldFreqMap.merge(k, v, (oldVal, newVal) -> oldVal + newVal);
                                    }
                                });
                                return oldFreqMap;
                            });
                }
            }
        }

        return minutesAsleepPerGuardId;
    }

    /**
     * Calculate the sum of all minutes each guard is asleep, and return the guard ID of the guard with the maximum
     * amount of minutes asleep.
     */
    protected Integer getMostAsleepGuardId(Map<Integer, Map<Integer, Integer>> minutesAsleepPerGuardId) {
        return Collections.max(minutesAsleepPerGuardId.entrySet(),
                Comparator.comparingLong(e -> e.getValue().values().stream()
                        .mapToLong(Long::valueOf)
                        .sum()))
                .getKey();
    }

    protected Integer getMinuteMostAsleep(Map<Integer, Map<Integer, Integer>> minutesAsleepPerGuardId,
                                          Integer mostAsleepGuardId) {
        return minutesAsleepPerGuardId.entrySet().stream()
                .filter(e -> e.getKey().equals(mostAsleepGuardId))
                .map(Map.Entry::getValue)
                .map(map -> Collections.max(map.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not determine minute most asleep"));
    }

    protected int deelTweeA(final String input) {
        return 0;
    }

    private static class Record {

        private final LocalDateTime timestamp;
        private Integer guardId;
        private final GuardAction action;

        private Record() {
            this.timestamp = null;
            this.guardId = null;
            this.action = null;
        }

        public Record(LocalDateTime timestamp, Integer guardId, GuardAction action) {
            this.timestamp = timestamp;
            this.guardId = guardId;
            this.action = action;
        }

        public Record(String input) {
            Matcher matcher = PATTERN.matcher(input);

            if (matcher.matches()) {
                this.timestamp = LocalDateTime.parse(matcher.group(1), LOCALDATETIME_FORMAT);
                this.guardId = matcher.group(3) != null ? Integer.valueOf(matcher.group(3)) : null;
                this.action = GuardAction.fromString(matcher.group(2));
            } else {
                this.timestamp = null;
                this.guardId = null;
                this.action = null;
            }
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public Integer getGuardId() {
            return guardId;
        }

        public GuardAction getAction() {
            return action;
        }
    }

    private enum GuardAction {
        BeginShift,
        FallAsleep,
        WakeUp;

        public static GuardAction fromString(String input) {
            if (input.startsWith("Guard")) {
                return BeginShift;
            } else if ("falls asleep".equals(input)) {
                return FallAsleep;
            } else if ("wakes up".equals(input)) {
                return WakeUp;
            } else {
                throw new IllegalStateException(String.format("Input %s could not be mapped to BeginShift," +
                        " FallAsleep or WakeUp.", input));
            }
        }
    }
}
