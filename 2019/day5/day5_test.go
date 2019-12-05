package main

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestRunProgram(t *testing.T) {

	// part 1
	{
		assert.Equal(t, 1, RunProgram([]int{3, 0, 4, 0, 99}, 1))
		assert.Equal(t, 1002, RunProgram([]int{1002, 4, 3, 4, 33}, 1))
		assert.Equal(t, 1101, RunProgram([]int{1101, 100, -1, 4, 0}, 1))
	}

	// part 2
	{
		assert.Equal(t, 1, RunProgram([]int{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8}, 8)) // input == 8, so output == 1
		assert.Equal(t, 0, RunProgram([]int{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8}, 1)) // input != 8, so output == 0

		assert.Equal(t, 1, RunProgram([]int{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8}, 7)) // input < 8, so output == 1
		assert.Equal(t, 0, RunProgram([]int{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8}, 8)) // input !< 8, so output == 0

		assert.Equal(t, 1, RunProgram([]int{3, 3, 1108, -1, 8, 3, 4, 3, 99}, 8)) // input == 8, so output == 1
		assert.Equal(t, 0, RunProgram([]int{3, 3, 1108, -1, 8, 3, 4, 3, 99}, 9)) // input != 8, so output == 0

		assert.Equal(t, 1, RunProgram([]int{3, 3, 1107, -1, 8, 3, 4, 3, 99}, 7)) // input < 8, so output == 1
		assert.Equal(t, 0, RunProgram([]int{3, 3, 1107, -1, 8, 3, 4, 3, 99}, 9)) // input !< 8, so output == 0

		assert.Equal(t, 0, RunProgram([]int{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9}, 0)) // input == 0, so output == 0
		assert.Equal(t, 1, RunProgram([]int{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9}, 1)) // input != 0, so output == 1

		assert.Equal(t, 0, RunProgram([]int{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1}, 0)) // input == 0, so output == 0
		assert.Equal(t, 1, RunProgram([]int{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1}, 1)) // input != 0, so output == 1

		assert.Equal(t, 999, RunProgram([]int{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
			1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
			999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99}, 7)) // input < 8, so output == 999

		assert.Equal(t, 1000, RunProgram([]int{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
			1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
			999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99}, 8)) // input == 8, so output == 1000

		assert.Equal(t, 1001, RunProgram([]int{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
			1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
			999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99}, 9)) // input > 8, so output == 1001
	}
}
