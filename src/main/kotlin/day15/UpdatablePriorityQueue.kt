package day15

import java.util.*
import kotlin.NoSuchElementException
import kotlin.collections.HashMap

class UpdatablePriorityQueue<T> {
    private val map = HashMap<Int, MutableSet<T>>().toSortedMap()
    private val currentPriority = HashMap<T, Int>()

    fun addAll(elements: Collection<T>, priorityCalculator: (T) -> Int) {
        elements.forEach {
            add(it, priorityCalculator(it))
        }
    }

    private fun add(element: T, priority: Int) {
        map.computeIfAbsent(priority) { HashSet() }.add(element)
        currentPriority[element] = priority
    }

    fun updatePriority(element: T, priority: Int) {
        map[currentPriority[element]]?.remove(element)
        add(element, priority)
    }

    fun poll(): T {
        val firstMap = map.values.first { it.isNotEmpty() }
        val first = firstMap.firstOrNull() ?: throw NoSuchElementException()
        firstMap.remove(first)
        currentPriority.remove(first)
        return first
    }
}