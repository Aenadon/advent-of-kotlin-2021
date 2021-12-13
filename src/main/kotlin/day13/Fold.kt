package day13

data class Fold(
    val direction: FoldDirection,
    val position: Int
)

enum class FoldDirection {
    X,
    Y;

    companion object {
        fun fromString(string: String): FoldDirection {
            return values().find { it.name == string.toUpperCase() } ?: throw IllegalArgumentException(string)
        }
    }
}
