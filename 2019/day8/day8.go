package main

import (
	"fmt"
	"github.com/adventofcode/2019/common"
	"os"
	"strconv"
	"strings"
)

func main() {
	inputAsStr := string(common.ReadBytes("./day8/input.txt"))

	result1 := RunProgram(inputAsStr, 25, 6)
	//result2 := RunProgram(inputAsStr, 25, 6)

	fmt.Printf("Part 1 is: %d \n", result1)
	//fmt.Printf("Part 2 is: %d \n", result2)
}

func RunProgram(input string, imageWidth int, imageHeight int) int {
	layerArea := imageWidth * imageHeight
	imageLayers := makeLayeredImage(input, layerArea)
	imageLayerWithFewestZeroDigits := findLayerWithMinimum(imageLayers, 0)
	numberOfOneDigits := countNumberInLayer(imageLayerWithFewestZeroDigits, 1)
	numberOfTwoDigits := countNumberInLayer(imageLayerWithFewestZeroDigits, 2)

	return numberOfOneDigits * numberOfTwoDigits
}

type layeredImage [][]int

func makeLayeredImage(input string, layerArea int) layeredImage {
	layerCounter := 0
	inputAsSlice := strings.Split(input, "")
	amountOfLayers := len(inputAsSlice) / layerArea
	var result = make([][]int, amountOfLayers)

	for i, digit := range inputAsSlice {
		digitAsInt, err := strconv.Atoi(digit)
		if err != nil {
			fmt.Println(err)
			os.Exit(-1)
		}

		if result[layerCounter] == nil {
			result[layerCounter] = []int{}
		}

		result[layerCounter] = append(result[layerCounter], digitAsInt)

		if (i + 1) >= (layerArea * (layerCounter + 1)) {
			layerCounter++
		}
	}
	return result
}

func findLayerWithMinimum(layers layeredImage, number int) []int {
	currentMinimumAmount := int(^uint(0) >> 1)
	var currentLayer []int

	for _, layer := range layers {
		currentAmount := countNumberInLayer(layer, number)
		if currentAmount < currentMinimumAmount {
			currentLayer = layer
			currentMinimumAmount = currentAmount
		}
	}
	return currentLayer
}

func countNumberInLayer(layer []int, number int) int {
	var amount int

	for _, digit := range layer {
		if digit == number {
			amount++
		}
	}
	return amount
}
