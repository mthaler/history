package com.mthaler.history.util

import com.mthaler.history.utils.elementsAtStart
import com.mthaler.history.utils.removeAtStart
import com.mthaler.history.utils.truncate
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ArrayListTest: StringSpec({

    "truncate" {
        val l0 = ArrayList<String>()
        l0.truncate(5)
        l0 shouldBe ArrayList<String>()
        val l1 = arrayListOf("a", "b", "c", "d", "e")
        l1.truncate(3)
        l1 shouldBe arrayListOf("a", "b", "c")
        val l2 = arrayListOf("a", "b", "c", "d", "e")
        l2.truncate(5)
        l2 shouldBe arrayListOf("a", "b", "c", "d", "e")
        val l3 = arrayListOf("a", "b", "c", "d", "e")
        l3.truncate(7)
        l3 shouldBe arrayListOf("a", "b", "c", "d", "e")
    }

    "removeAtStart" {
        val l0 = ArrayList<String>()
        l0.removeAtStart(5)
        l0 shouldBe ArrayList<String>()
        val l1 = arrayListOf("a", "b", "c", "d", "e")
        l1.removeAtStart(2)
        l1 shouldBe arrayListOf("c", "d", "e")
        val l2 = arrayListOf("a", "b", "c", "d", "e")
        l2.removeAtStart(5)
        l2 shouldBe ArrayList<String>()
        val l3 = arrayListOf("a", "b", "c", "d", "e")
        l3.removeAtStart(3)
        l3 shouldBe arrayListOf("d", "e")
        val l4 = arrayListOf("a", "b", "c", "d", "e")
        l4.removeAtStart(4)
        l4 shouldBe arrayListOf("e")
        val l5 = arrayListOf("a", "b", "c", "d", "e")
        l5.removeAtStart(6)
        l5 shouldBe ArrayList<String>()
    }

    "elementsAtStart" {
        ArrayList<String>().elementsAtStart(5) shouldBe emptyList()
        arrayListOf("a", "b", "c", "d", "e").elementsAtStart(2) shouldBe arrayListOf("a", "b")
        arrayListOf("a", "b", "c", "d", "e").elementsAtStart(5) shouldBe arrayListOf("a", "b", "c", "d", "e")
        arrayListOf("a", "b", "c", "d", "e").elementsAtStart(3) shouldBe arrayListOf("a", "b", "c")
        arrayListOf("a", "b", "c", "d", "e").elementsAtStart(4) shouldBe arrayListOf("a", "b", "c", "d")
        arrayListOf("a", "b", "c", "d", "e").elementsAtStart(6) shouldBe arrayListOf("a", "b", "c", "d", "e")
    }
})