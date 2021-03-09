package com.mthaler.history

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
})