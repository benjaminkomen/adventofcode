package main

import (
	"fmt"
	"github.com/adventofcode/2019/common"
	"strings"
)

func main() {
	inputAsStr := string(common.ReadBytes("./day6/input.txt"))
	inputInstructions := PrepareInput(inputAsStr)

	result1 := RunProgram(inputInstructions)

	fmt.Printf("Part 1 is: %d \n", result1)
	//fmt.Printf("Part 2 is: %d \n", result2)

}

func RunProgram(instructions []Instruction) int {

	result := make(map[string]int) // key = subject, value = # of orbits

	// for every instruction:
	// update the result with the amount of orbits per subject

	for _, instruction := range instructions {

	}

	return 0
}

type Instruction struct {
	subject string
	orbiter string
}

func PrepareInput(inputAsStr string) []Instruction {
	var instructions []Instruction

	lines := strings.Split(inputAsStr, "\n")

	for _, line := range lines {
		instructionsOnLine := strings.Split(line, ")")

		instruction := Instruction{
			subject: instructionsOnLine[0],
			orbiter: instructionsOnLine[1], // orbiter is in orbit around subject
		}

		instructions = append(instructions, instruction)

	}
	return instructions
}
