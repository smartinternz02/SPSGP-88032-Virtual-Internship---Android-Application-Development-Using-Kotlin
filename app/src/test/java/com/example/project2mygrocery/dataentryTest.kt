package com.example.project2mygrocery

import org.junit.Assert.*
import org.junit.Test

class dataentryTest{

    @Test
    fun `valid item returns true`(){
        val res = dataentry.validate(
            "tomotoes",
            0,
            6.0
        )
        assert(res)
    }
}