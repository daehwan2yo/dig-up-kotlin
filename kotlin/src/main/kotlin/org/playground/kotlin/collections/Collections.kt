package org.playground.kotlin.collections

interface Iterable<out T> {
    operator fun iterator(): Iterator<T>
}

interface MutableIterable<out T> : Iterable<T> {
    override fun iterator(): MutableIterator<T>
}

interface Collection<out E> : Iterable<E> {
    val size: Int
    fun isEmpty(): Boolean
    fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean
    operator fun contains(element: @UnsafeVariance E): Boolean
    override fun iterator(): Iterator<E>
}

interface MutableCollection<E> : Collection<E>, MutableIterable<E> {
    override fun iterator(): MutableIterator<E>
    fun add(element: E): Boolean
    fun remove(element: E): Boolean
    fun addAll(elements: Collection<E>): Boolean
    fun removeAll(elements: Collection<E>): Boolean
    fun retainAll(elements: Collection<E>): Boolean
    fun clear(): Unit
}

interface List<out E> : Collection<E> {
    override val size: Int
    override fun isEmpty(): Boolean
    override fun contains(element: @UnsafeVariance E): Boolean
    override fun iterator(): Iterator<E>
    override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean

    operator fun get(index: Int): E
    fun indexOf(element: @UnsafeVariance E): Int
    fun lastIndexOf(element: @UnsafeVariance E): Int
    fun listIterator(): ListIterator<E>
    fun listIterator(index: Int): ListIterator<E>
    fun subList(fromIndex: Int, toIndex: Int): List<E>
}

interface MutableList<E> : List<E>, MutableCollection<E> {
    override fun add(element: E): Boolean
    override fun remove(element: E): Boolean
    override fun addAll(elements: Collection<E>): Boolean
    override fun removeAll(elements: Collection<E>): Boolean
    override fun retainAll(elements: Collection<E>): Boolean
    override fun clear(): Unit

    fun addAll(index: Int, elements: Collection<E>): Boolean
    operator fun set(index: Int, element: E): E
    fun add(index: Int, element: E): Unit
    fun removeAt(index: Int): E

    // List Iterators
    override fun listIterator(): MutableListIterator<E>
    override fun listIterator(index: Int): MutableListIterator<E>

    // View
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E>
}