package day20

class Day20 {

    private val input = InputReader.readInput(20, split = false, test = false)

    fun solve1() {
        var (replacerAlgorithm, image) = readData()
        repeat(2) {
            image = image.enhance(it, replacerAlgorithm)
        }
        println(image.sumOf { it.sum() })
    }

    private fun readData(): Pair<List<Int>, List<List<Int>>> {
        val split = input[0].split("\n\n")
        val replacerAlgorithm = split[0].split("").filterNot { it.isBlank() }.map { if (it == "#") 1 else 0 }
        val image = split[1].split("\n").filter { it.isNotBlank() }.map {
            it.split("").filterNot { it.isBlank() }.map { if (it == "#") 1 else 0 }
        }
        return Pair(replacerAlgorithm, image)
    }

    fun solve2() {
        var (replacerAlgorithm, image) = readData()
        repeat(50) {
            image = image.enhance(it, replacerAlgorithm)
        }
        println(image.sumOf { it.sum() })
    }

}

private fun List<List<Int>>.enhance(iteration: Int, replacerAlgorithm: List<Int>): List<List<Int>> {
    val default = if (iteration % 2 == 1) replacerAlgorithm[0] else 0
    val emptyRow = IntArray(this[0].size + 2) { default }
    val inputImage = listOf(emptyRow.toList()) + this.map { listOf(default) + it + default } + listOf(emptyRow.toList())
    return inputImage.mapIndexed { y, row ->
        List(row.size) { x ->
            replacerAlgorithm[inputImage.calculateIndex(y, x, default)]
        }
    }
}

private fun List<List<Int>>.calculateIndex(y: Int, x: Int, default: Int): Int {
    val digits = listOf(
        this.fetch(y - 1, x - 1, default),
        this.fetch(y - 1, x, default),
        this.fetch(y - 1, x + 1, default),
        this.fetch(y, x - 1, default),
        this.fetch(y, x, default),
        this.fetch(y, x + 1, default),
        this.fetch(y + 1, x - 1, default),
        this.fetch(y + 1, x, default),
        this.fetch(y + 1, x + 1, default),
    )

    return digits.joinToString("").toInt(2)
}

private fun List<List<Int>>.fetch(y: Int, x: Int, default: Int): Int {
    return this.getOrNull(y)?.getOrNull(x) ?: default
}
