package org.example.feignclientexample.commons.exceptions

import org.example.feignclientexample.commons.enums.ErrorType

open class AppException(
    val errorCode: String,
    val errorMessage: String,
    val statusCode: Int,
) : RuntimeException(errorMessage) {

    constructor(errorType: ErrorType) : this(errorCode = errorType.name, errorMessage = errorType.message, statusCode = errorType.code)
}