package common

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestPermutations(t *testing.T) {
	assert.Equal(t, 1, len(Permutations([]int{0})))
	assert.Equal(t, 2, len(Permutations([]int{0, 1})))
	assert.Equal(t, 6, len(Permutations([]int{0, 1, 2})))
	assert.Equal(t, 24, len(Permutations([]int{0, 1, 2, 3})))
	assert.Equal(t, 120, len(Permutations([]int{0, 1, 2, 3, 4})))
}
