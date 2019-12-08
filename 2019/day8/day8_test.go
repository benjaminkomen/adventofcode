package main

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestRunProgram(t *testing.T) {
	assert.Equal(t, 1, RunProgram(MakeLayeredImage("123456789012", 3, 2)))
}

func TestRunProgram2(t *testing.T) {
	assert.Equal(t, []int{0, 1, 1, 0}, RunProgram2(MakeLayeredImage("0222112222120000", 2, 2)))
}
