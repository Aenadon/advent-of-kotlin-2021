package day10

import InputReader
import java.util.*

class Day10 {

    private val input = InputReader.readInput(10, split = true, test = false)

    private val closingMap = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>',
    )
    private val errorScoreMap = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137,
    )
    private val autocompleteScoreMap = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4,
    )

    fun solve1() {
        println(input.sumOf { calculateScore(it).second })
    }

    private fun calculateScore(line: String): Pair<Stack<Char>, Int> {
        val bracketStack = Stack<Char>()
        line.forEach {
            if (closingMap.containsKey(it)) {
                bracketStack.push(it)
            } else if (closingMap.containsValue(it)) {
                val bracket = bracketStack.pop()
                if (it != closingMap[bracket]) {
                    val score = errorScoreMap[it] ?: throw IllegalArgumentException("$it")
                    return Pair(bracketStack, score)
                }
            } else {
                throw IllegalArgumentException("$it")
            }
        }

        return Pair(bracketStack, 0)
    }

    fun solve2() {
        val resultList = input.map {
            val result = calculateScore(it)
            if (result.second != 0) {
                return@map 0
            }

            val stack = result.first
            stack.reversed().map {
                autocompleteScoreMap[closingMap[it]!!]!!.toLong()
            }.reduce { a, b -> a * 5 + b }
        }.filter { it != 0L }.sorted()
        println(resultList[(resultList.size / 2.0).toInt()])
    }
}