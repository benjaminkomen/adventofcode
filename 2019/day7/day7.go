package main

import (
	"fmt"
	"github.com/adventofcode/2019/common"
)

func main() {
	input := common.ToIntSlice(common.ReadLines("./day7/input.txt"))

	result1 := RunProgramOnAmplifier(input)
	//result2 := IntcodeComputer(common.ToIntSlice(common.ReadLines("./day5/input.txt")), 5)

	fmt.Printf("Part 1 is: %d \n", result1)
	//fmt.Printf("Part 2 is: %d \n", result2)

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
		output = IntcodeComputer(freshInput, []int{phaseSetting, inputSignal})
		inputSignal = output
	}
	return output
}
