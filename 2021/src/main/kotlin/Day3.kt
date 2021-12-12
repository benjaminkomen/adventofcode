import java.lang.IllegalStateException

object Day3 {

  @JvmStatic
  fun main(args: Array<String>) {
    println("Starting Day 3 - part 1")
    val input = this::class.java.getResource("/input/day3.txt")?.readText() ?: ""
    val result1 = part1(input)
    println("Result for Day3 - part 1: $result1")

    val result2 = part2(input)
    println("Result for Day3 - part 2: $result2")
  }

  fun part1(input: String): Int {
    return input
      .split(Regex.fromLiteral("\n"))
      .map { it.mapToState() }
      .reduce { previousState, newLine -> previousState + newLine }
      .mapToPowerConsumption()
  }

  fun part2(input: String): Int {
    val oxygenGeneratorRating = computeOxygenGeneratorRating(input)
    val co2GeneratorRating = computeCo2GeneratorRating(input)

    return oxygenGeneratorRating * co2GeneratorRating
  }

  private fun computeOxygenGeneratorRating(input: String): Int {
    var states = input
      .split(Regex.fromLiteral("\n"))
      .map { it.mapToState() }

    (0 until states[0].bits.size).forEach { index ->
      val reducedState = states.reduce { previousState, newLine -> previousState + newLine }
      val oneOrZero = reducedState.bits[index].highestCount()

      states = states.filter { it.bits[index].toInt() == oneOrZero }

      if (states.size == 1) {
        return states[0].toInt()
      }
    }
    throw IllegalStateException("Could not compute the oxygen generator rating")
  }

  private fun computeCo2GeneratorRating(input: String): Int {
    var states = input
      .split(Regex.fromLiteral("\n"))
      .map { it.mapToState() }

    (0 until states[0].bits.size).forEach { index ->
      val reducedState = states.reduce { previousState, newLine -> previousState + newLine }
      val oneOrZero = reducedState.bits[index].lowestCount()

      states = states.filter { it.bits[index].toInt() == oneOrZero }

      if (states.size == 1) {
        return states[0].toInt()
      }
    }
    throw IllegalStateException("Could not compute the co2 generator rating")
  }
}

private fun State.Bit.highestCount(): Int {
   return if (this.oneCount < this.zeroCount) 0 else 1
}

private fun State.Bit.lowestCount(): Int {
  return if (this.oneCount < this.zeroCount) 1 else 0
}

private fun State.mapToPowerConsumption(): Int {
  val gammaRateAsBinary = this.bits.map { if (it.zeroCount > it.oneCount) 0 else 1 }.joinToString(separator = "")
  val gammaRateAsInt = gammaRateAsBinary.toInt(2)

  // actually this is the inverse of the gamma rate, could use binary unary operation
  val epsilonRate = this.bits.map {
    if (it.zeroCount < it.oneCount) 0 else 1
  }.joinToString("").toInt(2)

  return gammaRateAsInt * epsilonRate
}

private operator fun State.plus(newState: State): State {
  return State(bits = this.bits.zip(newState.bits).map { (oldBit, newBit) -> oldBit + newBit })
}

private operator fun State.Bit.plus(newBit: State.Bit): State.Bit {
  return State.Bit(
    zeroCount = this.zeroCount + newBit.zeroCount,
    oneCount = this.oneCount + newBit.oneCount,
  )
}

private fun String.mapToState(): State {
  return State(
    bits = this.split("")
      .filterNot { it.isBlank() }
      .map {
      State.Bit(
        zeroCount = if (it == "0") 1 else 0,
        oneCount = if (it == "1") 1 else 0
      )
    }
  )
}

data class State(
  val bits: List<Bit> = emptyList()
) {
  data class Bit(
    val zeroCount: Int,
    val oneCount: Int,
  ) {
    fun toInt() = if (oneCount == 1 ) 1 else 0
    override fun toString() = toInt().toString()
  }

  override fun toString() = bits.joinToString("") { it.toString() }

  fun toInt() = this.toString().toInt(2)
}
