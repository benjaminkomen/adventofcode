package main

import (
	"bufio"
	"fmt"
	"github.com/adventofcode/2019/common"
	"os"
	"strconv"
	"strings"
)

func main() {
	scanner := common.ReadLines("./day2/input.txt")
	inputNumbers := prepareInput(scanner)

	inputNumbers = preprovisionNumbers(inputNumbers)

	result := RunProgram(inputNumbers)

	fmt.Printf("Part 1: number at position 0 is: %d \n", result)

}

func preprovisionNumbers(numbers []int) []int {
	numbers[1] = 12
	numbers[2] = 2
	return numbers
}

func RunProgram(input []int) int {
	for i := 0; (i + 4) < len(input); i = i + 4 {
		opcode := input[i]

		switch opcode {
		case 1:
			// adds together numbers read from two positions and stores the result in a third position.
			readPosition1 := input[i+1]
			readPosition2 := input[i+2]
			writePosition := input[i+3]
			input[writePosition] = input[readPosition1] + input[readPosition2]
		case 2:
			// Opcode 2 works exactly like opcode 1, except it multiplies the two inputs instead of adding them.
			readPosition1 := input[i+1]
			readPosition2 := input[i+2]
			writePosition := input[i+3]
			input[writePosition] = input[readPosition1] * input[readPosition2]
		case 99:
			// 99 means that the program is finished and should immediately halt
			break
		default:
			fmt.Printf("unknown opcode, an error has occured!")
			os.Exit(-1)
		}
	}

	return input[0]
}

func prepareInput(scanner *bufio.Scanner) []int {
	var inputNumbers []int

	for scanner.Scan() {
		input := scanner.Text()
		inputStrings := strings.Split(input, ",")

		for _, inputString := range inputStrings {
			inputNumber, _ := strconv.Atoi(inputString)
			inputNumbers = append(inputNumbers, inputNumber)
		}
	}
	return inputNumbers
}
