package day14

class Day14 {

    private val input = InputReader.readInput(14, split = false, test = false)

    fun solve1() {
        // the inefficient variant. good enough for part 1, but will use the more efficient method for part 2
        val parts = input[0].split("\n\n")
        val instructions = parts[1]
            .split("\n")
            .filter { it.isNotBlank() }
            .map { it.split(" -> ") }
            .associate { it[0] to "${it[0][0]}${it[1]}${it[0][1]}" }

        var template = parts[0]

        repeat(10) {
            template = template.windowed(2, partialWindows = true).joinToString("") {
                if (it.length == 2) instructions[it]!!.substring(0..1) else it
            }
        }
        val elementGroups = template.groupBy { it }
        val maxElementCount = elementGroups.entries.maxByOrNull { it.value.size }!!.value.size
        val minElementCount = elementGroups.entries.minByOrNull { it.value.size }!!.value.size

        println(maxElementCount - minElementCount)
    }

    fun solve2() {
        val parts = input[0].split("\n\n")
        val template = parts[0]
        val instructions = parts[1]
            .split("\n")
            .filter { it.isNotBlank() }
            .map { it.split(" -> ") }
            .associate { it[0] to Pair("${it[0][0]}${it[1]}", "${it[1]}${it[0][1]}") }

        var fragments = template.windowed(2, partialWindows = false)
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }

        var firstFragment = template.substring(0..1)
        repeat(40) {
            fragments = fragments.flatMap {
                val res = instructions[it.key]!!
                listOf(
                    res.first to it.value,
                    res.second to it.value,
                )
            }.groupBy({ it.first }, { it.second })
                .mapValues { it.value.sum() }

            firstFragment = instructions[firstFragment]!!.first
        }

        val singleLetterCounts = fragments.entries.map {
            val splitKey = it.key.split("")
            splitKey[2] to it.value
        }.plus("${firstFragment[0]}" to fragments[firstFragment]!!)
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.sum() }

        val maxElementCount = singleLetterCounts.entries.maxByOrNull { it.value }!!.value
        val minElementCount = singleLetterCounts.entries.minByOrNull { it.value }!!.value

        println(maxElementCount - minElementCount)
    }
}
