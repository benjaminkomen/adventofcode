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
    return -1
  }

  fun part2(input: String): Int {
    return -1
  }
}
