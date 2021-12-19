import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day5Test {

  private val testInput = """0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2"""

  @Test
  fun `should test part1`() {
    val testInput = testInput

    assertEquals(5, Day5.part1(testInput))
  }

  @Test
  fun `should test part2`() {
    val testInput = testInput

    assertEquals(12, Day5.part2(testInput))
  }
}
