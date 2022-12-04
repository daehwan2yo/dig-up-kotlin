package collection

interface Iterable<out T> {
    operator fun iterator(): Iterator<T>

    interface Iterator<out T> {
        operator fun next(): T
        operator fun hasNext(): Boolean
    }
}

interface MutableIterable<out T> : Iterable<T>, Mutable<T> {
    override fun iterator(): MutableIterator<T>

    interface MutableIterator<out T> : Iterable.Iterator<T> {
        fun remove(): Unit
    }
}

interface ListIterator<out T> : Iterable.Iterator<T> {
    // Query Operations
    override fun next(): T
    override fun hasNext(): Boolean

    fun hasPrevious(): Boolean
    fun previous(): T
    fun nextIndex(): Int
    fun previousIndex(): Int
}
