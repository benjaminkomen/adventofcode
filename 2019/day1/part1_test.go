package main

import "testing"
import "github.com/stretchr/testify/assert"

func TestCalculateFuelRequired(t *testing.T) {
	assert.Equal(t, 2, CalculateFuelRequired(12))
	assert.Equal(t, 2, CalculateFuelRequired(14))
	assert.Equal(t, 654, CalculateFuelRequired(1969))
	assert.Equal(t, 33583, CalculateFuelRequired(100756))
}

func TestCalculateRecursiveFuelRequired(t *testing.T) {
	assert.Equal(t, 2, CalculateRecursiveFuelRequired(14))
	assert.Equal(t, 966, CalculateRecursiveFuelRequired(1969))
	assert.Equal(t, 50346, CalculateRecursiveFuelRequired(100756))
}
