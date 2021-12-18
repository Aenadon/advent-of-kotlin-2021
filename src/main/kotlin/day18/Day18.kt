package day18

import InputReader

class Day18 {

    private val input = InputReader.readInput(18, test = false)

    fun solve1() {
        val pairs = input.map {
            readPair(it, 0, 0).second
        }
        val result = pairs.reduce { former, latter ->
            (former + latter).reduceUntilDone()
        }.reduceUntilDone()
        println(result.magnitude())
    }

    private fun readPair(line: String, readPosition: Int, depth: Int): Pair<Int, Element> {
        if (line[readPosition] == '[') {
            val leftPair = readPair(line, readPosition + 1, depth + 1)
            val rightPair = readPair(line, readPosition + leftPair.first, depth + 1)
            val parent = PairElement(leftPair.second, rightPair.second, depth)
            return Pair(leftPair.first + rightPair.first + 1, parent)
        }
        return Pair(
            3,
            NumberElement(line[readPosition].toString().toInt(), depth)
        )
    }

    fun solve2() {
        val pairs = input.map {
            readPair(it, 0, 0).second
        }

        val allPossibilities = pairs.flatMap { x -> pairs.map { y -> Pair(x, y) } }.flatMap { pair -> listOf(pair, Pair(pair.second, pair.first)) }
        println(allPossibilities.map {
            val result = (it.first.copy() + it.second.copy())
            result.reduceUntilDone()
            result.magnitude()
        }.maxOrNull())
    }

}
