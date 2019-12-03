package main

import (
	"fmt"
	"github.com/adventofcode/2019/common"
	"math"
	"os"
	"sort"
	"strconv"
	"strings"
)

func main() {
	inputAsStr := string(common.ReadBytes("./day3/input.txt"))
	inputNumbers := PrepareInput(inputAsStr)

	result1 := RunProgram(inputNumbers)
	result2 := 0

	fmt.Printf("Part 1 is: %d \n", result1)
	fmt.Printf("Part 2 is: %d \n", result2)

}

type coordinate struct {
	x int
	y int
}

func (c *coordinate) Right() {
	c.x++
}

func (c *coordinate) Down() {
	c.y--
}

func (c *coordinate) Left() {
	c.x--
}

func (c *coordinate) Up() {
	c.y++
}

type Wire int

const (
	Wire_nothing Wire = iota
	Wire_first
	Wire_second
	Wire_both
)

func RunProgram(input map[int][]instruction) int {
	grid := make(map[coordinate]Wire) // every position can have: 0 (nothing), 1 (the first wire), 2 (the second wire) or 3 (both wires)
	var intersections []coordinate    // locations where both wires cross
	var currentCoordinate coordinate

	for w, instructions := range input {
		var wire Wire
		if w == 0 {
			wire = Wire_first
		} else if w == 1 {
			wire = Wire_second
		}

		for i, instruction := range instructions {
			grid, currentCoordinate, intersections = instruction.apply(grid, intersections, currentCoordinate, wire, i)
		}
	}

	return closestToOrigin(intersections)
}

type instruction struct {
	direction string
	amount    int
}

func (instruction instruction) apply(grid map[coordinate]Wire, intersections []coordinate, currentCoordinate coordinate, wire Wire, instructionNumber int) (map[coordinate]Wire, coordinate, []coordinate) {
	// set current coordinate to the origin when starting with a new wire
	if instructionNumber == 0 {
		currentCoordinate = coordinate{
			x: 0,
			y: 0,
		}
	}

	for i := 0; i < instruction.amount; i++ {
		switch instruction.direction {
		case "R":
			currentCoordinate.Right()
		case "D":
			currentCoordinate.Down()
		case "L":
			currentCoordinate.Left()
		case "U":
			currentCoordinate.Up()
		default:
			fmt.Printf("unsupported instruction direction %q", instruction.direction)
			os.Exit(-1)
		}

		wireAtCurrentLocation := grid[currentCoordinate]
		if wireAtCurrentLocation == Wire_nothing {
			grid[currentCoordinate] = wire
		} else if wireAtCurrentLocation == wire {
			// do nothing, we are crossing ourselves
		} else if wireAtCurrentLocation != wire {
			grid[currentCoordinate] = Wire_both
			intersections = append(intersections, currentCoordinate) // this is what we are actually interested in
		}
	}

	return grid, currentCoordinate, intersections
}

func closestToOrigin(intersections []coordinate) int {
	var manhattanDistances []int

	for _, intersection := range intersections {
		mDistance := math.Abs(float64(intersection.x-0)) + math.Abs(float64(intersection.y-0))
		manhattanDistances = append(manhattanDistances, int(mDistance))
	}

	sort.Ints(manhattanDistances) // ascending, lowest number first

	if len(manhattanDistances) > 0 {
		return manhattanDistances[0]
	}
	return 0
}

// returns a map of key = wire number, value = list of instructions
func PrepareInput(input string) map[int][]instruction {
	result := make(map[int][]instruction, 2)

	parts := strings.Split(input, "\n")

	for counter, part := range parts {
		inputStrings := strings.Split(part, ",")

		var instructions []instruction

		for _, inputString := range inputStrings {

			direction := string(inputString[0])
			amount, _ := strconv.Atoi(inputString[1:])

			instruction := instruction{
				direction: direction,
				amount:    amount,
			}

			instructions = append(instructions, instruction)
		}

		result[counter] = instructions
	}
	return result
}
