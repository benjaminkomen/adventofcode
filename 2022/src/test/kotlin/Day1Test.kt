import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day1Test {

  private val testInput = """1000
    2000
    3000

    4000

    5000
    6000

    7000
    8000
    9000

    10000""".trimIndent()

  @Test
  fun `should test part1`() {
    val testInput = testInput

    assertEquals(24000, Day1.part1(testInput))
  }

  @Test
  fun `should test part2`() {
    val testInput = testInput

    assertEquals(-1, Day1.part2(testInput))
  }
}
