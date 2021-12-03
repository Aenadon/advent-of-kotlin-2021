package day3

import InputReader
import java.util.*

class Day3 {

    private val input = InputReader.readInput(3, test = false)
    private val nLen = input[0].length

    fun solve1() {
        val result1 = solveUsingMethod1()
        val result2 = solveUsingMethod2()
        if (result1 != result2) {
            throw IllegalStateException("The 2 results differ: $result1 and $result2")
        }
        println(result1)
    }

    private fun solveUsingMethod1(): Int {
        (0 until nLen).fold("") { acc, i ->
            acc + if (input.count { it[i] == '1' } >= input.size / 2.0) '1' else '0'
        }.apply {
            return this.toInt(2) * this.map { if (it == '1') '0' else '1' }.joinToString("").toInt(2)
        }
    }

    private fun solveUsingMethod2(): Int {
        (1..nLen).fold(BitSet(nLen)) { acc, i ->
            acc[nLen - i] = input.count { it[i - 1] == '1' } >= input.size / 2.0
            acc
        }.apply {
            return (this.toLongArray()[0] * this.apply { this.flip(0, nLen) }.toLongArray()[0]).toInt()
        }
    }

    fun solve2() {
        val o2 = (0 until nLen).fold(input) { acc, i ->
            if (acc.size > 1) acc.filter { (it[i] == '1') == acc.count { it[i] == '1' } >= acc.size / 2.0 } else acc
        }[0].toInt(2)

        val co2 = (0 until nLen).fold(input) { acc, i ->
            if (acc.size > 1) acc.filter { (it[i] == '1') == acc.count { it[i] == '1' } < acc.size / 2.0 } else acc
        }[0].toInt(2)

        println(o2 * co2)
    }

}