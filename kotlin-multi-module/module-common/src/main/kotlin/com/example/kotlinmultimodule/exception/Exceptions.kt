package com.example.kotlinmultimodule.exception

import com.example.kotlinmultimodule.error.ErrorCode


abstract class BaseException(
    override val message: String,
    open val errorCode: ErrorCode
) : RuntimeException(message)

open class NotFoundException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.NOT_FOUND
) : BaseException(message, errorCode)