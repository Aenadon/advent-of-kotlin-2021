package day25

enum class Cucumber(val symbol: String) {
    EAST(">"),
    SOUTH("v");

    companion object {
        fun fromSymbol(symbol: String): Cucumber? {
            if (symbol == ".") {
                return null
            }
            return values().find { it.symbol == symbol } ?: throw IllegalArgumentException(symbol)
        }
    }
}