package com.benjamin;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day18 extends Day {

    public static void main(String[] args) {

        Day18 instance = new Day18();
        String input = instance.inputRepository.getInput(18);

        System.out.println("The answer to part 1 is: " + instance.deelEenA(input));
        System.out.println("The answer to part 2 is: " + instance.deelTweeA(input));
    }

    /**
     *
     */
    protected int deelEenA(String input) {
        Set<Register> registers = new HashSet<>();
        List<Instruction> instructions = stringToInstructionList(input);

        for (int i = 0; i < instructions.size(); i++) {

            try {
                performInstruction(instructions, registers, i);
            } catch (FirstRecoveredException e) {
                return e.getRegister().getValue();
            }
        }
        return 0;
    }

    /**
     *
     */
    protected String deelTweeA(String input) {
        return "";
    }

    /**
     * Turn a string into a list Instruction objects.
     */
    private List<Instruction> stringToInstructionList(String input) {
        return Arrays.stream(input.split("\n"))
                .map(this::makeInstruction)
                .collect(Collectors.toList());
    }

    /**
     * Factory method to create a DanceMove object from a given input String.
     */
    private Instruction makeInstruction(String string) {
        Pattern pattern = Pattern.compile("^(.*?)\\s([a-z0-9])(\\s|)(.*?|)$");
        Matcher matcher = pattern.matcher(string);

        String instructionType = matcher.matches()
                ? matcher.group(1)
                : null;

        String register = matcher.matches()
                ? matcher.group(2)
                : null;

        String value = matcher.matches()
                ? matcher.group(4)
                : null;

        return new Instruction(InstructionType.fromDescription(instructionType), register, value);
    }

    /**
     * Returns true if the instruction was actually performed.
     */
    private boolean performInstruction(List<Instruction> instructions, Set<Register> registers, int instructionNumber) {

        Instruction instruction = instructions.get(instructionNumber);

        Optional<Register> registerX = findRegister(registers, instruction.getRegister());
        Integer currentValueX = registerX.map(Register::getValue).orElse(0);

        Optional<Register> registerY = findRegister(registers, instruction.getValue());
        Integer currentValueY = registerY.map(Register::getValue).orElse(0);

        switch (instruction.getInstructionType()) {
            case PLAY_SOUND:
                upsertRegisterLastPlayedValue(registers, instruction.getRegister());

                registerX.ifPresent(register1 -> System.out.println(String.format("Playing sound of register %s with frequency of %s.",
                        instruction.getRegister(), register1.getValue())));
                return true;
            case SET:
                if (isNumeric(instruction.getValue())) {
                    Integer newValue = Integer.valueOf(instruction.getValue());
                    upsertRegisterValue(registers, instruction.getRegister(), newValue);
                } else {
                    Integer newValue = registerX.map(Register::getValue).orElse(null);

                    upsertRegisterValue(registers, instruction.getRegister(), newValue);
                }
                return true;
            case INCREASE:
                if (isNumeric(instruction.getValue())) {
                    Integer newValue = currentValueX + Integer.valueOf(instruction.getValue());
                    upsertRegisterValue(registers, instruction.getRegister(), newValue);
                } else {
                    Integer newValue = currentValueX + currentValueY;

                    upsertRegisterValue(registers, instruction.getRegister(), newValue);
                }
                return true;
            case MULTIPLY:
                if (isNumeric(instruction.getValue())) {
                    Integer newValue = currentValueX * Integer.valueOf(instruction.getValue());
                    upsertRegisterValue(registers, instruction.getRegister(), newValue);
                } else {
                    Integer newValue = currentValueX * currentValueY;
                    upsertRegisterValue(registers, instruction.getRegister(), newValue);
                }
                return true;
            case MODULO:
                if (isNumeric(instruction.getValue())) {
                    Integer newValue = currentValueX % Integer.valueOf(instruction.getValue());
                    upsertRegisterValue(registers, instruction.getRegister(), newValue);
                } else {
                    Integer newValue = currentValueX % currentValueY;
                    upsertRegisterValue(registers, instruction.getRegister(), newValue);
                }
                return true;
            case RECOVER:
                return recoverLastPlayedValue(registerX);
            case JUMP:
                if (registerX.isPresent() && registerX.get().getValue() > 0) {
                    Integer amountToJump;
                    if (isNumeric(instruction.getValue())) {
                        amountToJump = Integer.valueOf(instruction.getValue());
                    } else {
                        amountToJump = currentValueY;
                    }
                    return performInstruction(instructions, registers, instructionNumber + amountToJump);
                } else {
                    return false;
                }
            default:
                throw new IllegalStateException("Unrecognized instructionType " + instruction.getInstructionType());
        }
    }

    private static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    @NotNull
    private static Optional<Register> findRegister(Set<Register> registers, String name) {
        return registers.stream()
                .filter(r -> r.getName().equals(name))
                .findFirst();
    }

    private static void upsertRegisterValue(Set<Register> registers, String name, Integer value) {

        Optional<Register> register = findRegister(registers, name);
        if (register.isPresent()) {
            register.get().setValue(value);
        } else {
            registers.add(new Register(name, value));
        }
    }

    /**
     * Returns true if the recover was performed.
     */
    private static boolean recoverLastPlayedValue(Optional<Register> registerOptional) {
        if (registerOptional.isPresent()) {
            Register register = registerOptional.get();

            if (register.getValue() != 0) {
                register.setValue(register.getLastPlayedValue());
                throw new FirstRecoveredException(register);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static void upsertRegisterLastPlayedValue(Set<Register> registers, String name) {

        Optional<Register> register = findRegister(registers, name);
        if (register.isPresent()) {
            register.get().setLastPlayedValue(register.get().getValue());
        } else {
            throw new IllegalStateException("We cannot play the sound of a register which does not exist yet.");
        }
    }

    private static class Register {
        private String name;
        private Integer value = 0;
        private Integer lastPlayedValue;

        private Register() {
            // no-args constructor
        }

        public Register(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Integer getLastPlayedValue() {
            return lastPlayedValue;
        }

        public void setLastPlayedValue(Integer lastPlayedValue) {
            this.lastPlayedValue = lastPlayedValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Register register = (Register) o;
            return Objects.equals(name, register.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    private class Instruction {
        private InstructionType instructionType;
        private String register;
        private String value;

        private Instruction() {
            // no-args constructor
        }

        public Instruction(InstructionType instructionType, String register, String value) {
            this.instructionType = instructionType;
            this.register = register;
            this.value = value;
        }

        public InstructionType getInstructionType() {
            return instructionType;
        }

        public String getRegister() {
            return register;
        }

        public String getValue() {
            return value;
        }
    }

    private enum InstructionType {
        PLAY_SOUND("snd"),
        SET("set"),
        INCREASE("add"),
        MULTIPLY("mul"),
        MODULO("mod"),
        RECOVER("rcv"),
        JUMP("jgz");

        private String description;

        InstructionType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public static InstructionType fromDescription(String description) {
            return Arrays.stream(InstructionType.values())
                    .filter(it -> it.getDescription().equals(description))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("No enum found for description  \'" + description + "\'"));
        }
    }

    private static class FirstRecoveredException extends RuntimeException {

        private final transient Register register;

        public FirstRecoveredException(Register register) {
            this.register = register;
        }

        public Register getRegister() {
            return register;
        }
    }
}
