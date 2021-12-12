import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day2Test {

  private val testInput = """forward 5
down 5
forward 8
up 3
down 8
forward 2"""

  @Test
  fun `should test part1`() {
    val testInput = testInput

    assertEquals(150, Day2.part1(testInput))
  }

  @Test
  fun `should test part2`() {
    val testInput = testInput

    assertEquals(900, Day2.part2(testInput))
  }
}
