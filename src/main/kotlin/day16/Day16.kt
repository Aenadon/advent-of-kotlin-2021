package day16

private const val TYPE_ID_LITERAL = 4

class Day16 {

    private val input = InputReader.readInput(16, test = false)

    fun solve1() {
        val data = input[0].trim().map { "$it".toInt(16).toString(2).padWithZeroes(4) }.joinToString("")
        println(decodePacket(data))
    }

    private fun decodePacket(packetData: String): Result {
        if (packetData.length < 6) {
            return Result(versionId = 0, result = null, readBits = packetData.length)
        }

        var pointer = 0

        val version = packetData.drop(pointer).take(3).toInt(radix = 2)
        pointer += 3
        val packetTypeId = packetData.drop(pointer).take(3).toInt(radix = 2)
        pointer += 3

        if (packetTypeId == TYPE_ID_LITERAL) {
            var foundLastGroupOf5 = false
            val groups = ArrayList<String>()
            while (!foundLastGroupOf5) {
                val group = packetData.drop(pointer).take(5)
                groups.add(group.substring(1))
                if (group.startsWith("0")) {
                    foundLastGroupOf5 = true
                }
                pointer += 5
            }
            val parsedNumber = groups.joinToString("").toLong(radix = 2)
            return Result(versionId = version, result = parsedNumber, readBits = pointer)
        } else {
            val lengthTypeId = packetData.drop(pointer).take(1).toInt(2)
            pointer += 1

            when (lengthTypeId) {
                0 -> {
                    val packetBits = packetData.drop(pointer).take(15).toInt(radix = 2)
                    pointer += 15

                    val subpacketResults = ArrayList<Long>()
                    var readBits = 0
                    var subVersionSum = 0
                    while (readBits < packetBits) {
                        val relevantData = packetData.drop(pointer).take(packetBits - readBits)
                        val subresult = decodePacket(relevantData)
                        subVersionSum += subresult.versionId
                        readBits += subresult.readBits
                        pointer += subresult.readBits
                        if (subresult.result != null) {
                            subpacketResults.add(subresult.result)
                        }
                    }
                    return Result(
                        versionId = version + subVersionSum,
                        result = calculateResult(packetTypeId, subpacketResults),
                        readBits = pointer
                    )
                }
                1 -> {
                    val packetCount = packetData.drop(pointer).take(11).toInt(radix = 2)
                    pointer += 11

                    val subpacketResults = ArrayList<Long>()
                    var readBits = 0
                    var subVersionSum = 0
                    repeat(packetCount) {
                        val subresult = decodePacket(packetData.drop(pointer))
                        subVersionSum += subresult.versionId
                        readBits += subresult.readBits
                        pointer += subresult.readBits
                        if (subresult.result != null) {
                            subpacketResults.add(subresult.result)
                        }
                    }

                    return Result(
                        versionId = version + subVersionSum,
                        result = calculateResult(packetTypeId, subpacketResults),
                        readBits = pointer
                    )
                }
                else -> throw IllegalArgumentException("Illegal length type ID $lengthTypeId")
            }
        }
    }

    private fun calculateResult(packetTypeId: Int, subpacketResults: List<Long>): Long {
        return when (packetTypeId) {
            0 -> subpacketResults.sum()
            1 -> subpacketResults.reduce { a, b -> a * b }
            2 -> subpacketResults.minOrNull()!!
            3 -> subpacketResults.maxOrNull()!!
            5 -> if (subpacketResults[0] > subpacketResults[1]) 1 else 0
            6 -> if (subpacketResults[0] < subpacketResults[1]) 1 else 0
            7 -> if (subpacketResults[0] == subpacketResults[1]) 1 else 0
            else -> throw IllegalArgumentException("Illegal packet type $packetTypeId")
        }
    }

    fun solve2() {
        solve1() // it will print the result for part 2 as well
    }

}

private fun String.padWithZeroes(i: Int): String {
    if (this.length >= i) {
        return this
    }

    return "0".repeat(i - this.length) + this
}
