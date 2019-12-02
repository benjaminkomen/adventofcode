package main

import "testing"
import "github.com/stretchr/testify/assert"

func TestRunProgram(t *testing.T) {
	assert.Equal(t, 3500, RunProgram([]int{1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50}))
	assert.Equal(t, 2, RunProgram([]int{1, 0, 0, 0, 99}))
	assert.Equal(t, 2, RunProgram([]int{2, 3, 0, 3, 99}))
	assert.Equal(t, 2, RunProgram([]int{2, 4, 4, 5, 99, 0}))
	assert.Equal(t, 30, RunProgram([]int{1, 1, 1, 4, 99, 5, 6, 0, 99}))
}

func TestFindNounAndVerb(t *testing.T) {
	assert.Equal(t, 1202, FindNounAndVerb(3765464))
}
