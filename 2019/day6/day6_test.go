package main

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

var testInput = `COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L`

func TestPrepareInput(t *testing.T) {

	expectedInstructions := []Instruction{
		{
			subject: "COM",
			orbiter: "B",
		},
		{
			subject: "B",
			orbiter: "C",
		},
		{
			subject: "C",
			orbiter: "D",
		},
		{
			subject: "D",
			orbiter: "E",
		},
		{
			subject: "E",
			orbiter: "F",
		},
		{
			subject: "B",
			orbiter: "G",
		},
		{
			subject: "G",
			orbiter: "H",
		},
		{
			subject: "D",
			orbiter: "I",
		},
		{
			subject: "E",
			orbiter: "J",
		},
		{
			subject: "J",
			orbiter: "K",
		},
		{
			subject: "K",
			orbiter: "L",
		},
	}

	assert.Equal(t, expectedInstructions, PrepareInput(testInput))

}

func TestRunProgram(t *testing.T) {

}
