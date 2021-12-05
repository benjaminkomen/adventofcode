import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day1Test {

    @Test
    fun `should test part1`() {
      val testInput = """199
200
208
210
200
207
240
269
260
263"""

      assertEquals(7, Day1.part1(testInput))
    }
}
