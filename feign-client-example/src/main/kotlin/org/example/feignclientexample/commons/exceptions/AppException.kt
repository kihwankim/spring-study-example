package org.example.feignclientexample.commons.exceptions

import org.example.feignclientexample.commons.enums.ErrorType

class AppException(
    val errorType: ErrorType,
) : RuntimeException(message = errorType.message)