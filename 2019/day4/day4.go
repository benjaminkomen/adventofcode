package main

import (
	"fmt"
	"strconv"
	"strings"
)

func main() {
	inputLower, inputUpper := 240920, 789857

	result1 := RunProgram(inputLower, inputUpper)
	//result2 := RunProgram2(inputNumbers)

	fmt.Printf("Part 1 is: %d \n", result1)
	//fmt.Printf("Part 2 is: %d \n", result2)

}

func RunProgram(inputLower, inputUpper int) int {

	numberOfPasswords := 0

	for i := inputLower; i < (inputUpper + 1); i++ {
		if MeetsConditions(i) {
			numberOfPasswords++
		}
	}

	return numberOfPasswords
}

func MeetsConditions(number int) bool {
	numberSliced := intToIntSlice(number)
	return TwoSameAdjacentDigits(numberSliced) && DigitsNeverDecrease(numberSliced)
}

func TwoSameAdjacentDigits(numberSliced []int) bool {
	for i := 0; i < (len(numberSliced) - 1); i++ {
		if numberSliced[i] == numberSliced[i+1] {
			return true
		}
	}

	return false
}

func DigitsNeverDecrease(numberSliced []int) bool {
	for i := 0; i < (len(numberSliced) - 1); i++ {
		if numberSliced[i+1] < numberSliced[i] {
			return false
		}
	}

	return true
}

func intToIntSlice(number int) []int {
	str := strconv.Itoa(number)
	splitStr := strings.Split(str, "")

	var result []int

	for _, strDigit := range splitStr {
		digit, _ := strconv.Atoi(strDigit)
		result = append(result, digit)
	}

	return result
}
