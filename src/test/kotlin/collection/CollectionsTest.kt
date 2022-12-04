package collection

import org.junit.jupiter.api.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CollectionsTest {
    @Test
    fun `빈 immutable 한 List 를 반환한다`() {
        assertTrue { EmptyList is List<*> }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class EmptyListTest {
        lateinit var emptyList: List<*>

        // given for each
        @BeforeAll
        fun init() {
            emptyList = emptyList()
        }

        @Test
        fun `Empty 한 지 검증한다`() {
            // when
            assertEmptyList(emptyList)
        }

        @Nested
        inner class Exceptions {
            @Test
            fun `listIterator() 에서 0 이 아닌 경우 예외 발생`() {
                assertThrows<IndexOutOfBoundsException> { emptyList().listIterator(1) }
            }
        }
    }

    @Nested
    inner class listOfTest {
        lateinit var 검증_대상: List<*>

        @Test
        fun `입력한 element 가 없는 경우는 emptyList 를 응답한다`() {
            // when
            val listOf = listOf<String>()

            // then
            assertEmptyList(listOf)
        }

        @Test
        fun `element 가 한개 인 경우 size 가 1인 list 를 응답한다`() {
            // when
            val listOf = listOf("hello world")

            // then
            assertElementsOfList(listOf, "hello world")
        }

        @Test
        fun `element 가 여러개 인 경우 size 가 여러개인 list 를 응답한다`() {
            // when
            val listOf = listOf("hello", "world", "daehwan")

            // then
            assertElementsOfList(listOf, "hello", "world", "daehwan")
        }

        @Test
        fun `element 가 여러개인 경우 순서가 보장이 된다`() {
            // when
            val listOf = listOf("hello", "world", "daehwan")

            // then
            assertEquals(listOf[0], "hello")
            assertEquals(listOf[1], "world")
            assertEquals(listOf[2], "daehwan")
        }
    }

    private fun assertEmptyList(target: List<*>) {
        assertAll({
            assertEquals(target.toString(), "[]")
            assertTrue(target.isEmpty())
            assertFalse(target.contains(""))
            assertEquals(target.indexOf(3), -1)
            assertEquals(target.lastIndexOf("a"), -1)
        })
    }

    private fun <T> assertElementsOfList(target: List<*>, vararg t: T) {
        assertAll({
            t.forEach { assertTrue(target.contains(it)) }
            assertTrue(target.isNotEmpty())
            assertEquals(target.lastIndexOf("a"), -1)
            assertEquals(target.size, t.size)
        })
    }
}