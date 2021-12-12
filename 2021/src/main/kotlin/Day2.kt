object Day2 {

  @JvmStatic
  fun main(args: Array<String>) {
    println("Starting Day 2 - part 1")
    val input = this::class.java.getResource("/input/day2.txt")?.readText() ?: ""
    val result1 = part1(input)
    println("Result for Day2 - part 1: $result1")

    val result2 = part2(input)
    println("Result for Day2 - part 2: $result2")
  }

  fun part1(input: String): Int {
    return input
      .split(Regex.fromLiteral("\n"))
      .map { it.mapToInstruction() }
      .fold(Position(0, 0)) { previousPosition, newInstruction -> previousPosition + newInstruction }
      .let { it.horizontal * it.vertical }
  }

  fun part2(input: String): Int {
    return input
      .split(Regex.fromLiteral("\n"))
      .map { it.mapToInstruction() }
      .fold(PositionWithAim(0, 0, 0)) { previousPosition, newInstruction -> previousPosition + newInstruction }
      .let { it.horizontal * it.vertical }
  }
}

data class Instruction(
  val direction: String,
  val units: Int,
)

data class Position(
  val horizontal: Int,
  val vertical: Int,
)

data class PositionWithAim(
  val horizontal: Int,
  val vertical: Int,
  val aim: Int,
)

private fun String.mapToInstruction(): Instruction {
  val (direction, units) = this.split(" ")
  return Instruction(direction = direction, units = units.toInt())
}

private operator fun Position.plus(instruction: Instruction): Position {
  return when (instruction.direction) {
    "forward" -> this.copy(horizontal = this.horizontal + instruction.units)
    "down"  -> this.copy(vertical = this.vertical + instruction.units)
    "up" -> this.copy(vertical = this.vertical - instruction.units)
    else -> throw IllegalStateException("Unknown instruction: $instruction")
  }
}

private operator fun PositionWithAim.plus(instruction: Instruction): PositionWithAim {
  return when (instruction.direction) {
    "forward" -> {
      this.copy(
        horizontal = this.horizontal + instruction.units,
        vertical = this.vertical + (this.aim * instruction.units)
      )
    }
    "down" -> this.copy(aim = this.aim + instruction.units)
    "up" -> this.copy(aim = this.aim - instruction.units)
    else -> throw IllegalStateException("Unknown instruction: $instruction")
  }
}
