package main

import (
	"fmt"
	"github.com/adventofcode/2019/common"
	"os"
)

type IntcodeComputerInstance struct {
	memory                   []int
	inputInstructions        []int
	instructionPointer       int
	output                   int
	inputInstructionsPointer int
}

func createIntcodeComputer(amplifierControllerSoftware []int) IntcodeComputerInstance {
	freshInput := make([]int, len(amplifierControllerSoftware))
	copy(freshInput, amplifierControllerSoftware)
	return IntcodeComputerInstance{
		memory: freshInput,
	}
}

func (vm *IntcodeComputerInstance) Run(inputInstructions []int) (int, bool) {
	vm.inputInstructions = append(vm.inputInstructions, inputInstructions...)
	return vm.IntcodeComputer()
}

// return: output (int) and alreadyDone (bool). This last means we hit case 99.
func (vm *IntcodeComputerInstance) IntcodeComputer() (int, bool) {

outerLoop:
	for {
		opcode, modeOfFirstParam, modeOfSecondParam, modeOfThirdParam := extractInstruction(vm.memory[vm.instructionPointer])

		switch opcode {
		case 1:
			// adds together numbers read from two positions and stores the result in a third position.
			var readPosition1, readPosition2, writePosition int

			// determine readPosition1
			{
				if modeOfFirstParam == 0 {
					readPosition1 = vm.memory[vm.instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					readPosition1 = vm.instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			// determine readPosition2
			{
				if modeOfSecondParam == 0 {
					readPosition2 = vm.memory[vm.instructionPointer+2]
				} else if modeOfSecondParam == 1 {
					readPosition2 = vm.instructionPointer + 2
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfSecondParam)
					os.Exit(-1)
				}
			}

			// determine writePosition
			// Note: Parameters that an instruction writes to will never be in immediate mode. So in theory modeOfThirdParam should always be 0
			{
				if modeOfThirdParam == 0 {
					writePosition = vm.memory[vm.instructionPointer+3]
				} else if modeOfThirdParam == 1 {
					writePosition = vm.instructionPointer + 3
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfThirdParam)
					os.Exit(-1)
				}
			}

			vm.memory[writePosition] = vm.memory[readPosition1] + vm.memory[readPosition2]
			vm.instructionPointer = move(vm.instructionPointer, len(vm.memory), 4)
			vm.output = vm.memory[0]
		case 2:
			// adds together numbers read from two positions and stores the result in a third position.
			var readPosition1, readPosition2, writePosition int

			// determine readPosition1
			{
				if modeOfFirstParam == 0 {
					readPosition1 = vm.memory[vm.instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					readPosition1 = vm.instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			// determine readPosition2
			{
				if modeOfSecondParam == 0 {
					readPosition2 = vm.memory[vm.instructionPointer+2]
				} else if modeOfSecondParam == 1 {
					readPosition2 = vm.instructionPointer + 2
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfSecondParam)
					os.Exit(-1)
				}
			}

			// determine writePosition
			// Note: Parameters that an instruction writes to will never be in immediate mode. So in theory modeOfThirdParam should always be 0
			{
				if modeOfThirdParam == 0 {
					writePosition = vm.memory[vm.instructionPointer+3]
				} else if modeOfThirdParam == 1 {
					writePosition = vm.instructionPointer + 3
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfThirdParam)
					os.Exit(-1)
				}
			}

			vm.memory[writePosition] = vm.memory[readPosition1] * vm.memory[readPosition2]
			vm.instructionPointer = move(vm.instructionPointer, len(vm.memory), 4)
			vm.output = vm.memory[0]
		case 3:
			// Opcode 3 takes a single integer as input and saves it to the position given by its only parameter.
			var writePosition int

			// determine writePosition
			// Note: Parameters that an instruction writes to will never be in immediate mode. So in theory modeOfFirstParam should always be 0
			{
				if modeOfFirstParam == 0 {
					writePosition = vm.memory[vm.instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					writePosition = vm.instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			if vm.inputInstructionsPointer >= len(vm.inputInstructions) {
				// assumption: just take the last inputInstruction if we have exceeded the length
				vm.memory[writePosition] = vm.inputInstructions[len(vm.inputInstructions)-1]
			} else {
				vm.memory[writePosition] = vm.inputInstructions[vm.inputInstructionsPointer]
			}
			vm.inputInstructionsPointer++
			vm.instructionPointer = move(vm.instructionPointer, len(vm.memory), 2)
		case 4:
			// Opcode 4 outputs the value of its only parameter.
			var readPosition int

			// determine readPosition
			{
				if modeOfFirstParam == 0 {
					readPosition = vm.memory[vm.instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					readPosition = vm.instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			vm.output = vm.memory[readPosition]
			return vm.output, false
			//fmt.Printf("Output instruction: %d\n", output)
			vm.instructionPointer = move(vm.instructionPointer, len(vm.memory), 2)
		case 5:
			// Opcode 5 is jump-if-true
			var firstParameter, secondParameter int

			// determine firstParameter
			{
				if modeOfFirstParam == 0 {
					firstParameter = vm.memory[vm.instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					firstParameter = vm.instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			// determine secondParameter
			{
				if modeOfSecondParam == 0 {
					secondParameter = vm.memory[vm.instructionPointer+2]
				} else if modeOfSecondParam == 1 {
					secondParameter = vm.instructionPointer + 2
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfSecondParam)
					os.Exit(-1)
				}
			}

			if vm.memory[firstParameter] != 0 {
				vm.instructionPointer = vm.memory[secondParameter]
			} else {
				vm.instructionPointer = move(vm.instructionPointer, len(vm.memory), 3)
			}
		case 6:
			// Opcode 6 is jump-if-false
			var firstParameter, secondParameter int

			// determine firstParameter
			{
				if modeOfFirstParam == 0 {
					firstParameter = vm.memory[vm.instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					firstParameter = vm.instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			// determine secondParameter
			{
				if modeOfSecondParam == 0 {
					secondParameter = vm.memory[vm.instructionPointer+2]
				} else if modeOfSecondParam == 1 {
					secondParameter = vm.instructionPointer + 2
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfSecondParam)
					os.Exit(-1)
				}
			}

			if vm.memory[firstParameter] == 0 {
				vm.instructionPointer = vm.memory[secondParameter]
			} else {
				vm.instructionPointer = move(vm.instructionPointer, len(vm.memory), 3)
			}

		case 7:
			// Opcode 7 is less than
			var firstParameter, secondParameter, thirdParameter int

			// determine firstParameter
			{
				if modeOfFirstParam == 0 {
					firstParameter = vm.memory[vm.instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					firstParameter = vm.instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			// determine secondParameter
			{
				if modeOfSecondParam == 0 {
					secondParameter = vm.memory[vm.instructionPointer+2]
				} else if modeOfSecondParam == 1 {
					secondParameter = vm.instructionPointer + 2
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfSecondParam)
					os.Exit(-1)
				}
			}

			// determine thirdParameter
			{
				if modeOfThirdParam == 0 {
					thirdParameter = vm.memory[vm.instructionPointer+3]
				} else if modeOfThirdParam == 1 {
					thirdParameter = vm.instructionPointer + 3
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfThirdParam)
					os.Exit(-1)
				}
			}

			if vm.memory[firstParameter] < vm.memory[secondParameter] {
				vm.memory[thirdParameter] = 1
			} else {
				vm.memory[thirdParameter] = 0
			}
			vm.instructionPointer = move(vm.instructionPointer, len(vm.memory), 4)
		case 8:
			// Opcode 8 is equals
			var firstParameter, secondParameter, thirdParameter int

			// determine firstParameter
			{
				if modeOfFirstParam == 0 {
					firstParameter = vm.memory[vm.instructionPointer+1]
				} else if modeOfFirstParam == 1 {
					firstParameter = vm.instructionPointer + 1
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfFirstParam)
					os.Exit(-1)
				}
			}

			// determine secondParameter
			{
				if modeOfSecondParam == 0 {
					secondParameter = vm.memory[vm.instructionPointer+2]
				} else if modeOfSecondParam == 1 {
					secondParameter = vm.instructionPointer + 2
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfSecondParam)
					os.Exit(-1)
				}
			}

			// determine thirdParameter
			{
				if modeOfThirdParam == 0 {
					thirdParameter = vm.memory[vm.instructionPointer+3]
				} else if modeOfThirdParam == 1 {
					thirdParameter = vm.instructionPointer + 3
				} else {
					fmt.Printf("unknown parameterMode %d", modeOfThirdParam)
					os.Exit(-1)
				}
			}

			if vm.memory[firstParameter] == vm.memory[secondParameter] {
				vm.memory[thirdParameter] = 1
			} else {
				vm.memory[thirdParameter] = 0
			}
			vm.instructionPointer = move(vm.instructionPointer, len(vm.memory), 4)
		case 99:
			// 99 means that the program is finished and should immediately halt
			break outerLoop
		default:
			fmt.Printf("unknown opcode and/or parametermode, an error has occured!")
			os.Exit(-1)
		}
	}
	return vm.output, true
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
