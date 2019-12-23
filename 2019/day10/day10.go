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

	twohunderthAsteroid := vaporizedAsteroids[numberToFind-1] // because 0-indexed

	return (twohunderthAsteroid.X * 100) + twohunderthAsteroid.Y
}

func vaporizeAsteroids(bestAsteroid Asteroid, asteroids []Asteroid) []Asteroid {

	asteroids = removeAsteroidFromAsteroidSlice(asteroids, bestAsteroid)

	for i, otherAsteroid := range asteroids {
		otherAsteroid.calculateDistances(bestAsteroid)
		asteroids[i] = otherAsteroid
	}

	/* sort by:
	   - quadrant ASC
	   - deltaX   ASC
	   - deltaY   ASC
	   - distance ASC
	*/
	// TODO sort correctly
	sort.Slice(asteroids, func(i, j int) bool {
		return asteroids[i].Quadrant < asteroids[j].Quadrant &&
			asteroids[i].DeltaX < asteroids[j].DeltaX &&
			asteroids[i].DeltaY > asteroids[j].DeltaY &&
			asteroids[i].DistanceToBestAsteroid < asteroids[j].DistanceToBestAsteroid
	})

	var i int
	round := 1
	for {
		// condition to break loop
		if allAsteroidsVaporized(asteroids) {
			break
		}

		// increase loop counter, at the end of the round we start over
		if i < len(asteroids) {
			i++
		} else {
			i = 0
			round++
		}

		otherAsteroid := asteroids[i]

		// not yet vaporized
		// TODO and should be visible, i.e. not blocked by some asteroid which has the direction but a smaller distance and not yet vaporized
		if otherAsteroid.VaporizedInRound == 0 {
			otherAsteroid.VaporizedInRound = round
		}
	}

	return asteroids
}

type Asteroid struct {
	X                            int
	Y                            int
	NumberOfAsteroidsItCanDetect int
	DeltaX                       float64
	DeltaY                       float64
	DistanceToBestAsteroid       float64
	Quadrant                     int // 0, 1, 2, 3   clockwise: first, second, third or fourth quadrant
	VaporizedInRound             int
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

func (a *Asteroid) calculateDistances(bestAsteroid Asteroid) {

	deltaXToBestAsteroid := float64(a.X - bestAsteroid.X)
	xIsNegative := math.Signbit(deltaXToBestAsteroid)
	absDeltaXToBestAsteroid := math.Abs(deltaXToBestAsteroid)

	deltaYToBestAsteroid := float64(a.Y - bestAsteroid.Y)
	yIsNegative := math.Signbit(deltaYToBestAsteroid)
	absDeltaYToBestAsteroid := math.Abs(deltaYToBestAsteroid)

	a.DeltaX = absDeltaXToBestAsteroid
	a.DeltaY = absDeltaYToBestAsteroid
	a.DistanceToBestAsteroid = math.Sqrt((absDeltaXToBestAsteroid * absDeltaXToBestAsteroid) + (absDeltaYToBestAsteroid * absDeltaYToBestAsteroid))

	// Determine Quadrant
	if !xIsNegative && !yIsNegative {
		a.Quadrant = 0
	} else if !xIsNegative && yIsNegative {
		a.Quadrant = 1
	} else if xIsNegative && !yIsNegative {
		a.Quadrant = 2
	} else if xIsNegative && yIsNegative {
		a.Quadrant = 3
	}
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

func removeAsteroidFromAsteroidSlice(asteroids []Asteroid, asteroidToRemove Asteroid) []Asteroid {

	var index int

	for i, asteroid := range asteroids {
		if asteroid.equals(asteroidToRemove) {
			index = i
		}
	}

	return append(asteroids[:index], asteroids[index+1:]...)
}

func allAsteroidsVaporized(asteroids []Asteroid) bool {
	for _, asteroid := range asteroids {
		if asteroid.VaporizedInRound == 0 {
			return false
		}
	}
	return true
}
