package day9

import InputReader

class Day9 {

    private val input = InputReader.readInput(9, split = true, test = false)

    private val positionCache = HashSet<Pair<Int, Int>>()

    fun solve1() {
        println(input.indices.sumOf { rowIndex ->
            val row = input[rowIndex]
            row.indices.sumOf { colIndex ->
                val value = row[colIndex]
                val isLow = isLow(colIndex, rowIndex, value, row)

                if (isLow) 1 + value.toString().toInt() else 0
            }
        })
    }

    private fun isLow(colIndex: Int, rowIndex: Int, value: Char, row: String) =
        (colIndex == 0 || input[rowIndex][colIndex - 1] > value) &&
                (colIndex == row.length - 1 || input[rowIndex][colIndex + 1] > value) &&
                (rowIndex == 0 || input[rowIndex - 1][colIndex] > value) &&
                (rowIndex == input.size - 1 || input[rowIndex + 1][colIndex] > value)

    fun solve2() {
        val lowPoints = input.indices.flatMap { rowIndex ->
            val row = input[rowIndex]
            row.indices.filter { colIndex ->
                val value = row[colIndex]
                isLow(colIndex, rowIndex, value, row)
            }.map { colIndex -> Pair(rowIndex, colIndex) }
        }

        val result = lowPoints
            .map { findCraterSize(it) }
            .sortedByDescending { it }
            .take(3)
            .reduce { a, b -> a * b }

        println(result)
    }

    private fun findCraterSize(position: Pair<Int, Int>): Int {
        if (position.first < 0 || position.first >= input.size) {
            return 0
        } else if (position.second < 0 || position.second >= input[0].length) {
            return 0
        } else if (input[position.first][position.second] == '9') {
            return 0
        } else if (positionCache.contains(position)) {
            return 0
        }

        positionCache.add(position)
        return 1 + findCraterSize(Pair(position.first - 1, position.second)) +
                findCraterSize(Pair(position.first, position.second + 1)) +
                findCraterSize(Pair(position.first + 1, position.second)) +
                findCraterSize(Pair(position.first, position.second - 1))
    }
}
