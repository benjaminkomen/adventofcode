object Day5 {

  @JvmStatic
  fun main(args: Array<String>) {
    println("Starting Day 5 - part 1")
    val input = this::class.java.getResource("/input/day5.txt")?.readText() ?: ""
    val result1 = part1(input)
    println("Result for Day5 - part 1: $result1")

    val result2 = part2(input)
    println("Result for Day5 - part 2: $result2")
  }

  fun part1(input: String): Int {
    return input
      .split("\n".toRegex())
      .map { it.toLine() }
      .filter { it.isHorizontal() || it.isVertical() }
      .flatMap { it.toPoints() }
      .fold(Diagram()) { previousDiagram, newPoint -> previousDiagram + newPoint }
      .positions.filter { it.coverage >= 2 }.size
  }

  fun part2(input: String): Int {
    TODO()
  }

  data class Line(
    val from: Point,
    val to: Point,
  ) {
    data class Point(
      val x: Int,
      val y: Int,
    ) {
      override fun toString(): String = "$x,$y"
    }

    override fun toString(): String = "$from -> $to"
  }

  data class Diagram(
    val positions: List<Position> = emptyList(),
  ) {
    data class Position(
      val point: Line.Point,
      var coverage: Int,
    )
  }

  private fun Line.isHorizontal(): Boolean = this.from.x == this.to.x
  private fun Line.isVertical(): Boolean = this.from.y == this.to.y

  private fun Line.toPoints(): List<Line.Point> {
    return if (this.isHorizontal()) {
      val range = if (from.y < to.y) {
        from.y..to.y
      } else {
        from.y downTo to.y
      }
      range.map { yPoint -> Line.Point(x = this.from.x, y = yPoint) }
    } else if (this.isVertical()) {
      val range = if (this.from.x < this.to.x) {
        this.from.x..this.to.x
      } else {
        this.from.x downTo this.to.x
      }
      range.map { xPoint -> Line.Point(x = xPoint, y = this.from.y) }
    } else {
      throw IllegalStateException("Line should be either horizontal or vertical")
    }
  }

  private fun String.toLine(): Line {
    val (from, to) = this.split(" -> ")
    return Line(from = from.toPoint(), to = to.toPoint())
  }

  private fun String.toPoint(): Line.Point {
    val (x, y) = this.split(",")
    return Line.Point(x = x.toInt(), y = y.toInt())
  }

  private operator fun Diagram.plus(point: Line.Point): Diagram {
    val increaseCoverageOfExistingPosition = positions.firstOrNull { it.point == point }?.let { it.coverage++ }?.let { positions }
    val addNewPosition = positions + listOf(Diagram.Position(point = point, coverage = 1))
    return this.copy(positions = increaseCoverageOfExistingPosition ?: addNewPosition)
  }
}
