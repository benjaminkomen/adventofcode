import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day1Test {

  private val testInput = """199
200
208
210
200
207
240
269
260
263"""

  @Test
  fun `should test part1`() {
    val testInput = testInput

    assertEquals(7, Day1.part1(testInput))
  }

  @Test
  fun `should test part2`() {
    val testInput = testInput

    assertEquals(5, Day1.part2(testInput))
  }
}
