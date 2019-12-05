package main

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestRunProgram(t *testing.T) {
	assert.Equal(t, 1, RunProgram([]int{3, 0, 4, 0, 99}, 1))
	assert.Equal(t, 1002, RunProgram([]int{1002, 4, 3, 4, 33}, 1))
	assert.Equal(t, 1101, RunProgram([]int{1101, 100, -1, 4, 0}, 1))
}
