package day18

import kotlin.math.ceil
import kotlin.math.floor

interface Element {
    var depth: Int

    operator fun plus(e: Element): Element {
        this.increaseDepth()
        e.increaseDepth()
        return PairElement(this, e, 0)
    }

    fun magnitude(): Long

    fun reduceUntilDone(): Element
    fun explode(lastLeft: Element? = null, firstRight: Element? = null)
    fun split(lastLeft: Element? = null, firstRight: Element? = null)
    fun requiresExplosion(): Boolean
    fun requiresSplit(): Boolean

    fun increaseDepth()

    fun addToLeftmost(number: Int)
    fun addToRightmost(number: Int)

    fun copy(): Element
}

class PairElement(
    var first: Element,
    var second: Element,
    override var depth: Int,
) : Element {
    override fun magnitude(): Long {
        return 3 * first.magnitude() + 2 * second.magnitude()
    }

    override fun reduceUntilDone(): Element {
        while (true) {
            try {
                explode(null, null)
                split(null, null)
                break
            } catch (e: HasPerformedOperationException) {
                continue
            }
        }
        return this
    }

    override fun explode(lastLeft: Element?, firstRight: Element?) {
        var hasDoneOperation = false
        if (first.requiresExplosion()) {
            val firstPair = (first as PairElement)
            lastLeft?.addToRightmost((firstPair.first as NumberElement).number)
            second.addToLeftmost((firstPair.second as NumberElement).number)
            first = NumberElement(0, first.depth)
            hasDoneOperation = true
        }
        if (hasDoneOperation) {
            throw HasPerformedOperationException()
        }
        if (second.requiresExplosion()) {
            val secondPair = (second as PairElement)
            first.addToRightmost((secondPair.first as NumberElement).number)
            firstRight?.addToLeftmost((secondPair.second as NumberElement).number)
            second = NumberElement(0, second.depth)
            hasDoneOperation = true
        }
        if (hasDoneOperation) {
            throw HasPerformedOperationException()
        }

        first.explode(lastLeft, second)
        second.explode(first, firstRight)
    }

    override fun split(lastLeft: Element?, firstRight: Element?) {
        first.split(lastLeft, second)

        var hasDoneOperation = false
        if (first.requiresSplit()) {
            val numberElement = (first as NumberElement)
            first = PairElement(
                first = NumberElement(floor(numberElement.number / 2.0).toInt(), first.depth + 1),
                second = NumberElement(ceil(numberElement.number / 2.0).toInt(), first.depth + 1),
                depth = first.depth
            )
            hasDoneOperation = true
        }
        if (hasDoneOperation) {
            throw HasPerformedOperationException()
        }

        second.split(first, firstRight)

        if (second.requiresSplit()) {
            val numberElement = (second as NumberElement)
            second = PairElement(
                first = NumberElement(floor(numberElement.number / 2.0).toInt(), second.depth + 1),
                second = NumberElement(ceil(numberElement.number / 2.0).toInt(), second.depth + 1),
                depth = second.depth
            )
            hasDoneOperation = true
        }
        if (hasDoneOperation) {
            throw HasPerformedOperationException()
        }
    }

    override fun requiresExplosion(): Boolean {
        return depth == 4
    }

    override fun requiresSplit(): Boolean {
        return false
    }

    override fun increaseDepth() {
        this.depth++
        first.increaseDepth()
        second.increaseDepth()
    }

    override fun addToLeftmost(number: Int) {
        first.addToLeftmost(number)
    }

    override fun addToRightmost(number: Int) {
        second.addToRightmost(number)
    }

    override fun copy(): Element {
        return PairElement(
            first = first.copy(),
            second = second.copy(),
            depth = depth,
        )
    }

    override fun toString(): String {
        return "[$first, $second]"
    }
}

class NumberElement(
    var number: Int,
    override var depth: Int,
) : Element {
    override fun magnitude(): Long {
        return number.toLong()
    }

    override fun reduceUntilDone(): Element {
        return this
    }

    override fun explode(lastLeft: Element?, firstRight: Element?) {
        // do nothing :/
    }

    override fun split(lastLeft: Element?, firstRight: Element?) {
        // do nothing :/
    }

    override fun requiresExplosion(): Boolean {
        return false
    }

    override fun requiresSplit(): Boolean {
        return number >= 10
    }

    override fun increaseDepth() {
        this.depth++
    }

    override fun addToLeftmost(number: Int) {
        this.number += number
    }

    override fun addToRightmost(number: Int) {
        this.number += number
    }

    override fun copy(): Element {
        return NumberElement(
            number = number,
            depth = depth
        )
    }

    override fun toString(): String {
        return "$number"
    }
}