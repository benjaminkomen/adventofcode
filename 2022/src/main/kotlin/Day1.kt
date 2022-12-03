object Day1 {

  @JvmStatic
  fun main(args: Array<String>) {
    println("Starting Day 1 - part 1")
    val input = this::class.java.getResource("/input/day1.txt")?.readText() ?: ""
    val result1 = part1(input)
    println("Result for Day1 - part 1: $result1")

    val result2 = part2(input)
    println("Result for Day1 - part 2: $result2")
  }

  fun part1(input: String): Int {
    return input.split(Regex.fromLiteral("\n\n"))
      .map { singleElfInput -> singleElfInput
        .split(Regex.fromLiteral("\n"))
        .sumOf { it.trim().toInt() }
      }
      .max()
  }

  fun part2(input: String): Int {
    return input.split(Regex.fromLiteral("\n\n"))
      .map { singleElfInput -> singleElfInput
        .split(Regex.fromLiteral("\n"))
        .sumOf { it.trim().toInt() }
      }
      .sortedDescending()
      .subList(0, 3)
      .sum()
  }
}
