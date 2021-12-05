object Day1 {

  @JvmStatic
  fun main(args: Array<String>) {
    println("Starting Day 1 - part 1")
    val input = this::class.java.getResource("/input/day1.txt")?.readText() ?: ""
    val result1 = part1(input)
    println("Result for Day1 - part 1: $result1")
  }

  fun part1(input: String): Int {
    return input
      .split(Regex.fromLiteral("\n"))
      .mapNotNull { it.toIntOrNull() }
      .asSequence()
      .windowed(2) { (left, right) -> if (right > left) 1 else 0 }
      .sumOf { it }
  }
}
