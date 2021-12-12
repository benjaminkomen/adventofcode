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
    TODO()
  }
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
  )
}
