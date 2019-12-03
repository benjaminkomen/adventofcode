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
	result2 := RunProgram2(inputNumbers)

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

type WireAndSteps struct {
	Wire  Wire
	Steps int
}

type intersectionTravelled struct {
	coordinate
	steps int
}

func RunProgram(input map[int][]instruction) int {
	grid := make(map[coordinate]WireAndSteps) // every position can have: 0 (nothing), 1 (the first wire), 2 (the second wire) or 3 (both wires)
	var intersections []intersectionTravelled // locations where both wires cross
	var currentCoordinate coordinate

	for w, instructions := range input {
		var wire Wire
		if w == 0 {
			wire = Wire_first
		} else if w == 1 {
			wire = Wire_second
		}

		for i, instruction := range instructions {
			grid, currentCoordinate, intersections, _ = instruction.apply(grid, intersections, 0, currentCoordinate, wire, i)
		}
	}

	return closestToOrigin(intersections)
}

func RunProgram2(input map[int][]instruction) int {
	grid := make(map[coordinate]WireAndSteps) // every position can have: 0 (nothing), 1 (the first wire), 2 (the second wire) or 3 (both wires)
	var intersections []intersectionTravelled // locations where both wires cross
	var currentCoordinate coordinate
	var stepsTravelled int

	for w, instructions := range input {
		var wire Wire
		if w == 0 {
			wire = Wire_first
		} else if w == 1 {
			wire = Wire_second
		}

		for i, instruction := range instructions {
			grid, currentCoordinate, intersections, stepsTravelled = instruction.apply(grid, intersections, stepsTravelled, currentCoordinate, wire, i)
		}
	}

	return leastTravelled(intersections)
}

type instruction struct {
	direction string
	amount    int
}

func (instruction instruction) apply(grid map[coordinate]WireAndSteps, intersections []intersectionTravelled, travelled int, currentCoordinate coordinate, wire Wire, instructionNumber int) (map[coordinate]WireAndSteps, coordinate, []intersectionTravelled, int) {
	// set current coordinate to the origin when starting with a new wire
	if instructionNumber == 0 {
		currentCoordinate = coordinate{
			x: 0,
			y: 0,
		}
		travelled = 0
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

		travelled++

		wireAtCurrentLocation := grid[currentCoordinate]
		if wireAtCurrentLocation.Wire == Wire_nothing {
			grid[currentCoordinate] = WireAndSteps{
				Wire:  wire,
				Steps: travelled,
			}
		} else if wireAtCurrentLocation.Wire == wire {
			// do nothing, we are crossing ourselves
		} else if wireAtCurrentLocation.Wire != wire {

			existingGridElement := grid[currentCoordinate]

			grid[currentCoordinate] = WireAndSteps{
				Wire:  Wire_both,
				Steps: existingGridElement.Steps,
			}

			it := intersectionTravelled{
				coordinate: currentCoordinate,
				steps:      grid[currentCoordinate].Steps + travelled, // steps by first wire and steps by second wire
			}
			intersections = append(intersections, it) // this is what we are actually interested in
		}
	}

	return grid, currentCoordinate, intersections, travelled
}

func closestToOrigin(intersections []intersectionTravelled) int {
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

func leastTravelled(intersections []intersectionTravelled) int {

	var travelDistances []int

	for _, intersection := range intersections {
		travelDistances = append(travelDistances, intersection.steps)
	}

	sort.Ints(travelDistances) // ascending, lowest number first

	if len(travelDistances) > 0 {
		return travelDistances[0]
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
