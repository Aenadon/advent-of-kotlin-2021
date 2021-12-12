package day12

data class Node(
    val id: String,
    val to: Set<String>
) {
    val small = id.toLowerCase() == id
}