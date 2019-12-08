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

func RunProgram2(layeredImage LayeredImage) []int {
	layeredImage.decodeImage()
	layeredImage.visualiseImage()

	return layeredImage.DecodedImage
}

type Image []int

type LayeredImage struct {
	Width        int
	Height       int
	ImageLayers  []Image
	DecodedImage Image
}

func MakeLayeredImage(input string, imageWidth int, imageHeight int) LayeredImage {
	layerArea := imageWidth * imageHeight
	layerCounter := 0
	inputAsSlice := strings.Split(input, "")
	amountOfLayers := len(inputAsSlice) / layerArea

	var result = LayeredImage{
		Width:        imageWidth,
		Height:       imageHeight,
		ImageLayers:  make([]Image, amountOfLayers),
		DecodedImage: nil,
	}

	for i, digit := range inputAsSlice {
		digitAsInt, err := strconv.Atoi(digit)
		if err != nil {
			fmt.Println(err)
			os.Exit(-1)
		}

		if result.ImageLayers[layerCounter] == nil {
			result.ImageLayers[layerCounter] = []int{}
		}

		result.ImageLayers[layerCounter] = append(result.ImageLayers[layerCounter], digitAsInt)

		if (i + 1) >= (layerArea * (layerCounter + 1)) {
			layerCounter++
		}
	}
	return result
}

func findLayerWithMinimum(layeredImage LayeredImage, number int) Image {
	currentMinimumAmount := int(^uint(0) >> 1)
	var currentImageLayer Image

	for _, layer := range layeredImage.ImageLayers {
		currentAmount := countNumberInLayer(layer, number)
		if currentAmount < currentMinimumAmount {
			currentImageLayer = layer
			currentMinimumAmount = currentAmount
		}
	}
	return currentImageLayer
}

func countNumberInLayer(imageLayer Image, number int) int {
	var amount int

	for _, digit := range imageLayer {
		if digit == number {
			amount++
		}
	}
	return amount
}

func (li *LayeredImage) decodeImage() {
	li.DecodedImage = make([]int, len(li.ImageLayers[0]))
	copy(li.DecodedImage, li.ImageLayers[0])

	for _, layer := range li.ImageLayers {
		for pos, pixel := range layer {
			// replace transparent upper layer with current layer's pixel, otherwise leave the upper layer as the visible pixel
			if li.DecodedImage[pos] == 2 {
				li.DecodedImage[pos] = pixel
			}
		}
	}
}

func (li *LayeredImage) imageArea() int {
	return li.Width * li.Height
}

func (li *LayeredImage) visualiseImage() {

	rowCounter := 0
	var currentRow strings.Builder

	for i, pixel := range li.DecodedImage {
		switch pixel {
		case 0:
			currentRow.WriteString(" ")
		case 1:
			currentRow.WriteString("x")
		case 2:
			currentRow.WriteString(" ")
		default:
			fmt.Printf("Error! Unexpected pixel coding: %d", pixel)
			os.Exit(-1)
		}

		if (i+1)%li.Width == 0 {
			rowCounter++
			fmt.Println(currentRow.String())
			currentRow.Reset()
		}
	}
}
