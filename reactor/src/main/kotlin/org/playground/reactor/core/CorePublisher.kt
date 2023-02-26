package org.playground.reactor.core

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

interface CorePublisher<T>: Publisher<T> {
    override fun subscribe(s: Subscriber<in T>)
}