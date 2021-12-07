package day7

import InputReader
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.round

class Day7 {

    private val input = InputReader.readInput(7, test = false)[0].split(",").map { it.toInt() }

    fun solve1() {
        val sorted = input.sortedBy { it }
        val half = floor(sorted.size / 2.0).toInt()
        val median = if (sorted.size % 2 == 0) {
            floor((sorted[half - 1] + sorted[half - 1]) / 2.0).toInt()
        } else {
            sorted[half]
        }

        println(sorted.sumOf { abs(median - it) })
    }

    fun solve2() {
        // no idea and no time to do it with "fancy" methods like maths or something, so here's a bruteforce
        // (it is nonetheless super fast, I didn't expect that :O)

        val max = input.maxOf { it }

        val result = (0..max).fold(Pair(-1, Long.MAX_VALUE)) { acc, v ->
            val sum = input.sumByDouble {
                val distance = abs(v - it)
                distance * (distance + 1) / 2.0
            }

            if (sum < acc.second) {
                Pair(v, sum.toLong())
            } else {
                acc
            }
        }

        println(result)
    }
}