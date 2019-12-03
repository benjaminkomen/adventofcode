package main

import (
	"fmt"
	"github.com/adventofcode/2019/common"
	"os"
	"strconv"
)

func main() {
	scanner := common.ReadLines("./day1/input.txt")

	var totalFuelRequired int64          // part 1
	var totalRecursiveFuelRequired int64 // part 2

	for scanner.Scan() {
		inputNumber, err := strconv.Atoi(scanner.Text())
		if err != nil {
			fmt.Printf("could not convert string to number: %s", err)
			os.Exit(-1)
		}

		fuelRequired := CalculateFuelRequired(inputNumber)
		totalFuelRequired += int64(fuelRequired)

		recursiveFuelRequired := CalculateRecursiveFuelRequired(inputNumber)
		totalRecursiveFuelRequired += int64(recursiveFuelRequired)
	}

	fmt.Printf("Part 1: total fuel required is: %d \n", totalFuelRequired)
	fmt.Printf("Part 2: total recursive fuel required is: %d \n", totalRecursiveFuelRequired)

}

func CalculateRecursiveFuelRequired(moduleMass int) int {

	fuelRequired := CalculateFuelRequired(moduleMass)

	// Any mass that would require negative fuel should instead be treated as if it requires zero fuel
	if fuelRequired <= 0 {
		return 0
	}

	return fuelRequired + CalculateRecursiveFuelRequired(fuelRequired)
}

/*
Specifically, to find the fuel required for a module, take its mass, divide by three, round down, and subtract 2.
*/
func CalculateFuelRequired(moduleMass int) int {
	dividedMass := moduleMass / 3 // NOTE: Go automatically rounds this down, the result is an int
	return dividedMass - 2
}
