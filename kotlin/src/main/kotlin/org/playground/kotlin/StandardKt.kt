package org.playground.kotlin

import org.playground.kotlin.contract.contract
import org.playground.kotlin.internal.InlineOnly

/**
 * let
 * : 암시적으로 전달된 수신 객체에 변경을 주지 않으면서 값을 활용해 변환 혹은 다른 값을 제공하기위해 적용한다.
 * convention
 * - non null 인 인스턴스에 대해 동작하기 위함
 * - 다른 객체로의 변환
 */
@InlineOnly
inline fun <T, R> T.let(block: (T) -> R): R {
    contract {
        // callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        // block 이 1회만 호출되어지게끔
    }
    return block(this)
}

/**
 * also
 * : 암시적으로 전달된 수신 객체를 전혀 사용하지 않고 변경을 주지 않으면서, 부가적인 로직을 수행하기 위해 적용한다.
 * convention
 * - 객체의 후처리 검증
 * - 로깅, 출력 과 같은 3rd 작업
 */
@InlineOnly
inline fun <T> T.also(block: (T) -> Unit): T {
    contract {
        // callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        // block 이 1회만 호출되어지게끔
    }
    block(this)
    return this
}

/**
 * apply
 * : 암시적으로 전달된 수신 객체의 함수를 사용하지 않고, 반환하는 경우
 * convention
 * - 객체의 초기화
 * - 수신 객체의 프로퍼티들만 활용해 정의한 확장 함수를 적용한다.
 */
@InlineOnly
inline fun <T> T.apply(block: T.() -> Unit): T {
    contract {
        // callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        // block 이 1회만 호출되어지게끔
    }
    block()
    return this
}

/**
 * with
 * : 명시적으로 non null 인 수신 객체를 입력하고, 확장 함수를 정의해 수행한다.
 * convention
 */
@InlineOnly
inline fun <T, R> with(receiver: T, block: T.() -> R): R {
    contract {
        // callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        // block 이 1회만 호출되어지게끔
    }
    return receiver.block() // with : 대상으로 입력된 인스턴스의 응답값이 있는 후처리 메서드를 정의해 적용한다
}

@InlineOnly
inline fun <T, R> T.run(block: T.() -> R): R {
    contract {
        // callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        // block 이 1회만 호출되어지게끔
    }
    return block()
}