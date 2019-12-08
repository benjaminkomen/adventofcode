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

	result1 := RunProgram(MakeLayeredImage(inputAsStr, 25, 6))
	result2 := RunProgram2(MakeLayeredImage(inputAsStr, 25, 6))

	fmt.Printf("Part 1 is: %d \n", result1)
	fmt.Printf("Part 2 is: %d \n", result2)
}

func RunProgram(imageLayers LayeredImage) int {
	imageLayerWithFewestZeroDigits := findLayerWithMinimum(imageLayers, 0)
	numberOfOneDigits := countNumberInLayer(imageLayerWithFewestZeroDigits, 1)
	numberOfTwoDigits := countNumberInLayer(imageLayerWithFewestZeroDigits, 2)

	return numberOfOneDigits * numberOfTwoDigits
}

func RunProgram2(imageLayers LayeredImage) []int {
	decodedImage := decodeImage(imageLayers)
	visualiseImage(decodedImage)
	return decodedImage
}

type Image []int
type LayeredImage []Image

func MakeLayeredImage(input string, imageWidth int, imageHeight int) LayeredImage {
	layerArea := imageWidth * imageHeight
	layerCounter := 0
	inputAsSlice := strings.Split(input, "")
	amountOfLayers := len(inputAsSlice) / layerArea
	var result = make(LayeredImage, amountOfLayers)

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

func findLayerWithMinimum(layers LayeredImage, number int) Image {
	currentMinimumAmount := int(^uint(0) >> 1)
	var currentImageLayer Image

	for _, layer := range layers {
		currentAmount := countNumberInLayer(layer, number)
		if currentAmount < currentMinimumAmount {
			currentImageLayer = layer
			currentMinimumAmount = currentAmount
		}
	}
	return currentImageLayer
}

func countNumberInLayer(layer Image, number int) int {
	var amount int

	for _, digit := range layer {
		if digit == number {
			amount++
		}
	}
	return amount
}

func decodeImage(layers LayeredImage) Image {
	decodedImage := layers[0]

	for _, layer := range layers {
		for pos, pixel := range layer {
			// replace transparent upper layer with current layer's pixel, otherwise leave the upper layer as the visible pixel
			if decodedImage[pos] == 2 {
				decodedImage[pos] = pixel
			}
		}
	}
	return decodedImage
}

func visualiseImage(image Image) {

}
