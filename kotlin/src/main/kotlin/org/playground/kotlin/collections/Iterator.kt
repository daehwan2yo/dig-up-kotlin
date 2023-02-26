package org.playground.kotlin.collections

interface Iterator<out T> {
    operator fun next(): T
    operator fun hasNext(): Boolean
}

interface MutableIterator<out T> : Iterator<T> {
    fun remove(): Unit
}

interface ListIterator<out T> : Iterator<T> {
    override fun next(): T
    override fun hasNext(): Boolean

    fun hasPrevious(): Boolean
    fun previous(): T
    fun nextIndex(): Int
    fun previousIndex(): Int
}

interface MutableListIterator<T> : ListIterator<T>, MutableIterator<T> {
    override fun next(): T
    override fun hasNext(): Boolean
    override fun remove()

    fun set(element: T) : Unit
    fun add(element: T) : Unit
}