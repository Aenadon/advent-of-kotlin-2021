package day5

data class Line(
    val fromX: Int,
    val fromY: Int,
    val toX: Int,
    val toY: Int,
) {
    val isDiagonal = fromX != toX && fromY != toY
}