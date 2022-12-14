package collection

interface Collection<out E> : Iterable<E> {
    val size: Int

    fun isEmpty(): Boolean
    fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean

    operator fun contains(elements: @UnsafeVariance E): Boolean

    override fun iterator(): Iterable.Iterator<E>
}

interface MutableCollection<E> : Collection<E>, MutableIterable<E> {
    override fun iterator(): MutableIterable.MutableIterator<E>

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
    override fun contains(elements: @UnsafeVariance E): Boolean
    override fun iterator(): Iterable.Iterator<E>

    // Bulk Operation
    override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean

    // Positional Access Operations
    operator fun get(index: Int): E

    // Search Operations
    fun indexOf(element: @UnsafeVariance E): Int
    fun lastIndexOf(element: @UnsafeVariance E): Int

    // List Iterators
    fun listIterator(): ListIterator<E>
    fun listIterator(index: Int): ListIterator<E>

    // View
    fun subList(fromIndex: Int, toIndex: Int): List<E>
}

interface MutableList<E> : List<E>, MutableCollection<E> {
    override fun add(element: E): Boolean
    override fun remove(element: E): Boolean

    // Bulk Operations
    override fun addAll(elements: Collection<E>): Boolean
    override fun removeAll(elements: Collection<E>): Boolean
    override fun retainAll(elements: Collection<E>): Boolean
    override fun clear(): Unit

    // Positional Access Operations
    operator fun set(index: Int, element: E): E
    fun add(index: Int, element: E): Unit
    fun removeAt(index: Int): E

    // List Iterator
    override fun listIterator(): MutableListIterator<E>
    override fun listIterator(index: Int): MutableListIterator<E>

    // View
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E>
}

interface Set<out E> : Collection<E> {
    override val size: Int

    override fun isEmpty(): Boolean
    override fun contains(elements: @UnsafeVariance E): Boolean
    override fun iterator(): Iterable.Iterator<E>

    // Bulk Operations
    override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean
}

interface MutableSet<E> : Set<E>, MutableCollection<E> {
    override fun iterator(): MutableIterable.MutableIterator<E>

    override fun add(element: E): Boolean
    override fun remove(element: E): Boolean

    // Bulk Operations
    override fun addAll(elements: Collection<E>): Boolean
    override fun removeAll(elements: Collection<E>): Boolean

    override fun retainAll(elements: Collection<E>): Boolean

    override fun clear()
}

interface Map<K, out V> {
    val size: Int

    fun isEmpty(): Boolean
    fun containsKey(key: K): Boolean
    fun containsValue(value: @UnsafeVariance V): Boolean
    operator fun get(key: K): V? // nullable

    @SinceKotlin("1.1")
    fun getOrDefault(key: K, defaultValue: @UnsafeVariance V): V {
        // ????????? ????????? ???????????? ?????????????????????.
        throw NotImplementedError()
    }

    // Views
    /**
     * Returns a read-only Set of all keys in this map.
     * key can not be duplicated
     */
    val keys: Set<K>

    /**
     * Returns a read-only Collection of all values in this map.
     * values can be duplicated
     */
    val values: Collection<V>
    val entries: Set<Entry<K, V>>

    interface Entry<out K, out V> {
        val key: K
        val value: V
    }
}

interface MutableMap<K, V> : Map<K, V> {
    // Modification Operations
    fun put(key: K, value: V): V? // nullable
    fun remove(key: K): V? // nullable

    @SinceKotlin("1.1")
    fun remove(key: K, value: V): Boolean {
        // ?????? ????????? ???????????? ???????????? ??????????????? true ??? ????????????. -> idempotent
        return true
    }

    // Bulk Operations
    /**
     * from ???????????? Entry ?????? ?????? map ??? ???????????????.
     */
    fun putAll(from: Map<K, V>): Unit

    /**
     * ?????? element ??? ?????????.
     */
    fun clear(): Unit

    override val keys: MutableSet<K>
    override val values: MutableCollection<V>
    override val entries: Set<MutableEntry<K, V>>

    interface MutableEntry<K, V> : Map.Entry<K, V> {
        /**
         * ????????? ????????? ?????? Entry ??? value ??? ????????????.
         * ??????????????? ?????? ?????? ????????????.
         */
        fun setValue(newValue: V): V
    }
}