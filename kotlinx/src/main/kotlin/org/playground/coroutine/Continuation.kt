package org.playground.coroutine

interface Continuation<T> {
    // Continuation 이 특정 Thread 혹은 Thread Pool 에서 동작하도록
    val context: CoroutineContext

    // 특정 함수가 suspend 되어야 할 때, 현재 함수에서 특정 함수의 결과 값을 T 로 받게 해주는 함수
    fun resumeWith(result: Result<T>)
}