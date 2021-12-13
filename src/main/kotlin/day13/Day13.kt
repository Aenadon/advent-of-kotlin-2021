package day13

class Day13 {

    private val input = InputReader.readInput(13, split = false, test = false)

    fun solve1() {
        solve(limit = 1, printField = false)
    }

    private fun solve(limit: Int? = null, printField: Boolean = true) {
        val (field, instructions) = parseData()

        var maxY = field.size
        var maxX = field[0].size

        val instructionList = if (limit != null) instructions.take(limit) else instructions
        instructionList.forEach { i ->
            when (i.direction) {
                FoldDirection.X -> {
                    val one = if (maxX % 2 == 1) 1 else 0
                    (i.position + 1 until maxX).forEach { sourceX ->
                        val targetX = maxX - one - sourceX
                        (0 until maxY).forEach { targetY ->
                            field[targetY][targetX] = field[targetY][targetX] || field[targetY][sourceX]
                        }
                    }
                    maxX = i.position
                }
                FoldDirection.Y -> {
                    val one = if (maxY % 2 == 1) 1 else 0
                    (i.position + 1 until maxY).forEach { sourceY ->
                        val targetY = maxY - one - sourceY
                        (0 until maxX).forEach { targetX ->
                            field[targetY][targetX] = field[targetY][targetX] || field[sourceY][targetX]
                        }
                    }
                    maxY = i.position
                }
            }
        }

        val activePoints = (0 until maxY).sumOf { y ->
            if (printField) {
                println()
            }
            (0 until maxX).count { x ->
                if (printField) {
                    print(if (field[y][x]) 'ß' else ' ')
                }
                field[y][x]
            }
        }
        println()
        println(activePoints)
    }

    private fun parseData(): Pair<Array<BooleanArray>, List<Fold>> {
        val split = input[0].split("\n\n")
        val rawField = split[0].split("\n").map { it.split(",").map { it.toInt() } }
        val maxX = rawField.maxOf { it[0] } + 1
        val maxY = rawField.maxOf { it[1] } + 1

        val dotCache = rawField.groupBy({ it[1] }, { it[0] })

        val field = Array(maxY) { y -> BooleanArray(maxX) { x -> dotCache.containsKey(y) && dotCache[y]!!.contains(x) } }

        val instructions = split[1].split("\n").map {
            val rawFold = it.split(" ")[2].split("=")
            Fold(FoldDirection.fromString(rawFold[0]), rawFold[1].toInt())
        }

        return Pair(field, instructions)
    }

    fun solve2() {
        solve()
    }
}