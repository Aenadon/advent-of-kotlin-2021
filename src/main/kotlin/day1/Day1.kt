package day1

class Day1 {

    private val input = InputReader.readIntLineInput(1, test = false)

    fun solve1() {
        println(input.windowed(size = 2).count { it[0] < it[1] })
    }

    fun solve2() {
        println(input.windowed(size = 4).count { it.slice(0..2).sum() < it.slice(1..3).sum() })
    }

}