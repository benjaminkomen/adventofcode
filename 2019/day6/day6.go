package main

import (
	"fmt"
	"github.com/adventofcode/2019/common"
	"strings"
)

func main() {
	inputAsStr := string(common.ReadBytes("./day6/input.txt"))
	inputInstructions := PrepareInput(inputAsStr)

	result1 := RunProgram(inputInstructions)

	fmt.Printf("Part 1 is: %d \n", result1)
	//fmt.Printf("Part 2 is: %d \n", result2)

}

type Node struct {
	uid       string
	parentUid string
	orbits    int
}

type Instruction struct {
	subject string
	orbiter string
}

func RunProgram(instructions []Instruction) int {
	graph := buildGraph(instructions)
	return countOrbits(graph)
}

func countOrbits(graph []Node) int {
	var totalOrbits int

	for _, graphNode := range graph {
		graphNode.orbits = countParents(graphNode.uid, graph)
		totalOrbits = totalOrbits + graphNode.orbits
	}

	return totalOrbits
}

func buildGraph(instructions []Instruction) []Node {
	var graph []Node

	for _, instruction := range instructions {
		node := Node{
			uid:       instruction.orbiter,
			parentUid: instruction.subject,
			orbits:    0,
		}

		graph = append(graph, node)
	}
	return graph
}

func countParents(nodeUid string, graph []Node) int {
	var parents int
	nodeUitToLookFor := nodeUid

	for {
		node, found := findNodeInGraph(nodeUitToLookFor, graph)
		if !found {
			break
		}
		parents++
		nodeUitToLookFor = node.parentUid
	}

	return parents
}

func findNodeInGraph(nodeUid string, graph []Node) (Node, bool) {
	for _, node := range graph {
		if node.uid == nodeUid {
			return node, true
		}
	}
	return Node{}, false
}

func PrepareInput(inputAsStr string) []Instruction {
	var instructions []Instruction

	lines := strings.Split(inputAsStr, common.LineBreak)

	for _, line := range lines {
		instructionsOnLine := strings.Split(line, ")")

		instruction := Instruction{
			subject: instructionsOnLine[0],
			orbiter: instructionsOnLine[1], // orbiter is in orbit around subject
		}

		instructions = append(instructions, instruction)

	}
	return instructions
}
