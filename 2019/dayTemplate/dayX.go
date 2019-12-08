package main

import (
	"fmt"
	"github.com/adventofcode/2019/common"
)

func main() {
	inputAsStr := string(common.ReadBytes("./dayX/input.txt"))

	result1 := RunProgram(inputAsStr)
	result2 := RunProgram(inputAsStr)

	fmt.Printf("Part 1 is: %d \n", result1)
	fmt.Printf("Part 2 is: %d \n", result2)
}

func RunProgram(input string) int {
	return 0
}
