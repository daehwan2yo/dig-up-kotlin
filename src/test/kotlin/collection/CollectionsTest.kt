package collection

import org.junit.jupiter.api.*
import java.lang.IndexOutOfBoundsException
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ListTest {
    @Test
    fun `빈 immutable 한 List 를 반환한다`() {
        assertTrue { EmptyList is List<*> }
    }

    @Nested
    internal class EmptyListTest {
        lateinit var 검증_대상: List<*>

        // given for each
        @BeforeEach
        fun init() {
            검증_대상 = emptyList()
        }

        @Test
        fun `Empty 한 지 검증한다`() {
            // when
            assertAll({
                assertEquals(검증_대상.toString(), "[]")
                assertTrue(검증_대상.isEmpty())
                assertFalse(검증_대상.contains(""))
                assertEquals(검증_대상.indexOf(3), -1)
                assertEquals(검증_대상.lastIndexOf("a"), -1)
            })
        }

        @Nested
        internal class Exceptions {
            @Test
            fun `listIterator() 에서 0 이 아닌 경우 예외 발생`() {
                assertThrows<IndexOutOfBoundsException> { emptyList().listIterator(1) }
            }
        }
    }


}