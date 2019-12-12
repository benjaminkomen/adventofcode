package main

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestRunProgram(t *testing.T) {

	sampleInput1 := `.#..#
.....
#####
....#
...##`

	assert.Equal(t, 8, RunProgram(ParseInput(sampleInput1)).NumberOfAsteroidsItCanDetect)

	sampleInput2 := `......#.#.
#..#.#....
..#######.
.#.#.###..
.#..#.....
..#....#.#
#..#....#.
.##.#..###
##...#..#.
.#....####`

	assert.Equal(t, 33, RunProgram(ParseInput(sampleInput2)).NumberOfAsteroidsItCanDetect)

	sampleInput3 := `#.#...#.#.
.###....#.
.#....#...
##.#.#.#.#
....#.#.#.
.##..###.#
..#...##..
..##....##
......#...
.####.###.`

	assert.Equal(t, 35, RunProgram(ParseInput(sampleInput3)).NumberOfAsteroidsItCanDetect)

	sampleInput4 := `.#..#..###
####.###.#
....###.#.
..###.##.#
##.##.#.#.
....###..#
..#.#..#.#
#..#.#.###
.##...##.#
.....#.#..`

	assert.Equal(t, 41, RunProgram(ParseInput(sampleInput4)).NumberOfAsteroidsItCanDetect)

	sampleInput5 := `.#..##.###...#######
##.############..##.
.#.######.########.#
.###.#######.####.#.
#####.##.#.##.###.##
..#####..#.#########
####################
#.####....###.#.#.##
##.#################
#####.##.###..####..
..######..##.#######
####.##.####...##..#
.#####..#.######.###
##...#.##########...
#.##########.#######
.####.#.###.###.#.##
....##.##.###..#####
.#.#.###########.###
#.#.#.#####.####.###
###.##.####.##.#..##`

	assert.Equal(t, 210, RunProgram(ParseInput(sampleInput5)).NumberOfAsteroidsItCanDetect)
}

func TestRunProgram2(t *testing.T) {

	sampleInput6 := `.#....#####...#..
##...##.#####..##
##...#...#.#####.
..#.....X...###..
..#.#.....#....##`

	asteroids6 := ParseInput(sampleInput6)
	bestAsteroid6 := Asteroid{
		X: 8,
		Y: 3,
	}

	assert.Equal(t, (14*100)+3, RunProgram2(bestAsteroid6, asteroids6, 36))

	sampleInput5 := `.#..##.###...#######
##.############..##.
.#.######.########.#
.###.#######.####.#.
#####.##.#.##.###.##
..#####..#.#########
####################
#.####....###.#.#.##
##.#################
#####.##.###..####..
..######..##.#######
####.##.####...##..#
.#####..#.######.###
##...#.##########...
#.##########.#######
.####.#.###.###.#.##
....##.##.###..#####
.#.#.###########.###
#.#.#.#####.####.###
###.##.####.##.#..##`

	asteroids5 := ParseInput(sampleInput5)
	bestAsteroid5 := RunProgram(asteroids5)

	assert.Equal(t, 802, RunProgram2(bestAsteroid5, asteroids5, 200))
}
