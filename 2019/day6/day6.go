package main

import (
	"fmt"
	"github.com/adventofcode/2019/common"
	"os"
	"strings"
)

func main() {
	inputAsStr := string(common.ReadBytes("./day6/input.txt"))
	inputInstructions := PrepareInput(inputAsStr, common.LineBreak)

	result1 := RunProgram(inputInstructions)
	result2 := RunProgram2(inputInstructions)

	fmt.Printf("Part 1 is: %d \n", result1)
	fmt.Printf("Part 2 is: %d \n", result2)

}

type Node struct {
	uid          string
	parentUid    string
	childrenUids []string
	orbits       int
}

type Instruction struct {
	subject string
	orbiter string
}

func RunProgram(instructions []Instruction) int {
	graph := buildGraph(instructions)
	return countOrbits(graph)
}

func RunProgram2(instructions []Instruction) int {
	graph := buildGraph(instructions)
	return countOrbitalTransfersToSanta(graph)
}

func countOrbits(graph []Node) int {
	var totalOrbits int

	for _, graphNode := range graph {
		graphNode.orbits = countParents(graphNode.uid, graph)
		totalOrbits = totalOrbits + graphNode.orbits
	}

	return totalOrbits
}

func countOrbitalTransfersToSanta(graph []Node) int {
	youNode, _, found := findNodeInGraph("YOU", graph)
	if !found {
		fmt.Printf("could not find YOU node in graph, exiting..")
		os.Exit(-1)
	}

	sanNode, _, found := findNodeInGraph("SAN", graph)
	if !found {
		fmt.Printf("could not find SAN node in graph, exiting..")
		os.Exit(-1)
	}

	parentsOfYouNode := determineParentNodes(youNode, graph)
	parentsOfSanNode := determineParentNodes(sanNode, graph)

	// find intersection
	for youPos, currentNode := range parentsOfYouNode {
		foundNode, sanPos, found := findNodeInGraph(currentNode.uid, parentsOfSanNode)
		if found {
			fmt.Printf("YOU can reach SAN via node with uid %q\n", foundNode.uid)
			return youPos + sanPos
		}
	}

	return 0
}

func determineParentNodes(startNode Node, graph []Node) []Node {
	var parentNodes []Node
	nodeUidToLookFor := startNode.parentUid

	for {
		foundNode, _, found := findNodeInGraph(nodeUidToLookFor, graph)
		if !found {
			break
		}
		nodeUidToLookFor = foundNode.parentUid
		parentNodes = append(parentNodes, foundNode)
	}

	return parentNodes
}

func tryToReachSantaFromNode(node Node, graph []Node) (int, bool) {
	return 0, false
}

func buildGraph(instructions []Instruction) []Node {
	var graph []Node

	for _, instruction := range instructions {
		node := Node{
			uid:          instruction.orbiter,
			parentUid:    instruction.subject,
			childrenUids: []string{},
			orbits:       0,
		}

		// add childrenUids to graph
		{
			foundNode, pos, found := findNodeInGraph(node.parentUid, graph)
			if found {
				graph[pos].childrenUids = append(foundNode.childrenUids, node.uid)
			}
		}

		graph = append(graph, node)
	}
	return graph
}

func countParents(nodeUid string, graph []Node) int {
	var parents int
	nodeUitToLookFor := nodeUid

	for {
		node, _, found := findNodeInGraph(nodeUitToLookFor, graph)
		if !found {
			break
		}
		parents++
		nodeUitToLookFor = node.parentUid
	}

	return parents
}

func findNodeInGraph(nodeUid string, graph []Node) (Node, int, bool) {
	for pos, node := range graph {
		if node.uid == nodeUid {
			return node, pos, true
		}
	}
	return Node{}, 0, false
}

func PrepareInput(inputAsStr string, lineBreak string) []Instruction {
	var instructions []Instruction

	lines := strings.Split(inputAsStr, lineBreak)

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
