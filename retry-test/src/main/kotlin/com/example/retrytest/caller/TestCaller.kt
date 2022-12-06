package com.example.retrytest.caller

import com.example.retrytest.callee.TestCallee
import org.springframework.stereotype.Service

@Service
class TestCaller(
    private val testCallee: TestCallee
) {

    fun callAll(isErr: Boolean): Int = when (isErr) {
        false -> testCallee.errorTest()
        true -> 1
    }
}