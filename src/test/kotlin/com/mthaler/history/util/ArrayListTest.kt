package com.mthaler.history.util

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
})