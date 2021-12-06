package day6

import InputReader

class Day6 {

    private val input = InputReader.readInput(6, test = false)[0].split(",").map { it.trim().toInt() }

    fun solve1() {
        println(solve(rounds = 80))
    }

    fun solve2() {
        println(solve(rounds = 256))
    }

    private fun solve(rounds: Int): Long {
        val ocean = input.groupingBy { it }
            .eachCount()
            .plus(0 to 0)
            .toSortedMap()
            .toList()
            .map { it.second.toLong() }
            .toMutableList()
        while (ocean.size < 9) {
            ocean.add(0)
        }

        repeat(rounds) {
            val replicators = ocean[0]
            ocean[0] = 0
            (1 until ocean.size).forEach { fish ->
                ocean[fish - 1] = ocean[fish]
            }
            ocean[6] = replicators + ocean[6]
            ocean[8] = replicators
        }

        return ocean.sum()
    }

    private fun solveInefficiently(rounds: Int): Long {
        val fishList = input.toMutableList()
        repeat(rounds) {
            (0 until fishList.size).forEach { index ->
                val fish = fishList[index]
                if (fish == 0) {
                    fishList[index] = 6
                    fishList.add(8)
                } else {
                    fishList[index] = fish - 1
                }
            }
        }
        // well this wouldn't work with 256 rounds anyway because a list can only store MAX_INT values
        return fishList.count().toLong()
    }
}
