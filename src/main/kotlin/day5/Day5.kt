package day5

class Day5 {

    private val input = InputReader.readInput(5, test = false)
        .map {
            val raw = it.split(" -> ").flatMap { it.split(",") }
            Line(raw[0].toInt(), raw[1].toInt(), raw[2].toInt(), raw[3].toInt())
        }

    fun solve1() {
        val ocean = OceanField()
        input.forEach { ocean.draw(it) }
        println(ocean.countDangerousAreas())
    }

    fun solve2() {
        val ocean = OceanField()
        input.forEach { ocean.draw(it, drawDiagonals = true) }
        println(ocean.countDangerousAreas())
    }
}