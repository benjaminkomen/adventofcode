import java.lang.IllegalStateException

object Day4 {

  @JvmStatic
  fun main(args: Array<String>) {
    println("Starting Day 4 - part 1")
    val input = this::class.java.getResource("/input/day4.txt")?.readText() ?: ""
    val result1 = part1(input)
    println("Result for Day4 - part 1: $result1")

    val result2 = part2(input)
    println("Result for Day4 - part 2: $result2")
  }

  fun part1(input: String): Int {
    val data = input.split("\n\n".toRegex())
    val drawNumbers = data.first().split(",").map { it.toInt() }
    val bingoBoards = data.subList(1, data.size).map { it.toBingoBoard() }

    drawNumbers.forEach { number ->
      bingoBoards.drawNumber(number)
      val sumOfAllUnmarkedNumbers = bingoBoards.determineIfWon()

      if (sumOfAllUnmarkedNumbers != null) {
        return sumOfAllUnmarkedNumbers * number
      }
    }

    throw IllegalStateException("Did not find a winning bingo board")
  }

  fun part2(input: String): Int {
    TODO()
  }
}

private fun String.toBingoBoard(): BingoBoard {
  return BingoBoard(
    numbers = split("\n".toRegex())
    .map { row -> row.trim().split("""\s+""".toRegex()) }
    .flatMapIndexed { rowIndex, row ->
      row.mapIndexed { colIndex, num ->
        BingoBoard.Entry(
          value = num.toInt(),
          position = BingoBoard.Entry.Coordinates(
            x = colIndex,
            y = rowIndex
          )
        )
      }
    })
}

private fun List<BingoBoard>.drawNumber(number: Int) {
  this.forEach {
    it.numbers.forEach { num ->
      if (num.value == number) {
        num.drawn = true
      }
    }
  }
}

private fun List<BingoBoard>.determineIfWon(): Int? {
  this.forEach {
    it.won = anyRowComplete(it.numbers) || anyColumnComplete(it.numbers)

    if (it.won) {
      return it.sumOfAllUnmarkedNumbers()
    }
  }
  return null
}

private fun BingoBoard.sumOfAllUnmarkedNumbers(): Int {
  return this.numbers.filter { it.drawn.not() }.sumOf { it.value }
}

fun anyRowComplete(numbers: List<BingoBoard.Entry>): Boolean {
  return (0 until 4).any { rowNum -> numbers.filter { it.position.y == rowNum }.all { it.drawn } }
}

fun anyColumnComplete(numbers: List<BingoBoard.Entry>): Boolean {
  return (0 until 4).any { rowNum -> numbers.filter { it.position.x == rowNum }.all { it.drawn } }
}

data class BingoBoard(
  val numbers: List<Entry>,
  var won: Boolean = false,
) {
  data class Entry(
    val value: Int,
    var drawn: Boolean = false,
    val position: Coordinates,
  ) {
    data class Coordinates(
      val x: Int,
      val y: Int,
    )
  }
}
