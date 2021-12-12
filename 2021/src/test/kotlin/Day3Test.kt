import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day3Test {

  private val testInput = """00100
11110
10110
10111
10101
01111
00111
11100
10000
11001
00010
01010"""

  @Test
  fun `should test part1`() {
    val testInput = testInput

    assertEquals(198, Day3.part1(testInput))
  }

  @Test
  fun `should test part2`() {
    val testInput = testInput

    assertEquals(230, Day3.part2(testInput))
  }
}
