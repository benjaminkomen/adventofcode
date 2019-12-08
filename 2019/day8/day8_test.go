package main

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestRunProgram(t *testing.T) {
	assert.Equal(t, 1, RunProgram("123456789012", 3, 2))
}
