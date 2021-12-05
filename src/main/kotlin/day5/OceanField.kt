package day5

class OceanField {
    private val ocean = HashMap<Int, HashMap<Int, Int>>()

    fun draw(line: Line, drawDiagonals: Boolean = false) {
        if (line.isDiagonal) {
            if (drawDiagonals) {
                drawDiagonal(line)
            }
        } else {
            drawHorizontalVertical(line)
        }
    }

    private fun drawDiagonal(line: Line) {
        val rangeX = if (line.fromX <= line.toX) line.fromX..line.toX else line.fromX downTo line.toX
        val rangeY = if (line.fromY <= line.toY) line.fromY..line.toY else line.fromY downTo line.toY

        rangeX.zip(rangeY).forEach {
            val x = it.first
            val y = it.second

            if (!ocean.containsKey(x)) {
                ocean[x] = HashMap()
            }
            ocean[x]!![y] = (ocean[x]!![y] ?: 0) + 1
        }
    }

    private fun drawHorizontalVertical(line: Line) {
        val rangeX = if (line.fromX <= line.toX) line.fromX..line.toX else line.fromX downTo line.toX
        val rangeY = if (line.fromY <= line.toY) line.fromY..line.toY else line.fromY downTo line.toY

        rangeX.forEach { x ->
            rangeY.forEach { y ->
                if (!ocean.containsKey(x)) {
                    ocean[x] = HashMap()
                }
                ocean[x]!![y] = (ocean[x]!![y] ?: 0) + 1
            }
        }
    }

    fun countDangerousAreas(): Int {
        return ocean.values.flatMap { it.values }.count { it >= 2 }
    }
}