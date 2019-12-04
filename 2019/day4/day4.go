package main

import (
	"fmt"
	"strconv"
	"strings"
)

func main() {
	inputLower, inputUpper := 240920, 789857

	result1 := RunProgram(inputLower, inputUpper)
	result2 := RunProgram2(inputLower, inputUpper)

	fmt.Printf("Part 1 is: %d \n", result1)
	fmt.Printf("Part 2 is: %d \n", result2)

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

func RunProgram2(inputLower, inputUpper int) int {

	numberOfPasswords := 0

	for i := inputLower; i < (inputUpper + 1); i++ {
		if MeetsConditions2(i) {
			numberOfPasswords++
		}
	}

	return numberOfPasswords
}

func MeetsConditions(number int) bool {
	numberSliced := intToIntSlice(number)
	return TwoSameAdjacentDigits(numberSliced) && DigitsNeverDecrease(numberSliced)
}

func MeetsConditions2(number int) bool {
	numberSliced := intToIntSlice(number)
	return TwoSameAdjacentDigitsNotPartOfLargerGroup(numberSliced) && DigitsNeverDecrease(numberSliced)
}

func TwoSameAdjacentDigits(numberSliced []int) bool {
	for i := 0; i < (len(numberSliced) - 1); i++ {
		if numberSliced[i] == numberSliced[i+1] {
			return true
		}
	}

	return false
}

/* The point is: if we find ANY pair of adjacent equal digits, we must make sure that:
- the digit before the pair is not the same as the pair
- the digit after the pair is not the same as the pair

Then we are good to go, the rest of the digits don't matter anymore.
*/
func TwoSameAdjacentDigitsNotPartOfLargerGroup(numberSliced []int) bool {
	for i := 0; i < (len(numberSliced) - 1); i++ {
		if numberSliced[i] == numberSliced[i+1] && digitBeforePairIsNotTheSame(i, numberSliced) && digitAfterPairIsNotTheSame(i, numberSliced) {
			return true
		}
	}
	return false
}

func digitBeforePairIsNotTheSame(i int, numberSliced []int) bool {
	if i > 0 {
		// we are not at the first digit, so we can safely look one digit to the left
		return numberSliced[i-1] != numberSliced[i]
	}
	return true // there is no digit to the left
}

func digitAfterPairIsNotTheSame(i int, numberSliced []int) bool {
	if i < (len(numberSliced) - 2) {
		// we are not at the but-last digit, so we can safely look two digits to the right
		return numberSliced[i+2] != numberSliced[i+1]
	}
	return true // there is no digit two places to the right
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
