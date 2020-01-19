package main

import (
	"fmt"
	"github.com/adventofcode/2019/common"
	"math"
	"regexp"
	"strconv"
	"strings"
)

func main() {
	inputAsStr := string(common.ReadBytes("./day12/input.txt"))

	result1 := RunProgram(ParseInput(inputAsStr), 1000)
	//result2 := RunProgram(inputAsStr)

	fmt.Printf("Part 1 is: %.0f \n", result1)
	//fmt.Printf("Part 2 is: %d \n", result2)
}

func RunProgram(moons []Moon, timeSteps int) float64 {

	for time := 0; time < timeSteps; time++ {
		moons = updateVelocities(moons)
		moons = updatePositions(moons)
	}

	totalEnergy := calculateTotalEnergy(moons)

	return totalEnergy
}

// Update the velocity of each moon by applying gravity
func updateVelocities(moons []Moon) []Moon {

	var alreadyComparedPairs = make(map[string]bool, 6)

	// consider every distinct pair of moons:
	// 0,1
	// 0,2
	// 0,3
	// 1,2
	// 1,3
	// 2,3
	for i, firstMoon := range moons {
		for j, secondMoon := range moons {
			if firstMoon.equalsPosition(secondMoon) {
				continue // don't compare with yourself
			}

			combination1 := fmt.Sprintf("%d,%d", i, j)
			combination2 := fmt.Sprintf("%d,%d", j, i)
			_, contains1 := alreadyComparedPairs[combination1]
			_, contains2 := alreadyComparedPairs[combination2]

			// if we already compared moon 0 and moon 1, we don't want to also compare moon 1 and moon 0. That would be redundant.
			if contains1 || contains2 {
				continue
			}

			//log.Printf("Comparing moon '%d' with moon '%d'", i, j)
			alreadyComparedPairs[combination1] = true

			// apply gravity
			if firstMoon.position.x < secondMoon.position.x {
				moons[i].velocity.x++
				moons[j].velocity.x--
			} else if firstMoon.position.x > secondMoon.position.x {
				moons[i].velocity.x--
				moons[j].velocity.x++
			} else {
				// positions are the same, do nothing
			}

			if firstMoon.position.y < secondMoon.position.y {
				moons[i].velocity.y++
				moons[j].velocity.y--
			} else if firstMoon.position.y > secondMoon.position.y {
				moons[i].velocity.y--
				moons[j].velocity.y++
			} else {
				// positions are the same, do nothing
			}

			if firstMoon.position.z < secondMoon.position.z {
				moons[i].velocity.z++
				moons[j].velocity.z--
			} else if firstMoon.position.z > secondMoon.position.z {
				moons[i].velocity.z--
				moons[j].velocity.z++
			} else {
				// positions are the same, do nothing
			}
		}
	}

	return moons
}

// apply velocity: simply add the velocity of each moon to its own position.
func updatePositions(moons []Moon) []Moon {
	for i, _ := range moons {
		moons[i].position.x = moons[i].position.x + moons[i].velocity.x
		moons[i].position.y = moons[i].position.y + moons[i].velocity.y
		moons[i].position.z = moons[i].position.z + moons[i].velocity.z
	}

	return moons
}

func calculateTotalEnergy(moons []Moon) float64 {
	var totalEnergy = 0.0

	for _, moon := range moons {
		potentialEnergy := math.Abs(float64(moon.position.x)) + math.Abs(float64(moon.position.y)) + math.Abs(float64(moon.position.z))
		kineticEnergy := math.Abs(float64(moon.velocity.x)) + math.Abs(float64(moon.velocity.y)) + math.Abs(float64(moon.velocity.z))

		totalEnergy = totalEnergy + (potentialEnergy * kineticEnergy)
	}

	return totalEnergy
}

type Moon struct {
	position Coordinates
	velocity Coordinates
}

func (m *Moon) equalsPosition(other Moon) bool {
	return m.position.x == other.position.x &&
		m.position.y == other.position.y &&
		m.position.z == other.position.z
}

type Coordinates struct {
	x int
	y int
	z int
}

func ParseInput(inputAsStr string) []Moon {

	var result []Moon

	compRegEx := regexp.MustCompile("<x=(.*?), y=(.*?), z=(.*?)>")

	lines := strings.Split(inputAsStr, "\n")

	for _, line := range lines {
		match := compRegEx.FindStringSubmatch(line)

		xCoordinate, _ := strconv.Atoi(match[1])
		yCoordinate, _ := strconv.Atoi(match[2])
		zCoordinate, _ := strconv.Atoi(match[3])

		var moon = Moon{
			position: Coordinates{
				x: xCoordinate,
				y: yCoordinate,
				z: zCoordinate,
			},
			velocity: Coordinates{
				x: 0,
				y: 0,
				z: 0,
			},
		}

		result = append(result, moon)
	}

	return result
}
