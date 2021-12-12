package day12

class Day12 {

    private val input = InputReader.readInput(12, split = true, test = false)
    private val nodes = parseNodes()

    fun solve1() {
        println(traverseAndCount1("start"))
    }

    private fun parseNodes(): HashMap<String, Node> {
        val parsedNodes = HashMap<String, Node>()
        input.map { it.split('-') }
            .groupBy { it[0] }
            .mapValues { it.value.map { it[1] }.toSet() }
            .forEach { (id, nodeIds) ->
                val node = parsedNodes[id]
                val oldTargets = node?.to ?: emptySet()

                if (id != "start") {
                    nodeIds.forEach {
                        if (parsedNodes.containsKey(it)) {
                            parsedNodes[it] = Node(it, parsedNodes[it]!!.to + id)
                        } else {
                            parsedNodes[it] = Node(it, setOf(id))
                        }
                    }
                }

                parsedNodes[id] = Node(id, oldTargets + nodeIds)
            }
        return parsedNodes
    }

    private fun traverseAndCount1(nodeId: String, visitedCaves: Set<String> = emptySet()): Int {
        val node = nodes[nodeId]!!
        if (node.small && visitedCaves.contains(nodeId)) {
            return 0
        }

        val updatedVisitedSmallCaves = visitedCaves + node.id
        val subSum = node.to.sumOf {
            traverseAndCount1(it, updatedVisitedSmallCaves)
        }

        val thisNodeCount = if (node.id == "end") 1 else 0

        return thisNodeCount + subSum
    }

    fun solve2() {
        println(traverseAndCount2("start"))
    }

    private fun traverseAndCount2(
        nodeId: String,
        mayRevisitSmallCave: Boolean = true,
        visitedCaves: Set<String> = emptySet()
    ): Int {
        if (nodeId == "end") {
            return 1
        }

        var mayRevisit = mayRevisitSmallCave

        val node = nodes[nodeId]!!
        if (node.small && visitedCaves.contains(nodeId)) {
            if (mayRevisitSmallCave) {
                mayRevisit = false
            } else {
                return 0
            }
        }

        val updatedVisitedSmallCaves = visitedCaves + node.id
        val subSum = node.to.filter { it != "start" }.sumOf {
            traverseAndCount2(it, mayRevisit, updatedVisitedSmallCaves)
        }

        return subSum
    }
}