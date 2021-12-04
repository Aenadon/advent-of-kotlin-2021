package day4

class BingoBoard(private var rows: List<List<String>>) {
    var hasWonAlready = false

    fun markAndCheck(number: String): Boolean {
        if (hasWonAlready) {
            return false
        }
        rows = rows.map { row ->
            row.map { if (it == number) "X" else it }
        }

        (0..4).forEach { c1 ->
            val column = (0..4).fold(true) { acc, c2 ->
                acc && rows[c1][c2] == "X"
            }

            val row = (0..4).fold(true) { acc, c2 ->
                acc && rows[c2][c1] == "X"
            }

            if (column || row) {
                hasWonAlready = true
                return true
            }
        }
        return false
    }

    fun calculateScore(): Int {
        return rows.map { it.filter { it != "X" }.map { it.toInt() }.sum() }.sum()
    }
}