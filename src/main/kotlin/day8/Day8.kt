package day8

import InputReader

class Day8 {

    private val input = InputReader.readInput(8, test = false)

    fun solve1() {
        println(input.map { it.split(" | ")[1] }
            .flatMap { it.split(" ").filter { listOf(2, 3, 4, 7).contains(it.length) } }
            .count())
    }

    fun solve2() {
        println(input.sumOf { input ->
            val splitInput = input.split(" | ")
            val displays = splitInput[0].split(" ").map { it.toSet() }
            val query = splitInput[1].split(" ").map { it.toSet() }

            val lengthMap = displays.groupBy { it.size }
            val seven = lengthMap[3]!!.first()
            val one = lengthMap[2]!!.first()

            val top = (seven - one).first()

            val six = lengthMap[6]!!.first { !it.containsAll(one) }

            val bottomRight = one.intersect(six).first()
            val topRight = (one - bottomRight).first()

            val four = lengthMap[4]!!.first()
            val two = lengthMap[5]!!.first { (it - four).size == 3 }
            val fiveSegmentsNotTwo = lengthMap[5]!!.filter { (it - four).size != 3 }

            val bottomLeft = (two - fiveSegmentsNotTwo[0] - fiveSegmentsNotTwo[1]).first()
            val bottom = (two - four - top - bottomLeft).first()

            val three = lengthMap[5]!!.first { (it - top - topRight - bottomRight - bottom).size == 1 }
            val center = (three - top - topRight - bottomRight - bottom).first()

            val topLeft = (lengthMap[7]!!.first() - one - two).first()

            val digits = mapOf(
                setOf(top, topLeft, topRight, bottomLeft, bottomRight, bottom) to 0,
                setOf(topRight, bottomRight) to 1,
                setOf(top, topRight, center, bottomLeft, bottom) to 2,
                setOf(top, topRight, center, bottomRight, bottom) to 3,
                setOf(topLeft, topRight, center, bottomRight) to 4,
                setOf(top, topLeft, center, bottomRight, bottom) to 5,
                setOf(top, topLeft, center, bottomLeft, bottomRight, bottom) to 6,
                setOf(top, topRight, bottomRight) to 7,
                setOf(top, topLeft, topRight, center, bottomLeft, bottomRight, bottom) to 8,
                setOf(top, topLeft, topRight, center, bottomRight, bottom) to 9,
            )

            query.joinToString("") { digits[it]!!.toString() }.toInt()
        })
    }
}