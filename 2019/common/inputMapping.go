package common

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func ToIntSlice(scanner *bufio.Scanner) []int {
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

func IntToIntSlice(number int) []int {
	str := strconv.Itoa(number)
	splitStr := strings.Split(str, "")

	var result []int

	for _, strDigit := range splitStr {
		digit, _ := strconv.Atoi(strDigit)
		result = append(result, digit)
	}

	return result
}

func IntSliceToInt(slice []int) int {
	result, err := strconv.Atoi(strings.Trim(strings.Join(strings.Split(fmt.Sprint(slice), " "), ""), "[]"))
	if err != nil {
		fmt.Printf("Error during conversion of int slice to int: %s", err)
		os.Exit(-1)
	}
	return result
}
