package day25

class Day25 {

    private val input = InputReader.readInput(25, test = false)

    fun solve1() {
        val field = readField()
        var completedSteps = 0
        while (true) {
            val hadEastChanges = field.processMovement(Cucumber.EAST)
            val hadSouthChanges = field.processMovement(Cucumber.SOUTH)

            completedSteps++

            if (!hadEastChanges && !hadSouthChanges) {
                println(completedSteps)
                return
            }
        }
    }

    private fun readField(): Array<Array<Cucumber?>> {
        return input.map {
            it.split("").filter { it.isNotBlank() }.map { Cucumber.fromSymbol(it) }.toTypedArray()
        }.toTypedArray()
    }

    fun solve2() {
        println("No part 2 for day 25.")
    }

}

private fun Array<Array<Cucumber?>>.processMovement(movingCucumbers: Cucumber): Boolean {
    val markedCucumbers = ArrayList<Pair<Pos, Pos>>()

    this.forEachIndexed { y, row ->
        row.forEachIndexed { x, cucumber ->
            if (cucumber == movingCucumbers) {
                val nextX = when (movingCucumbers) {
                    Cucumber.EAST -> (x + 1) % row.size
                    Cucumber.SOUTH -> x
                }
                val nextY = when (movingCucumbers) {
                    Cucumber.EAST -> y
                    Cucumber.SOUTH -> (y + 1) % this.size
                }

                if (this[nextY][nextX] == null) {
                    markedCucumbers.add(Pair(Pos(x, y), Pos(nextX, nextY)))
                }
            }
        }
    }

    markedCucumbers.forEach {
        val from = it.first
        val to = it.second

        this[to.y][to.x] = this[from.y][from.x]
        this[from.y][from.x] = null
    }

    return markedCucumbers.isNotEmpty()
}
