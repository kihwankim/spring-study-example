package com.example.springcloudcontract.service

import com.example.springcloudcontract.external.CalleeCaller
import com.example.springcloudcontract.presentation.ResponseBody
import org.springframework.stereotype.Service

@Service
class CallService(
    private val calleeCaller: CalleeCaller,
) {
    fun callData(num: Int): ResponseBody {
        val calleeResult = calleeCaller.callCalleeApplication(num)

        return ResponseBody(calleeResult.statusCode.value(), calleeResult.body!!)
    }
}