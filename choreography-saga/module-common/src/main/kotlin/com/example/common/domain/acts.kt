package com.example.common.domain

sealed class ProcessResult<A> {
    abstract val resultType: ResultType

    internal class Success<A>(val data: A) : ProcessResult<A>() {
        override val resultType = ResultType.SUCCESS
    }

    internal class Failure<A>(
        val errorResponse: ErrorResponse
    ) : ProcessResult<A>() {
        override val resultType = ResultType.FAILURE
    }

    companion object {
        fun <A> success(data: A): ProcessResult<A> = Success(data)
        fun <A> failure(errorResponse: ErrorResponse): ProcessResult<A> = Failure(errorResponse)
        operator fun <A> invoke(func: () -> A): ProcessResult<A> =
            executeExceptionSafeContext(
                run = { Success(func()) },
                failure = { Failure(it) }
            )
    }

    fun <C> map(
        f: (A) -> C
    ): ProcessResult<C> = when (this) {
        is Success -> Success(f(data))
        is Failure -> failure(errorResponse)
    }

    // Success인 경우만 결과 데이터를 ActResult<C> 타입으로 변환
    fun <C> flatMap(
        f: (A) -> ProcessResult<C>,
    ): ProcessResult<C> = when (this) {
        is Success -> f(data)
        is Failure -> failure(errorResponse)
    }

    fun onSuccess(f: (A) -> Unit): ProcessResult<A> = when (this) {
        is Success -> {
            f(data)
            success(data)
        }
        is Failure -> failure(errorResponse)
    }

    fun onFailure(f: (ErrorResponse) -> Unit): ProcessResult<A> = when (this) {
        is Success -> success(data)
        is Failure -> {
            f(errorResponse)
            this
        }
    }
}

enum class ResultType {
    SUCCESS, FAILURE
}


inline fun <T> executeExceptionSafeContext(
    run: () -> T,
    failure: (e: ErrorResponse) -> T,
) = try {
    run()
} catch (e: Exception) {
    failure(ErrorResponse(e))
}

data class ErrorResponse(val ex: Exception) {
    fun throwError() {
        throw ex
    }
}
