package main

import (
	"fmt"
	"github.com/adventofcode/2019/common"
	"math"
	"strings"
)

func main() {
	inputAsStr := string(common.ReadBytes("./day10/input.txt"))

	result1 := RunProgram(ParseInput(inputAsStr))
	//result2 := RunProgram(inputAsStr)

	fmt.Printf("Part 1 is: %d \n", result1)
	//fmt.Printf("Part 2 is: %d \n", result2)
}

func RunProgram(asteroids []Asteroid) int {

	var maxNumberOfAsteroids int
	var computedAsteroids []Asteroid

	for _, asteroid := range asteroids {
		asteroid.determineNumberOfAsteroidsItCanDetect(asteroids)

		computedAsteroids = append(computedAsteroids, asteroid)

		if asteroid.NumberOfAsteroidsItCanDetect > maxNumberOfAsteroids {
			maxNumberOfAsteroids = asteroid.NumberOfAsteroidsItCanDetect
		}
	}

	return maxNumberOfAsteroids
}

type Asteroid struct {
	X                            int
	Y                            int
	NumberOfAsteroidsItCanDetect int
}

func (a *Asteroid) equals(other Asteroid) bool {
	return a.X == other.X &&
		a.Y == other.Y
}

func (a *Asteroid) determineNumberOfAsteroidsItCanDetect(asteroids []Asteroid) {

	for _, otherAsteroid := range asteroids {
		if a.equals(otherAsteroid) {
			continue // skip comparing with yourself obviously
		}

		if a.canDetectOtherAsteroid(otherAsteroid, asteroids) {
			a.NumberOfAsteroidsItCanDetect++
		}
	}
}

func (a *Asteroid) canDetectOtherAsteroid(otherAsteroid Asteroid, asteroids []Asteroid) bool {

	lineOfSight := computeLineOfSight(*a, otherAsteroid)

	for _, someAsteroid := range asteroids {
		if a.equals(someAsteroid) || otherAsteroid.equals(someAsteroid) {
			continue // again, not interested in comparing with ourselves
		}

		comparisonX := fmt.Sprintf("%.2f", lineOfSight(float64(someAsteroid.X)))
		comparisonY := fmt.Sprintf("%.2f", float64(someAsteroid.Y))

		deltaXToOtherAsteroid := math.Abs(float64(otherAsteroid.X - a.X))
		deltaXToSomeAsteroid := math.Abs(float64(someAsteroid.X - a.X))
		deltaYToOtherAsteroid := math.Abs(float64(otherAsteroid.Y - a.Y))
		deltaYToSomeAsteroid := math.Abs(float64(someAsteroid.X - a.Y))
		if comparisonX == comparisonY && deltaXToSomeAsteroid < deltaXToOtherAsteroid && deltaYToSomeAsteroid < deltaYToOtherAsteroid {
			// there is SOME asteroid in the line-of-sight BETWEEN (not further away) our asteroid of interest and the other asteroid, they cannot detect each other
			return false
		}
	}

	return true
}

func computeLineOfSight(firstAsteroid Asteroid, secondAsteroid Asteroid) func(x float64) float64 {
	x1 := float64(firstAsteroid.X)
	x2 := float64(secondAsteroid.X)
	y1 := float64(firstAsteroid.Y)
	y2 := float64(secondAsteroid.Y)

	slope := (y2 - y1) / (x2 - x1)

	y := func(x float64) float64 {
		return (slope * x) - (slope * x1) + y1
	}

	return y
}

func ParseInput(inputAsStr string) []Asteroid {

	var result []Asteroid

	lines := strings.Split(inputAsStr, "\n")

	for yCounter, line := range lines {
		columns := strings.Split(line, "")

		for xCounter, column := range columns {

			if column == "#" {
				asteroid := Asteroid{
					X: xCounter,
					Y: yCounter,
				}

				result = append(result, asteroid)
			}
		}
	}

	return result
}
