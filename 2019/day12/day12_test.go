package main

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestRunProgram(t *testing.T) {

	moons1 := `<x=-1, y=0, z=2>
	<x=2, y=-10, z=-7>
	<x=4, y=-8, z=8>
	<x=3, y=5, z=-1>`

	assert.Equal(t, float64(179), RunProgram(ParseInput(moons1), 10))

	moons2 := `<x=-8, y=-10, z=0>
<x=5, y=5, z=10>
<x=2, y=-7, z=3>
<x=9, y=-8, z=-3>`

	assert.Equal(t, float64(1940), RunProgram(ParseInput(moons2), 100))
}

func TestRunProgram2(t *testing.T) {

	moons1 := `<x=-1, y=0, z=2>
	<x=2, y=-10, z=-7>
	<x=4, y=-8, z=8>
	<x=3, y=5, z=-1>`

	assert.Equal(t, 2772, RunProgram2(ParseInput(moons1)))

	moons2 := `<x=-8, y=-10, z=0>
<x=5, y=5, z=10>
<x=2, y=-7, z=3>
<x=9, y=-8, z=-3>`

	assert.Equal(t, 4686774924, RunProgram2(ParseInput(moons2)))
}
