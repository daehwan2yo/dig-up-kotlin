package org.playground.coroutine

interface ContinuationInterceptor : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<ContinuationInterceptor>

    /**
     *
     */
    fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T>

    fun releaseInterceptedContinuation(continuation: Continuation<*>) {
        // do nothing by default
    }

    override operator fun <E : CoroutineContext.Element> get(key: CoroutineContext.Key<E>): E? {
        if(key is AbstractCoroutineContextKey<*, *>) {
            return if (key.isSubKey(this.key)) key.tryCast(this) as? E else null
        }
        return if (ContinuationInterceptor === key) this as E else null
    }

    override fun minusKey(key: CoroutineContext.Key<*>): CoroutineContext {
        if(key is AbstractCoroutineContextKey<*, *>) {
            return if (key.isSubKey(this.key) && key.tryCast(this) != null) EmptyCoroutineContext else this
        }
        return if (ContinuationInterceptor === key) EmptyCoroutineContext else this
    }
}