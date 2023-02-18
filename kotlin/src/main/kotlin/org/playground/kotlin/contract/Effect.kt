package org.playground.kotlin.contract

/**
 * 함수 호출의 Effect,
 * 정상적으로 반환되어지거나, 람다에서의 side-effect
 */
interface Effect

/**
 * 함수의 다른 effect 를 바탕으로 일부 조건이 true 가 되어지는 effect
 * : 조건에 따른 effect
 */
interface ConditionalEffect : Effect

/**
 * 함수 호출 이후 보여지는 effect
 * infix) something implies true
 */
interface SimpleEffect : Effect {
    infix fun implies(booleanExpression: Boolean): ConditionalEffect
}

/**
 * 함수가 일반적으로 응답값을 반환하는 상황을 정의한다.
 */
interface Returns : SimpleEffect

/**
 * 함수가 non null 인 값을 반환하는 상황을 정의한다.
 */
interface ReturnsNotNull: SimpleEffect

/**
 * 함수가 함수형 파라미터를 제자리에서 호출하는 effect
 * 함수형 파라미터는 함수를 통해 실행이 반환되지 않은 경우, 함수형 파라미터는 함수가 끝나고 나서 호출되어질 수 없다.
 */
interface CallsInPlace : Effect