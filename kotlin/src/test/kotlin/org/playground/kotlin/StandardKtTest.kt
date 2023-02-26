package org.playground.kotlin

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

internal data class Person(val name: String, val age: Int)

class StandardKtTest {

    @Nested
    inner class also {
        @Test
        fun `대상 인스턴스의 validation 을 수행한다`() {
            assertDoesNotThrow {
                Person("daehwan", 26).also {
                    if (!it.isDaehwan()) throw IllegalArgumentException("not daehwan")
                }
            }

            assertThrows<IllegalArgumentException> {
                Person("inyoung", 26).also {
                    if (!it.isDaehwan()) throw IllegalArgumentException("not daehwan")
                }
            }
        }

        @Test
        fun `대상 인스턴스의 logging 을 수행한다`() {
            assertDoesNotThrow {
                Person("daehwan", 26).also {
                    println(it)
                }
            }
        }

        // validation
        private fun Person.isDaehwan() = this.name == "daehwan"
    }

    @Nested
    inner class apply {
        private inner class Person() {
            lateinit var name: String
            var age: Int = 0
        }

        @Test
        fun `대상 인스턴스의 필드를 초기화한다`() {
            // given
            val person = Person().apply {
                this.name = "daehwan"
                this.age = 26
            }

            // when, then
            assert(person.name == "daehwan")
            assert(person.age == 26)
        }

        @Nested
        inner class Collection {
            @Test
            fun `대상 MutableList 의 초기값을 세팅한다`() {
                val mutableList = ArrayList<String>().apply {
                    this += listOf("a", "b", "c")
                }

                assert(mutableList.containsAll(listOf("a", "b", "c")))
            }

            @Test
            fun `대상 MutableMap 의 초기값을 세팅한다`() {
                val mutableMap = HashMap<String, Any?>().apply {
                    this += listOf(
                        Pair("name", "daehwan"),
                        Pair("age", 26),
                        Pair("bio", null)
                    )
                }

                assert(mutableMap["name"] == "daehwan")
                assert(mutableMap["age"] == 26)
                assert(mutableMap["bio"] == null)
            }
        }
    }

    @Nested
    inner class with {
        
    }

    @Nested
    inner class let {

    }

    @Nested
    inner class run {

    }
}