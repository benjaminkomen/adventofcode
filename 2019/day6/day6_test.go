package main

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

var (
	testInput = `COM)B
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

	testInput2 = `COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L
K)YOU
I)SAN`

	sampleInstructions = []Instruction{
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
)

func TestPrepareInput(t *testing.T) {
	assert.Equal(t, sampleInstructions, PrepareInput(testInput, "\n"))

}

func TestRunProgram(t *testing.T) {
	assert.Equal(t, 42, RunProgram(sampleInstructions))
}

func TestRunProgram2(t *testing.T) {
	assert.Equal(t, 4, RunProgram2(PrepareInput(testInput2, "\n")))
}
