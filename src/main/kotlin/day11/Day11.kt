package day11

import InputReader
import kotlin.math.max
import kotlin.math.min

class Day11 {

    private val input = InputReader.readInput(11, split = true, test = false)
        .map { it.split("").filter { it.isNotBlank() }.map { it.toInt() }.toMutableList() }

    fun solve1() {
        var flashCount = 0
        repeat(100) {
            input.forEach { row ->
                row.indices.forEach { colIndex ->
                    row[colIndex]++
                }
            }
            input.forEachIndexed { rowIndex, row ->
                row.indices.forEach { colIndex ->
                    if (row[colIndex] == 10) {
                        increaseSurroundingOctopuses(rowIndex, colIndex)
                    }
                }
            }
            input.forEachIndexed { rowIndex, row ->
                row.indices.forEach { colIndex ->
                    if (row[colIndex] >= 10) {
                        flashCount++
                        row[colIndex] = 0
                    }
                }
            }
        }
        println(flashCount)
    }

    private fun increaseSurroundingOctopuses(rowIndex: Int, colIndex: Int) {
        val minRow = max(0, rowIndex - 1)
        val maxRow = min(input.size - 1, rowIndex + 1)

        val minCol = max(0, colIndex - 1)
        val maxCol = min(input[0].size - 1, colIndex + 1)

        (minRow..maxRow).forEach { row ->
            (minCol..maxCol).forEach { col ->
                if (input[row][col] != 10) {
                    input[row][col] = input[row][col] + 1
                    if (input[row][col] == 10) {
                        input[row][col] = 11
                        increaseSurroundingOctopuses(row, col)
                    }
                }
            }
        }
    }

    fun solve2() {
        var iterationCount = 1
        while (true) {
            input.forEach { row ->
                row.indices.forEach { colIndex ->
                    row[colIndex]++
                }
            }
            input.forEachIndexed { rowIndex, row ->
                row.indices.forEach { colIndex ->
                    if (row[colIndex] == 10) {
                        increaseSurroundingOctopuses(rowIndex, colIndex)
                    }
                }
            }
            input.forEachIndexed { rowIndex, row ->
                row.indices.forEach { colIndex ->
                    if (row[colIndex] >= 10) {
                        row[colIndex] = 0
                    }
                }
            }
            if (input.sumOf { it.sum() } == 0) {
                println(iterationCount)
                return
            }
            iterationCount++
        }
    }
}