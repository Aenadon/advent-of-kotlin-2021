package day4

class Day4 {

    private val input = InputReader.readInput(4, test = false)

    fun solve1() {
        val drawnNumbers = input[0].split(",")
        val boards = input.drop(1)
            .chunked(5)
            .map { createBoard(it) }
            .map { BingoBoard(it) }

        drawnNumbers.forEach { num ->
            boards.forEach {
                val checkWin = it.markAndCheck(num)
                if (checkWin) {
                    println(num.toInt() * it.calculateScore())
                    return
                }
            }
        }

        println("FAIL")
    }

    private fun createBoard(rawBoard: List<String>): List<List<String>> {
        return rawBoard.map {
            it.trim()
                .replace("\\s{2,}".toRegex(), " ")
                .split(" ")
        }
    }

    fun solve2() {
        val drawnNumbers = input[0].split(",")
        val boards = input.drop(1)
            .chunked(5)
            .map { createBoard(it) }
            .map { BingoBoard(it) }

        var lastWinner: Int? = null
        drawnNumbers.forEach { num ->
            boards.forEach {
                val checkWin = it.markAndCheck(num)
                if (checkWin) {
                    lastWinner = num.toInt() * it.calculateScore()
                }
            }
        }

        println(lastWinner)
    }
}