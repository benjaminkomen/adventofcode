package main

import (
	"fmt"
	"github.com/adventofcode/2019/common"
)

func main() {
	input := common.ToIntSlice(common.ReadLines("./day7/input.txt"))

	result1 := RunProgramOnAmplifier(input)
	result2 := RunProgramOnAmplifier2(input)

	fmt.Printf("Part 1 is: %d \n", result1)
	fmt.Printf("Part 2 is: %d \n", result2)

}

func RunProgramOnAmplifier(amplifierControllerSoftware []int) int {
	var highestSignal int
	var currentSignal int

	for _, permutation := range common.Permutations([]int{0, 1, 2, 3, 4}) {
		currentSignal = TryCombination(permutation, amplifierControllerSoftware)

		if currentSignal > highestSignal {
			highestSignal = currentSignal
		}
	}

	return highestSignal
}

func TryCombination(permutation []int, amplifierControllerSoftware []int) int {
	var output int
	inputSignal := 0

	for _, phaseSetting := range permutation {
		freshInput := make([]int, len(amplifierControllerSoftware))
		copy(freshInput, amplifierControllerSoftware)
		myComputer := createIntcodeComputer(freshInput)
		output, _ = myComputer.Run([]int{phaseSetting, inputSignal})
		inputSignal = output
	}
	return output
}

func RunProgramOnAmplifier2(amplifierControllerSoftware []int) int {
	var highestSignal int
	var currentSignal int

	for _, permutation := range common.Permutations([]int{5, 6, 7, 8, 9}) {
		currentSignal = TryCombination2(permutation, amplifierControllerSoftware)

		if currentSignal > highestSignal {
			highestSignal = currentSignal
		}
	}

	return highestSignal
}

func TryCombination2(permutation []int, amplifierControllerSoftware []int) int {
	currentSignal := 0
	var ampIsDone bool
	var amplifiers []*IntcodeComputerInstance

	// setup amplifiers
	for _, phaseSetting := range permutation {
		intcodeComputer := createIntcodeComputer(amplifierControllerSoftware)
		amplifiers = append(amplifiers, &intcodeComputer)
		currentSignal, ampIsDone = intcodeComputer.Run([]int{phaseSetting, currentSignal})
	}

	var amplifierIndex int
	highestSignal := currentSignal

	// loop until output is currentSignal, for this given permutation, assuming the output get higher each loop
	for {
		currentSignal, ampIsDone = amplifiers[amplifierIndex].Run([]int{currentSignal})

		if ampIsDone {
			break
		}

		// increase amplifierIndex from 0 to 4 and then starting again with 0
		if amplifierIndex == 4 {
			amplifierIndex = 0
		} else {
			amplifierIndex++
		}
	}

	return highestSignal
}
