package collection

interface Collection<out E> : Iterable<E> {
    val size: Int

    fun isEmpty(): Boolean
    fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean

    operator fun contains(elements: @UnsafeVariance E): Boolean

    override fun iterator(): Iterable.Iterator<E>
}

interface MutableCollection<E>: Collection<E>, MutableIterable<E> {
    override fun iterator(): MutableIterable.MutableIterator<E>

    fun add(element: E): Boolean
    fun remove(element: E): Boolean
    fun addAll(elements: Collection<E>): Boolean
    fun removeAll(elements: Collection<E>): Boolean
    fun retainAll(elements: Collection<E>): Boolean
    fun clear(): Unit
}