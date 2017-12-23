package com.benjamin;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day8 extends Day {

    private Map<String, Integer> registerMap = new HashMap<>();
    private int maxRegisterValue = 0;

    private ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");

    public static void main(String[] args) throws ScriptException {

        Day8 instance = new Day8();
        String input = instance.inputRepository.getInput(8);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    /**
     * Compute the largest value in any register after computing several instructions.
     */
    protected int deelEenA(final String input) throws ScriptException {
        final List<Instruction> instructions = stringToInstructions(input);

        for (Instruction instruction : instructions) {

            if (instruction.testCondition()) {
                registerMap.putIfAbsent(instruction.register, 0);

                if (instruction.getModification() == Modification.INCREASE) {
                    int newRegisterValue = registerMap.get(instruction.register) + instruction.value;
                    registerMap.put(instruction.register, newRegisterValue);
                } else if (instruction.getModification() == Modification.DECREASE) {
                    int newRegisterValue = registerMap.get(instruction.register) - instruction.value;
                    registerMap.put(instruction.register, newRegisterValue);
                } else {
                    throw new RuntimeException("not increase and not decrease???");
                }
            }
        }

        return registerMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .mapToInt(i -> i)
                .max()
                .orElseThrow(() -> new IllegalStateException("Could not determine maximum register value."));
    }

    /**
     * Compute the maximum register value during the computation of part 1.
     */
    protected int deelTweeA(final String input) throws ScriptException {
        registerMap = new HashMap<>();
        final List<Instruction> instructions = stringToInstructions(input);

        for (Instruction instruction : instructions) {

            if (instruction.testCondition()) {
                registerMap.putIfAbsent(instruction.register, 0);

                if (instruction.getModification() == Modification.INCREASE) {
                    int newRegisterValue = registerMap.get(instruction.register) + instruction.value;
                    registerMap.put(instruction.register, newRegisterValue);

                    // increase maxRegisterValue if applicable
                    maxRegisterValue = newRegisterValue > maxRegisterValue
                            ? newRegisterValue
                            : maxRegisterValue;
                } else if (instruction.getModification() == Modification.DECREASE) {
                    int newRegisterValue = registerMap.get(instruction.register) - instruction.value;
                    registerMap.put(instruction.register, newRegisterValue);

                    // increase maxRegisterValue if applicable
                    maxRegisterValue = newRegisterValue > maxRegisterValue
                            ? newRegisterValue
                            : maxRegisterValue;
                } else {
                    throw new RuntimeException("not increase and not decrease???");
                }
            }
        }

        return maxRegisterValue;
    }

    /**
     * Turn a string into a list of instructions.
     */
    private List<Instruction> stringToInstructions(String input) {
        return Arrays.stream(input.split("\n"))
                .map(this::stringToInstruction)
                .collect(Collectors.toList());
    }

    private Instruction stringToInstruction(String string) {
        Pattern pattern = Pattern.compile("^(\\w+)\\s(\\w+)\\s(.*?)\\sif\\s(\\w+)\\s(.*?)\\s(.*?)$");
        Matcher matcher = pattern.matcher(string);

        String register = matcher.matches()
                ? matcher.group(1)
                : null;

        Modification modification = matcher.matches()
                ? "inc".equals(matcher.group(2))
                ? Modification.INCREASE
                : "dec".equals(matcher.group(2))
                ? Modification.DECREASE
                : null
                : null;

        Integer value = matcher.matches()
                ? Integer.valueOf(matcher.group(3))
                : null;

        String conditionRegister = matcher.matches()
                ? matcher.group(4)
                : null;

        String conditionComparator = matcher.matches()
                ? matcher.group(5)
                : null;

        Integer conditionValue = matcher.matches()
                ? Integer.valueOf(matcher.group(6))
                : null;

        return new Instruction(register, modification, value, conditionRegister, conditionComparator, conditionValue);
    }

    private class Instruction {

        private String register;
        private Modification modification;
        private Integer value;
        private String conditionRegister;
        private String conditionComparator;
        private Integer conditionValue;

        private Instruction() {
            // no-args constructor
        }

        public Instruction(String register, Modification modification, Integer value, String conditionRegister,
                           String conditionComparator, Integer conditionValue) {
            this.register = register;
            this.modification = modification;
            this.value = value;
            this.conditionRegister = conditionRegister;
            this.conditionComparator = conditionComparator;
            this.conditionValue = conditionValue;
        }

        public String getRegister() {
            return register;
        }

        public Modification getModification() {
            return modification;
        }

        public int getValue() {
            return value;
        }

        public String getConditionRegister() {
            return conditionRegister;
        }

        public String getConditionComparator() {
            return conditionComparator;
        }

        public Integer getConditionValue() {
            return conditionValue;
        }

        private boolean testCondition() throws ScriptException {
            Integer registerValue = registerMap.getOrDefault(conditionRegister, 0);

            String expr = registerValue + conditionComparator + conditionValue;
            return (Boolean) scriptEngine.eval(expr);
        }
    }

    private enum Modification {
        INCREASE, DECREASE;
    }
}
