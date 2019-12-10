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

	assert.Equal(t, 8, RunProgram(ParseInput(sampleInput1)))
}
