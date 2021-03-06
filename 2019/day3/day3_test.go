package main

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestRunProgram(t *testing.T) {
	assert.Equal(t, 6, RunProgram(PrepareInput("R8,U5,L5,D3\nU7,R6,D4,L4")))
	assert.Equal(t, 159, RunProgram(PrepareInput("R75,D30,R83,U83,L12,D49,R71,U7,L72\nU62,R66,U55,R34,D71,R55,D58,R83")))
	assert.Equal(t, 135, RunProgram(PrepareInput("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\nU98,R91,D20,R16,D67,R40,U7,R15,U6,R7")))
}

func TestRunProgram2(t *testing.T) {
	assert.Equal(t, 30, RunProgram2(PrepareInput("R8,U5,L5,D3\nU7,R6,D4,L4")))
	assert.Equal(t, 610, RunProgram2(PrepareInput("R75,D30,R83,U83,L12,D49,R71,U7,L72\nU62,R66,U55,R34,D71,R55,D58,R83")))
	assert.Equal(t, 410, RunProgram2(PrepareInput("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\nU98,R91,D20,R16,D67,R40,U7,R15,U6,R7")))
}
