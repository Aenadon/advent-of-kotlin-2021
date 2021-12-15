package day15

import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Day15 {

    private val input = InputReader.readInput(15, test = false)

    fun solve1() {
        val numberField = parseToNumberField()
        val graph = parseToGraph(numberField)
        performDijkstraToTarget(graph)
    }

    private fun parseToNumberField(): MutableList<MutableList<Int>> {
        return input.map { row ->
            row.split("").filter { it.isNotBlank() }.map { it.toInt() }.toMutableList()
        }.toMutableList()
    }

    private fun parseToGraph(input: List<List<Int>>): List<Node> {
        val nodes = input.mapIndexed { y, row ->
            row.mapIndexed { x, element ->
                Node(element, isFinalNode = y == input.size - 1 && x == input[0].size - 1)
            }
        }

        nodes.forEachIndexed { y, row ->
            row.forEachIndexed { x, node ->
                if (x > 0) {
                    node.neighbors.add(row[x - 1])
                }
                if (x < row.size - 1) {
                    node.neighbors.add(row[x + 1])
                }
                if (y > 0) {
                    node.neighbors.add(nodes[y - 1][x])
                }
                if (y < nodes.size - 1) {
                    node.neighbors.add(nodes[y + 1][x])
                }
            }
        }
        return nodes.flatten()
    }

    private fun performDijkstraToTarget(graph: List<Node>) {
        // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Using_a_priority_queue
        val nodes = ArrayList(graph)
        val source = nodes[0]

        val dist = HashMap<Node, Int>().withDefault { Int.MAX_VALUE }
        dist[source] = 0

        val prev = HashMap<Node, Node>()

        val priorityQueue = UpdatablePriorityQueue<Node>()
        priorityQueue.addAll(nodes) { node -> dist.getValue(node) }

        val final: Node
        while (true) {
            val u = priorityQueue.poll()
            if (u.isFinalNode) {
                final = u
                break
            }
            u.neighbors.forEach { v ->
                val alt = dist.getValue(u) + v.riskLevel
                if (alt < dist.getValue(v)) {
                    dist[v] = alt
                    prev[v] = u
                    priorityQueue.updatePriority(v, alt)
                }
            }
        }

        var u = final
        var score = 0
        while (u != source) {
            score += u.riskLevel
            u = prev[u]!!
        }
        println(score)
    }

    fun solve2() {
        val numberField = parseToNumberField()
        val originalField = numberField.map { ArrayList(it) }

        numberField.forEachIndexed { index, row ->
            (1..4).forEach { offset ->
                row.addAll(originalField[index].map {
                    val result = it + offset
                    if (result < 10) result else (result % 10) + 1
                })
            }
        }
        (1..4).forEach { offset ->
            originalField.indices.forEach { index ->
                numberField.add(numberField[index].map {
                    val result = it + offset
                    if (result < 10) result else (result % 10) + 1
                }.toMutableList())
            }
        }

        val graph = parseToGraph(numberField)
        performDijkstraToTarget(graph)
    }

}