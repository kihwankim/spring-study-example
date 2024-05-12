package org.example.feignclientexample.commons.exceptions

import org.example.feignclientexample.commons.enums.ErrorType

class ExternalIgnoreException(
    errorType: ErrorType,
) : AppException(errorType)