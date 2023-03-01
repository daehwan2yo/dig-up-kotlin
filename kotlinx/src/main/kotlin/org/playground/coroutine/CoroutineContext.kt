package org.playground.coroutine


interface CoroutineContext {

    // 입력한 key 에 해당하는 CoroutineContext element 를 반환하고, 없으면 Null 을 반환한다.
    operator fun <E : Element> get(key: Key<E>): E?

    // 해당하는 CoroutineContext 와 다른 context 를 모두 포함하는 context 를 만든다.
    // 기존 coroutineContext 와 같은 key 를 재사용한다. (입력된 context 의 key 는 버려진다.)
    operator fun plus(context: CoroutineContext): CoroutineContext =

        // 붙힐 context 가 EmptyCoroutineContext 라면 붙히지 않는다.
        if (context == EmptyCoroutineContext) this
        else context.fold(this) { acc, element ->

            val removed = acc.minusKey(element.key)
            if (removed == EmptyCoroutineContext) element
            else {
                val interceptor = removed[ContinuationInterceptor]
                if (interceptor == null) CombinedContext(removed, element)
                else {
                    val left = removed.minusKey(ContinuationInterceptor)
                    if (left == EmptyCoroutineContext) CombinedContext(element, interceptor)
                    else CombinedContext(CombinedContext(left, element), interceptor)
                }
            }
        }

    // 입력받은 initial 부터 시작해 좌에서 우로 (linked) operation 을 적용시킨다.
    fun <R> fold(initial: R, operation: (R, Element) -> R): R

    // key 에 해당하는 context 를 제거한 coroutineContext 를 반환한다.
    fun minusKey(key: Key<*>): CoroutineContext

    /**
     * CoroutineContext.E element 의 key 의 interface 이다.
     */
    interface Key<E : Element>

    /**
     * CoroutineContext 의 요소이다. (코루틴 구성 주체)
     */
    interface Element : CoroutineContext {

        // 해당하는 CoroutineContext element 의 key 이다.
        val key: Key<*>

        override operator fun <E : Element> get(key: Key<E>): E? =
            @Suppress("UNCHECKED_CASE")
            if (this.key == key) this as E else null

        override fun <R> fold(initial: R, operation: (R, Element) -> R): R =
            operation(initial, this)

        override fun minusKey(key: Key<*>): CoroutineContext = if (this.key == key) EmptyCoroutineContext else this
    }
}