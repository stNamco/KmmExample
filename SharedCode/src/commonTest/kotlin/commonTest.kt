package com.stnamco.shared

import kotlin.test.Test
import kotlin.test.assertEquals

open class CommonTest {

    @Test
    fun testSum() {
        assertEquals(4, Calculator.sum(1,3))
    }
}