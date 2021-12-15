package day15

// No data class! We absolutely don't want an implicit set-breaking hashcode implementation here
class Node(
    val riskLevel: Int,
    val isFinalNode: Boolean
) {
    val neighbors: MutableSet<Node> = HashSet()

    override fun toString(): String {
        return "Node(riskLevel=$riskLevel, isFinalNode=$isFinalNode, neighbors=${neighbors.map { it.riskLevel }})"
    }
}