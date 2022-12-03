object DayX {

  @JvmStatic
  fun main(args: Array<String>) {
    println("Starting Day X - part 1")
    val input = this::class.java.getResource("/input/dayX.txt")?.readText() ?: ""
    val result1 = part1(input)
    println("Result for DayX - part 1: $result1")

    val result2 = part2(input)
    println("Result for DayX - part 2: $result2")
  }

  fun part1(input: String): Int {
    TODO()
  }

  fun part2(input: String): Int {
    TODO()
  }
}
