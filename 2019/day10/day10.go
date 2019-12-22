package main

import (
	"fmt"
	"github.com/adventofcode/2019/common"
	"math"
	"sort"
	"strings"
)

func main() {
	inputAsStr := string(common.ReadBytes("./day10/input.txt"))

	asteroids := ParseInput(inputAsStr)
	result1 := RunProgram(asteroids)
	result2 := RunProgram2(result1, asteroids, 200)

	fmt.Printf("Part 1 is: %d at location (%d,%d) \n", result1.NumberOfAsteroidsItCanDetect, result1.X, result1.Y)
	fmt.Printf("Part 2 is: %d \n", result2)
}

func RunProgram(asteroids []Asteroid) Asteroid {

	var asteroidOnBestLocation Asteroid
	var computedAsteroids []Asteroid

	for _, asteroid := range asteroids {
		asteroid.determineNumberOfAsteroidsItCanDetect(asteroids)

		computedAsteroids = append(computedAsteroids, asteroid)

		if asteroid.NumberOfAsteroidsItCanDetect > asteroidOnBestLocation.NumberOfAsteroidsItCanDetect {
			asteroidOnBestLocation = asteroid
		}
	}

	return asteroidOnBestLocation
}

func RunProgram2(bestAsteroid Asteroid, allAsteroids []Asteroid, numberToFind int) int {

	vaporizedAsteroids := vaporizeAsteroids(bestAsteroid, allAsteroids)

	found, twohunderthAsteroid := findVaporizedAsteroid(vaporizedAsteroids, numberToFind)
	if !found {
		return 0
	}

	return (twohunderthAsteroid.X * 100) + twohunderthAsteroid.Y
}

/* TODO implement this method:
- loop over asteroids and vaporize them (by setting the vaporize position and removing them from the current list and add it to the vaporizedAsteroids list), if they are visible
- return list of vaporized asteroids
*/
func vaporizeAsteroids(bestAsteroid Asteroid, asteroids []Asteroid) []Asteroid {

	var vaporizedAsteroids = make([]Asteroid, len(asteroids))

	for _, otherAsteroid := range asteroids {
		if bestAsteroid.equals(otherAsteroid) {
			continue // skip comparing with yourself obviously
		}

		otherAsteroid.calculateAngleToVerticalPlane(bestAsteroid)
	}

	sort.Slice(asteroids, func(i, j int) bool {
		return asteroids[i].Angle < asteroids[j].Angle
	})

	for _, asteroid := range asteroids {
		// if asteroid is visible, vaporize it and add
		vaporizedAsteroids = append(vaporizedAsteroids, asteroid)
	}

	return vaporizedAsteroids
}

type Asteroid struct {
	X                            int
	Y                            int
	NumberOfAsteroidsItCanDetect int
	Angle                        float64 // in radians (or degrees?)
	VaporizePosition             int
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

func (a *Asteroid) calculateAngleToVerticalPlane(otherAsteroid Asteroid) {
	tan := (otherAsteroid.X - a.X) / (otherAsteroid.Y - a.Y)
	radians := math.Atan(float64(tan))
	degrees := radiansToDegrees(radians)

	XIsNegative := math.Signbit(float64(otherAsteroid.X - a.X))
	YIsNegative := math.Signbit(float64(otherAsteroid.Y - a.Y))

	var adjustedDegrees float64

	if !XIsNegative && !YIsNegative {
		adjustedDegrees = degrees
	} else if !XIsNegative && YIsNegative {
		adjustedDegrees = degrees + 90
	} else if XIsNegative && !YIsNegative {
		adjustedDegrees = degrees + 180
	} else if XIsNegative && YIsNegative {
		adjustedDegrees = degrees + 270
	}

	a.Angle = adjustedDegrees
}

func radiansToDegrees(radians float64) float64 {
	return radians * 180 / math.Pi
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

func findVaporizedAsteroid(asteroids []Asteroid, numberToFind int) (bool, Asteroid) {
	for _, asteroid := range asteroids {
		if asteroid.VaporizePosition == numberToFind {
			return true, asteroid
		}
	}

	return false, Asteroid{}
}
