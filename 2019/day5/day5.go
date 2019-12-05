package main

import (
	"fmt"
	"github.com/adventofcode/2019/common"
	"os"
)

func main() {
	result1 := RunProgram(common.ToIntSlice(common.ReadLines("./day5/input.txt")), 1)
	result2 := RunProgram(common.ToIntSlice(common.ReadLines("./day5/input.txt")), 5)

	fmt.Printf("Part 1 is: %d \n", result1)
	fmt.Printf("Part 2 is: %d \n", result2)

}

func RunProgram(inputNumbers []int, inputInstruction int) int {
	instructionPointer := 0
	output := 0

outerLoop:
	for {
		opcode, modeOfFirstParam, modeOfSecondParam, modeOfThirdParam := extractInstruction(inputNumbers[instructionPointer])

		switch opcode {
		case 1:
			// adds together numbers read from two positions and stores the result in a third position.
			var readPosition1, readPosition2, writePosition int

			// determine readPosition1
			{
				if modeOfFirstParam == 0 {
					readPosition1 = inputNumbers[instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					readPosition1 = instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			// determine readPosition2
			{
				if modeOfSecondParam == 0 {
					readPosition2 = inputNumbers[instructionPointer+2]
				} else if modeOfSecondParam == 1 {
					readPosition2 = instructionPointer + 2
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfSecondParam)
					os.Exit(-1)
				}
			}

			// determine writePosition
			// Note: Parameters that an instruction writes to will never be in immediate mode. So in theory modeOfThirdParam should always be 0
			{
				if modeOfThirdParam == 0 {
					writePosition = inputNumbers[instructionPointer+3]
				} else if modeOfThirdParam == 1 {
					writePosition = instructionPointer + 3
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfThirdParam)
					os.Exit(-1)
				}
			}

			inputNumbers[writePosition] = inputNumbers[readPosition1] + inputNumbers[readPosition2]
			instructionPointer = move(instructionPointer, len(inputNumbers), 4)
			output = inputNumbers[0]
		case 2:
			// adds together numbers read from two positions and stores the result in a third position.
			var readPosition1, readPosition2, writePosition int

			// determine readPosition1
			{
				if modeOfFirstParam == 0 {
					readPosition1 = inputNumbers[instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					readPosition1 = instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			// determine readPosition2
			{
				if modeOfSecondParam == 0 {
					readPosition2 = inputNumbers[instructionPointer+2]
				} else if modeOfSecondParam == 1 {
					readPosition2 = instructionPointer + 2
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfSecondParam)
					os.Exit(-1)
				}
			}

			// determine writePosition
			// Note: Parameters that an instruction writes to will never be in immediate mode. So in theory modeOfThirdParam should always be 0
			{
				if modeOfThirdParam == 0 {
					writePosition = inputNumbers[instructionPointer+3]
				} else if modeOfThirdParam == 1 {
					writePosition = instructionPointer + 3
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfThirdParam)
					os.Exit(-1)
				}
			}

			inputNumbers[writePosition] = inputNumbers[readPosition1] * inputNumbers[readPosition2]
			instructionPointer = move(instructionPointer, len(inputNumbers), 4)
			output = inputNumbers[0]
		case 3:
			// Opcode 3 takes a single integer as input and saves it to the position given by its only parameter.
			var writePosition int

			// determine writePosition
			// Note: Parameters that an instruction writes to will never be in immediate mode. So in theory modeOfFirstParam should always be 0
			{
				if modeOfFirstParam == 0 {
					writePosition = inputNumbers[instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					writePosition = instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			inputNumbers[writePosition] = inputInstruction
			instructionPointer = move(instructionPointer, len(inputNumbers), 2)
		case 4:
			// Opcode 4 outputs the value of its only parameter.
			var readPosition int

			// determine readPosition
			{
				if modeOfFirstParam == 0 {
					readPosition = inputNumbers[instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					readPosition = instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			output = inputNumbers[readPosition]
			fmt.Printf("Output instruction: %d\n", output)
			instructionPointer = move(instructionPointer, len(inputNumbers), 2)
		case 5:
			// Opcode 5 is jump-if-true
			var firstParameter, secondParameter int

			// determine firstParameter
			{
				if modeOfFirstParam == 0 {
					firstParameter = inputNumbers[instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					firstParameter = instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			// determine secondParameter
			{
				if modeOfSecondParam == 0 {
					secondParameter = inputNumbers[instructionPointer+2]
				} else if modeOfSecondParam == 1 {
					secondParameter = instructionPointer + 2
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfSecondParam)
					os.Exit(-1)
				}
			}

			if inputNumbers[firstParameter] != 0 {
				instructionPointer = inputNumbers[secondParameter]
			} else {
				instructionPointer = move(instructionPointer, len(inputNumbers), 3)
			}
		case 6:
			// Opcode 6 is jump-if-false
			var firstParameter, secondParameter int

			// determine firstParameter
			{
				if modeOfFirstParam == 0 {
					firstParameter = inputNumbers[instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					firstParameter = instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			// determine secondParameter
			{
				if modeOfSecondParam == 0 {
					secondParameter = inputNumbers[instructionPointer+2]
				} else if modeOfSecondParam == 1 {
					secondParameter = instructionPointer + 2
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfSecondParam)
					os.Exit(-1)
				}
			}

			if inputNumbers[firstParameter] == 0 {
				instructionPointer = inputNumbers[secondParameter]
			} else {
				instructionPointer = move(instructionPointer, len(inputNumbers), 3)
			}

		case 7:
			// Opcode 7 is less than
			var firstParameter, secondParameter, thirdParameter int

			// determine firstParameter
			{
				if modeOfFirstParam == 0 {
					firstParameter = inputNumbers[instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					firstParameter = instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			// determine secondParameter
			{
				if modeOfSecondParam == 0 {
					secondParameter = inputNumbers[instructionPointer+2]
				} else if modeOfSecondParam == 1 {
					secondParameter = instructionPointer + 2
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfSecondParam)
					os.Exit(-1)
				}
			}

			// determine thirdParameter
			{
				if modeOfThirdParam == 0 {
					thirdParameter = inputNumbers[instructionPointer+3]
				} else if modeOfThirdParam == 1 {
					thirdParameter = instructionPointer + 3
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfThirdParam)
					os.Exit(-1)
				}
			}

			if inputNumbers[firstParameter] < inputNumbers[secondParameter] {
				inputNumbers[thirdParameter] = 1
			} else {
				inputNumbers[thirdParameter] = 0
			}
			instructionPointer = move(instructionPointer, len(inputNumbers), 4)
		case 8:
			// Opcode 8 is equals
			var firstParameter, secondParameter, thirdParameter int

			// determine firstParameter
			{
				if modeOfFirstParam == 0 {
					firstParameter = inputNumbers[instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					firstParameter = instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			// determine secondParameter
			{
				if modeOfSecondParam == 0 {
					secondParameter = inputNumbers[instructionPointer+2]
				} else if modeOfSecondParam == 1 {
					secondParameter = instructionPointer + 2
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfSecondParam)
					os.Exit(-1)
				}
			}

			// determine thirdParameter
			{
				if modeOfThirdParam == 0 {
					thirdParameter = inputNumbers[instructionPointer+3]
				} else if modeOfThirdParam == 1 {
					thirdParameter = instructionPointer + 3
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfThirdParam)
					os.Exit(-1)
				}
			}

			if inputNumbers[firstParameter] == inputNumbers[secondParameter] {
				inputNumbers[thirdParameter] = 1
			} else {
				inputNumbers[thirdParameter] = 0
			}
			instructionPointer = move(instructionPointer, len(inputNumbers), 4)
		case 99:
			// 99 means that the program is finished and should immediately halt
			break outerLoop
		default:
			fmt.Printf("unknown opcode and/or parametermode, an error has occured!")
			os.Exit(-1)
		}
	}
	return output
}

func extractInstruction(instruction int) (int, int, int, int) {
	instructionSlice := common.IntToIntSlice(instruction)

	var de, c, b, a int

	if len(instructionSlice) > 1 {
		de = common.IntSliceToInt(instructionSlice[len(instructionSlice)-2:])
	} else {
		de = instructionSlice[0]
	}

	if len(instructionSlice) > 2 {
		c = common.IntSliceToInt(instructionSlice[len(instructionSlice)-3 : len(instructionSlice)-2])
	} else {
		c = 0
	}

	if len(instructionSlice) > 3 {
		b = common.IntSliceToInt(instructionSlice[len(instructionSlice)-4 : len(instructionSlice)-3])
	} else {
		b = 0
	}

	if len(instructionSlice) > 4 {
		a = instructionSlice[0]
	} else {
		a = 0
	}

	return de, c, b, a
}

func move(instructionPointer int, inputLength int, steps int) int {
	if (instructionPointer + steps) < inputLength {
		instructionPointer = instructionPointer + steps
	}
	return instructionPointer
}
