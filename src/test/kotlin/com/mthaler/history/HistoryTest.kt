package com.mthaler.history

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class HistoryTest: StringSpec({

    "isEmpty" {
        History<String>().isEmpty() shouldBe true
        History("a").isEmpty() shouldBe false
        History("a", "b", "c").isEmpty() shouldBe false
    }

    "currentSize" {
        History<String>().currentSize shouldBe 0
        History("a").currentSize shouldBe 1
        History("a", "b", "c").currentSize shouldBe 3
    }

    "current" {
        History<String>().current shouldBe null
        History("a").current shouldBe "a"
        History("a", "b", "c").current shouldBe "c"
    }

    "past" {
        History<String>().past shouldBe emptyList()
        History("a").past shouldBe emptyList()
        val h = History("a", "b", "c")
        h.past shouldBe listOf("a", "b")
        h.undo()
        h.past shouldBe listOf("a")
        h.undo()
        h.past shouldBe emptyList()
        h.redo()
        h.past shouldBe listOf("a")
        h.redo()
        h.past shouldBe listOf("a", "b")
    }

    "iterator" {
        val it0 = History<String>().iterator()
        it0.hasNext() shouldBe false
        shouldThrow<NoSuchElementException> {
            it0.next()
        }

        val it1 = History("a").iterator()
        it1.hasNext() shouldBe true
        it1.next() shouldBe "a"
        it1.hasNext() shouldBe false
        val h = History("a", "b", "c")
        h.toList() shouldBe listOf("a", "b", "c")
        h.undo()
        h.toList() shouldBe listOf("a", "b")
        h.undo()
        h.toList() shouldBe listOf("a")
        h.undo()
        h.toList() shouldBe emptyList()
        h.redo()
        h.toList() shouldBe listOf("a")
        h.redo()
        h.toList() shouldBe listOf("a", "b")
        h.redo()
        h.toList() shouldBe listOf("a", "b", "c")
    }

    "canUndo" {
        History<String>().canUndo shouldBe false
        History("a").canUndo shouldBe true
        History("a", "b", "c").canUndo shouldBe true
    }

    "undo" {
        val h = History("a", "b", "c")
        h.current shouldBe "c"
        h.undo()
        h.current shouldBe "b"
        h.undo()
        h.current shouldBe "a"
        h.undo()
        h.current shouldBe null
    }
})