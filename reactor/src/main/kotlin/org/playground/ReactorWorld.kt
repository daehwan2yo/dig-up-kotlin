package org.playground

import reactor.core.publisher.Mono

fun main(arg: Array<String>) {
    val mono = Mono.just(1)

    mono.subscribe(
        {item -> println(item) },       // onNext
        {error -> println(error) },     // onError
        { println("done") }             // onCompleted
    )
}

class ReactorWorld {

}