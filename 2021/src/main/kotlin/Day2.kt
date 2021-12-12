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
      .map { mapStringToInstruction(it) }
      .fold(Position(0, 0)) { previousPosition, newInstruction -> previousPosition + newInstruction }
      .let { it.horizontal * it.vertical }
  }

  fun part2(input: String): Int {
    return input
      .split(Regex.fromLiteral("\n"))
      .map { mapStringToInstruction(it) }
      .fold(Position2(0, 0, 0)) { previousPosition, newInstruction -> previousPosition + newInstruction }
      .let { it.horizontal * it.vertical }
  }

  private fun mapStringToInstruction(command: String): Pair<String, Int> {
    val (direction, units) = command.split(" ")
    return Pair(direction, units.toInt())
  }

  data class Position(
    val horizontal: Int,
    val vertical: Int,
  )

  data class Position2(
    val horizontal: Int,
    val vertical: Int,
    val aim: Int,
  )
}

private operator fun Day2.Position.plus(newInstruction: Pair<String, Int>): Day2.Position {
  return when (newInstruction.first) {
    "forward" -> this.copy(horizontal = this.horizontal + newInstruction.second)
    "down"  -> this.copy(vertical = this.vertical + newInstruction.second)
    "up" -> this.copy(vertical = this.vertical - newInstruction.second)
    else -> throw IllegalStateException("Unknown instruction: $newInstruction")
  }
}

private operator fun Day2.Position2.plus(newInstruction: Pair<String, Int>): Day2.Position2 {
  return when (newInstruction.first) {
    "forward" -> {
      this.copy(
        horizontal = this.horizontal + newInstruction.second,
        vertical = this.vertical + (this.aim * newInstruction.second)
      )
    }
    "down" -> this.copy(aim = this.aim + newInstruction.second)
    "up" -> this.copy(aim = this.aim - newInstruction.second)
    else -> throw IllegalStateException("Unknown instruction: $newInstruction")
  }
}
