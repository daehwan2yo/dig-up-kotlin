package org.playground.coroutine

import org.playground.coroutine.CoroutineContext.Element
import org.playground.coroutine.CoroutineContext.Key
import java.io.Serializable

/**
 *
 */
abstract class AbstractCoroutineContextElement(
    override val key: Key<*>
) : Element

/**
 * CoroutineContext.Key 의 base implementation.
 * B : base class of a polymorphic element
 * baseKey : an instance of base key
 * E : element type associated with the current key
 * safeCast : a function that can safely cast abstract CoroutineContext.Element to the concrete E type
 * and return the element if it is a subtype of E or null otherwise.
 */
abstract class AbstractCoroutineContextKey<B : Element, E : B>(
    baseKey: Key<B>,
    private val safeCast: (element: Element) -> E?
) : Key<E> {
    private val topmostKey: Key<*> = if (baseKey is AbstractCoroutineContextKey<*, *>) baseKey.topmostKey else baseKey

    internal fun tryCast(element: Element): E? = safeCast(element)
    internal fun isSubKey(key: Key<*>): Boolean = key === this || topmostKey === key
}

object EmptyCoroutineContext : CoroutineContext, Serializable {
    private const val serialVersionUID: Long = 0
    private fun readResolve(): Any = EmptyCoroutineContext

    // key 에 대응하는 context element 를 가지고 있지 않다.
    override fun <E : Element> get(key: Key<E>): E? = null

    // 별도의 operation 을 수행하지 않는다.
    override fun <R> fold(initial: R, operation: (R, Element) -> R): R = initial

    // minus 대상이 없어 본인 (empty) 을 그대로 반환한다.
    override fun minusKey(key: CoroutineContext.Key<*>) = this

    override fun hashCode() = 0

    override fun toString() = "EmptyCoroutineContext"
}

/**
 * 해당 클래스는 노출되지 않고 internal 하게만 사용된다.
 * left-based list 로, plus 는 우로 자연스럽게 적용되어진다.
 */
internal class CombinedContext(
    private val left: CoroutineContext,
    private val element: Element
) : CoroutineContext, Serializable {
    override fun <E : Element> get(key: Key<E>): E? {
        var cur = this
        while (true) {
            // 현재 context 의 key 의 값이 존재한다면 (일치하는 값을 찾았다면) 반환
            cur.element[key]?.let { return it }

            // 현재 context 의 left 를 target 으로 반복
            val next = cur.left
            if (next is CombinedContext) {
                cur = next
            }
            // 다음 target 이 CombinedContext 가 아니라면 (끝 이라면) 그대로 반환
            else next[key] // nullable
        }
    }

    override fun <R> fold(initial: R, operation: (R, Element) -> R): R =
        // left 에 대해 재귀
        operation(left.fold(initial, operation), element)

    override fun minusKey(key: Key<*>): CoroutineContext {
        // 현재 element 와 key 가 matching 된다면 (찾았다면) left (다음값) 반환
        element[key]?.let { return left }

        val newLeft = left.minusKey(key)
        return when {
            newLeft === left -> this
            newLeft === EmptyCoroutineContext -> element
            else -> CombinedContext(newLeft, element)
        }
    }

    private fun size(): Int {
        var cur = this
        var size = 2
        while (true) {
            cur = cur.left as? CombinedContext ?: return size
            size++
        }
    }

    private fun contains(element: Element): Boolean =
        get(element.key) == element

    private fun containsAll(context: CombinedContext): Boolean {
        var cur = context
        while (true) {
            if (!contains(cur.element)) return false
            val next = cur.left
            if (next is CombinedContext) {
                cur = next
            } else {
                return contains(next as Element)
            }
        }
    }

    override fun equals(other: Any?): Boolean =
        this === other || other is CombinedContext && other.size() == size() && other.containsAll(this)

    override fun hashCode(): Int = left.hashCode() + element.hashCode()

    override fun toString(): String =
        "[" + fold("") { acc, element ->
            if (acc.isEmpty()) element.toString() else "$acc, $element"
        } + "]"

    private fun writeReplace(): Any {
        val n = size()
        val elements = arrayOfNulls<CoroutineContext>(n)
        var index = 0
        fold(Unit) { _, element -> elements[index++] = element }
        check(index == n)
        @Suppress("UNCHECKED_CAST")
        return Serialized(elements as Array<CoroutineContext>)
    }

    private class Serialized(val elements: Array<CoroutineContext>) : Serializable {
        companion object {
            private const val serialVersionUID: Long = 0L
        }

        private fun readResolve(): Any = elements.fold(EmptyCoroutineContext, CoroutineContext::plus)
    }
}