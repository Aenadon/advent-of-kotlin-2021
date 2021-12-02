package day2

class Day2 {

    private val input = InputReader.readInput(2, test = false)

    fun solve1() {
        val result = input.fold(Pair(0, 0)) { acc, element ->
            val split = element.split(' ')
            val step = split[1].toInt()
            when(split[0]) {
                "forward" -> acc.copy(first = acc.first + step)
                "down" -> acc.copy(second = acc.second + step)
                "up" -> acc.copy(second = acc.second - step)
                else -> throw IllegalArgumentException("Unknown direction")
            }
        }
        println(result.first * result.second)
    }

    fun solve2() {
        val result = input.fold(Triple(0, 0, 0)) { acc, element ->
            val split = element.split(' ')
            val step = split[1].toInt()
            when(split[0]) {
                "forward" -> acc.copy(first = acc.first + step, second = acc.second + acc.third * step)
                "down" -> acc.copy(third = acc.third + step)
                "up" -> acc.copy(third = acc.third - step)
                else -> throw IllegalArgumentException("Unknown direction")
            }
        }
        println(result.first * result.second)
    }
}