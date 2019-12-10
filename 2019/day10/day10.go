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

		deltaXToOtherAsteroid := float64(otherAsteroid.X - a.X)
		signDeltaXToOtherAsteroid := math.Signbit(deltaXToOtherAsteroid)
		absDeltaXToOtherAsteroid := math.Abs(deltaXToOtherAsteroid)

		deltaXToSomeAsteroid := float64(someAsteroid.X - a.X)
		signDeltaXToSomeAsteroid := math.Signbit(deltaXToSomeAsteroid)
		absDeltaXToSomeAsteroid := math.Abs(deltaXToSomeAsteroid)

		deltaYToOtherAsteroid := float64(otherAsteroid.Y - a.Y)
		signDeltaYToOtherAsteroid := math.Signbit(deltaYToOtherAsteroid)
		absDeltaYToOtherAsteroid := math.Abs(deltaYToOtherAsteroid)

		deltaYToSomeAsteroid := float64(someAsteroid.Y - a.Y)
		signDeltaYToSomeAsteroid := math.Signbit(deltaYToSomeAsteroid)
		absDeltaYToSomeAsteroid := math.Abs(deltaYToSomeAsteroid)

		if comparisonX == "NaN" && a.X == someAsteroid.X && absDeltaYToSomeAsteroid < absDeltaYToOtherAsteroid && signDeltaYToOtherAsteroid == signDeltaYToSomeAsteroid {
			// the lineOfSight is a vertical line with an infinite slope
			return false
		} else if a.Y == otherAsteroid.Y && a.Y == someAsteroid.Y && absDeltaXToSomeAsteroid < absDeltaXToOtherAsteroid && signDeltaXToOtherAsteroid == signDeltaXToSomeAsteroid {
			// the lineOfSight is a horizontal line
			return false
		} else if comparisonX == comparisonY && absDeltaXToSomeAsteroid < absDeltaXToOtherAsteroid && absDeltaYToSomeAsteroid < absDeltaYToOtherAsteroid && signDeltaXToOtherAsteroid == signDeltaXToSomeAsteroid && signDeltaYToOtherAsteroid == signDeltaYToSomeAsteroid {
			// there is SOME asteroid in the line-of-sight BETWEEN (not further away AND in the same direction) our asteroid of interest and the other asteroid, they cannot detect each other
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
