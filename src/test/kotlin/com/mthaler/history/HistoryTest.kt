package com.mthaler.history

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class HistoryTest: StringSpec({

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

    "future" {
        val h = History("a", "b", "c")
        h.future shouldBe emptyList()
        h.undo()
        h.future shouldBe listOf("c")
        h.undo()
        h.future shouldBe listOf("b", "c")
        h.undo()
        h.future shouldBe listOf("a", "b", "c")
        h.redo()
        h.future shouldBe listOf("b", "c")
        h.redo()
        h.future shouldBe listOf("c")
        h.redo()
        h.future shouldBe emptyList()
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

    "add" {
        val h = History("a", "b", "c")
        h.past shouldBe listOf("a", "b")
        h.current shouldBe "c"
        h.future shouldBe emptyList()
        h.add("d")
        h.past shouldBe listOf("a", "b", "c")
        h.current shouldBe "d"
        h.future shouldBe emptyList()
        h.undo()
        h.past shouldBe listOf("a", "b")
        h.current shouldBe "c"
        h.future shouldBe listOf("d")
        h.undo()
        h.past shouldBe listOf("a")
        h.current shouldBe "b"
        h.future shouldBe listOf("c", "d")
        h.add("e")
        h.past shouldBe listOf("a", "b")
        h.current shouldBe "e"
        h.future shouldBe emptyList()
    }

    "addMaximumSize" {
        val h0 = History<String>(maximumSize = 3)
        h0.add("a")
        h0.toList() shouldBe listOf("a")
        h0.add("b")
        h0.toList() shouldBe listOf("a", "b")
        h0.add("c")
        h0.toList() shouldBe listOf("a", "b", "c")
        h0.add("d")
        h0.toList() shouldBe listOf("b", "c", "d")
        h0.add("e")
        h0.toList() shouldBe listOf("c", "d", "e")
        val h1 = History<String>(maximumSize = 3, removeSize = 2)
        h1.add("a")
        h1.toList() shouldBe listOf("a")
        h1.add("b")
        h1.toList() shouldBe listOf("a", "b")
        h1.add("c")
        h1.toList() shouldBe listOf("a", "b", "c")
        h1.add("d")
        h1.toList() shouldBe listOf("a", "b", "c", "d")
        h1.add("e")
        h1.toList() shouldBe listOf("c", "d", "e")
    }

    "addRemovalListener" {
        var removed: List<String> = emptyList()
        val h0 = History<String>(maximumSize = 3)
        h0.setRemovalListener {
            removed = it
        }
        h0.add("a")
        h0.toList() shouldBe listOf("a")
        removed shouldBe emptyList()
        h0.add("b")
        h0.toList() shouldBe listOf("a", "b")
        removed shouldBe emptyList()
        h0.add("c")
        h0.toList() shouldBe listOf("a", "b", "c")
        removed shouldBe emptyList()
        h0.add("d")
        h0.toList() shouldBe listOf("b", "c", "d")
        removed shouldBe listOf("a")
        h0.add("e")
        h0.toList() shouldBe listOf("c", "d", "e")
        removed shouldBe listOf("b")
        removed = emptyList()
        val h1 = History<String>(maximumSize = 3, removeSize = 2)
        h1.setRemovalListener {
            removed = it
        }
        h1.add("a")
        h1.toList() shouldBe listOf("a")
        removed shouldBe emptyList()
        h1.add("b")
        h1.toList() shouldBe listOf("a", "b")
        removed shouldBe emptyList()
        h1.add("c")
        h1.toList() shouldBe listOf("a", "b", "c")
        removed shouldBe emptyList()
        h1.add("d")
        h1.toList() shouldBe listOf("a", "b", "c", "d")
        removed shouldBe emptyList()
        h1.add("e")
        h1.toList() shouldBe listOf("c", "d", "e")
        removed shouldBe listOf("a", "b")
    }
})