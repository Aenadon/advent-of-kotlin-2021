package day17

import InputReader
import kotlin.math.abs

class Day17 {

    private val input = InputReader.readInput(17, test = false)

    fun solve1() {
        val (_, _, y1, _) = readValues()
        val yLeap = abs(y1) - 1
        println((yLeap * (yLeap + 1)) / 2)
        // Explanation: No matter how high you throw it, it will arrive at exactly 0 again when falling
        // so the largest leap we can take from there is abs(min(y)), therefore our max height is abs(min(y)) - 1
        // and the last step taken after hitting 0 will be abs(min(y)), arriving perfectly at the edge of the area.
        // The highest y position reached is the sum of 1 to abs(min(y)), represented by (n * (n + 1)) / 2
    }

    private fun readValues(): List<Int> {
        val matches = "target area: x=(\\d+)..(\\d+), y=(-\\d+)..(-\\d+)".toRegex().matchEntire(input[0])!!
        val x1 = matches.groupValues[1].toInt()
        val x2 = matches.groupValues[2].toInt()
        val y1 = matches.groupValues[3].toInt()
        val y2 = matches.groupValues[4].toInt()
        return listOf(x1, x2, y1, y2)
    }

    fun solve2() {
        // Part 1 was solved using beauty and elegance.
        // Part 2 was solved using raw and brute force.
        val (x1, x2, y1, y2) = readValues()

        val minPossibleX = (0..x1).find { (it * (it + 1)) / 2 >= x1 }!!
        val potentialXValues = (minPossibleX..x2)

        val maxPossibleY = abs(y1) - 1
        val potentialYValues = (y1..maxPossibleY)

        val cartesianProduct = potentialXValues.flatMap { x -> potentialYValues.map { y -> Pair(x, y) } }
        println(cartesianProduct.count { hitsTarget(it, x1, x2, y1, y2) })
    }

    private fun hitsTarget(trajectory: Pair<Int, Int>, x1: Int, x2: Int, y1: Int, y2: Int): Boolean {
        var x = 0
        var y = 0

        var xDelta = trajectory.first
        var yDelta = trajectory.second

        while (x <= x2 && y >= y1) {
            x += intSign(xDelta) * xDelta
            y += yDelta

            xDelta -= intSign(xDelta)
            yDelta -= 1

            if (x in x1..x2 && y in y1..y2) {
                return true
            }
        }
        return false
    }

    private fun intSign(x: Int): Int {
        return if (x < 0) -1 else if (x == 0) 0 else 1
    }

}
