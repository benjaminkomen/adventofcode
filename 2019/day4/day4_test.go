package main

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestRunProgram(t *testing.T) {
	assert.Equal(t, true, TwoSameAdjacentDigits([]int{1, 2, 3, 4, 5, 5}))
	assert.Equal(t, false, TwoSameAdjacentDigits([]int{1, 2, 3, 4, 5, 6}))

	assert.Equal(t, true, DigitsNeverDecrease([]int{1, 2, 3, 4, 5, 5}))
	assert.Equal(t, false, DigitsNeverDecrease([]int{1, 2, 3, 4, 5, 0}))

	assert.Equal(t, true, MeetsConditions(111111))
	assert.Equal(t, false, MeetsConditions(223450))
	assert.Equal(t, false, MeetsConditions(123789))

	assert.Equal(t, 3, RunProgram(123456, 123488)) // 123466, 123477, 123488
}
