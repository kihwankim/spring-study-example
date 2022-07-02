package com.example.kotlinmultimodule.error

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String
) {
    // common
    INVALID_REQUEST(status = 400, code = "C001", message = "잘못된 요청입니다"),
    UNAUTHORIZED(status = 401, code = "C002", message = "인증에 실패하였습니다 다시 로그인 해주세요"),
    FORBIDDEN(status = 403, code = "C003", message = "해당 권한이 존재하지 않습니다"),
    NOT_FOUND(status = 404, code = "C004", message = "해당하는 리소스는 존재하지 않습니다"),
    CONFLICT(status = 409, code = "C005", message = "중복된 리소스가 존재합니다"),
    UNKNOWN_ERROR(status = 500, code = "C006", message = "서버에서 예상하지 못한 에러가 발생했습니다"),
}