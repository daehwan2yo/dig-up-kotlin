package collection

import IndexOutOfBoundsException
import NoSuchElementException
import io.Serializable


/**
 * Empty 한 Iterator 를 리턴하기 위한 싱글톤 object
 */
internal object EmptyIterator : ListIterator<Nothing> {
    override fun hasNext(): Boolean = false
    override fun hasPrevious(): Boolean = false
    override fun nextIndex(): Int = 0
    override fun previousIndex(): Int = -1
    override fun next(): Nothing = throw NoSuchElementException()
    override fun previous(): Nothing = throw NoSuchElementException()
}

/**
 * Empty 한 List 를 리턴하기 위한 싱글톤 object
 */
internal object EmptyList : List<Nothing>, Serializable, RandomAccess {
    // serialVersionUID 생략

    // override Any
    override fun equals(other: Any?): Boolean = other is List<*> && other.isEmpty()
    override fun hashCode(): Int = 1
    override fun toString(): String = "[]"

    // size 는 List interface 에서 val 로 선언되어 메서드가 아닌 필드로 override
    // - property 의 getter 는 별도 구현
    override val size: Int get() = 0

    // override List
    override fun isEmpty(): Boolean = true
    override fun contains(element: Nothing): Boolean = false
    override fun containsAll(elements: Collection<Nothing>): Boolean = false

    override fun get(index: Int): Nothing = throw IndexOutOfBoundsException()
    override fun indexOf(element: Nothing): Int = -1
    override fun lastIndexOf(element: Nothing): Int = -1

    override fun iterator(): Iterator<Nothing> = EmptyIterator
    override fun listIterator(): ListIterator<Nothing> = EmptyIterator
    override fun listIterator(index: Int): ListIterator<Nothing> {
        // index 가 0 인 경우가 아니면 예외 발생
        if (index != 0) throw IndexOutOfBoundsException()
        return EmptyIterator
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<Nothing> {
        // fromIndex, toIndex 모두 0 이 아니면 예외 발생
        if (fromIndex != 0 && toIndex != 0) throw IndexOutOfBoundsException()
        return this
    }

    private fun readResolve(): Any = EmptyList
}

/**
 * immutable 한 empty list 를 반환한다.
 */
fun <T> emptyList(): List<T> = EmptyList

fun <T> listOf(vararg elements: T): List<T> = if (elements.size > 0) elements.asList() else emptyList()

fun <T> mutableListOf(vararg elements: T): MutableList<T> =
    // 입력된 element 가 없으면 빈 ArrayList 를, 있다면 MutableList 구현체를 반환해준다.
    if(elements.size == 0) ArrayList() // TODO