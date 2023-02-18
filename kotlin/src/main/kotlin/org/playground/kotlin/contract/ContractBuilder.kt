package org.playground.kotlin.contract

import org.playground.kotlin.internal.InlineOnly

interface ContractBuilder {
    /**
     * 아무런 예외도 발생하지 않는 응답을 하는 함수의 상황을 정의한다.
     */
    fun returns(): Returns

    /**
     * 특정한 응답 값을 반환하는 함수의 상황을 정의한다.
     * value 는 true, false, null 가능하다.
     */
    fun returns(value: Any?) : Returns

    /**
     * non null 인 값을 반환하는 함수의 상황을 정의한다.
     */
    fun returnsNotNull() : ReturnsNotNull

    /**
     * lambda 의 수행을 in place 로 호출되어지도록 정의
     * - owner function 에 의해서만 호출되어진다..
     *   - owner function 의 호출이 완료되어진 후에는 호출되어질 수 없다.
     * - kind enum 을 통해 호출 횟수를 정의할 수 있다.
     */
    fun <R> callsInPlace(lambda: Function<R>, kind: InvocationKind = InvocationKind.UNKNOWN): CallsInPlace
}

/**
 * 함수형 파라미터가 얼마나 자기를 호출하는지 횟수를 구체화한다.
 */
enum class InvocationKind {
    AT_MOST_ONCE,   // 함수 파라미터가 1회 혹은 전혀 호출되어지지 않는다.
    AT_LEAST_ONCE,  // 함수 파라미터가 최소 1회는 호출되어진다.
    EXACTLY_ONCE,   // 함수 파라미터가 무조건 1회만 호출되어진다.
    UNKNOWN         // 함수 파라미터가 몇번 호출되어질지 모른다.
}

/**
 * contract 를 함수로써 정의한다.
 */
@InlineOnly
public inline fun contract(builder: ContractBuilder.() -> Unit) {}
