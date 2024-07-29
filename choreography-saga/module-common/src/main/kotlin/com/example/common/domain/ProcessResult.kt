package com.example.common.domain

sealed class ProcessResult<A> {
    abstract val resultType: ResultType

    abstract val recoverHandler: List<() -> Unit>

    companion object {
        fun <A> success(data: A): ProcessResult<A> = Success(data)

        fun <A> failure(
            errorResult: ErrorResult,
            isFirstFailed: Boolean,
            recoverHandler: List<() -> Unit>,
        ): ProcessResult<A> = Failure(errorResult, isFirstFailed, recoverHandler)

        operator fun <A> invoke(func: () -> A): ProcessResult<A> = executeExceptionSafeContext(
            run = { Success(func()) },
            failed = { Failure(it, true) }
        )
    }

    internal class Success<A>(
        val data: A,
        override val recoverHandler: List<() -> Unit> = emptyList(),
    ) : ProcessResult<A>() {
        override val resultType = ResultType.SUCCESS
    }

    internal class Failure<A>(
        val errorResult: ErrorResult,
        val isFirstFailed: Boolean,
        override val recoverHandler: List<() -> Unit> = emptyList(),
    ) : ProcessResult<A>() {
        override val resultType = ResultType.FAILURE
    }

    fun <C> flatMap(
        f: (A) -> ProcessResult<C>,
    ): ProcessResult<C> = when (this) {
        is Success -> when (val result = f(data)) {
            is Success -> Success(result.data, this.recoverHandler + result.recoverHandler)
            is Failure -> Failure(result.errorResult, false, this.recoverHandler + result.recoverHandler)
        }

        is Failure -> createFailureForProcess(errorResult)
    }

    fun <C> map(
        f: (A) -> C,
    ): ProcessResult<C> = when (this) {
        is Success -> executeExceptionSafeContext { f(data) }
        is Failure -> createFailureForProcess(errorResult)
    }

    fun doOnNext(
        f: (A) -> Unit,
    ): ProcessResult<A> = when (this) {
        is Success -> executeExceptionSafeContext {
            f(data)
            data
        }

        is Failure -> createFailureForProcess(errorResult)
    }

    private fun <C> createFailureForProcess(errorResult: ErrorResult): Failure<C> {
        return Failure(errorResult, false, this.recoverHandler)
    }

    fun doFailRollback(f: (A) -> Unit): ProcessResult<A> = when (this) {
        is Success -> Success(data, this.recoverHandler.plus { f(this.data) })
        is Failure -> when (this.isFirstFailed) {
            true -> Failure(this.errorResult, true, this.recoverHandler)
            false -> this
        }
    }

    fun onFailure(f: (ErrorResult) -> Unit): ProcessResult<A> = when (this) {
        is Success -> this
        is Failure -> if (this.isFirstFailed) {
            f(this.errorResult) // run error call
            Failure(this.errorResult, true, this.recoverHandler)
        } else {
            this
        }
    }

    fun getResultOrThrow(): A {
        return when (this) {
            is Success -> this.data
            is Failure -> {
                this.recoverHandler.reversed().forEach { it() }
                throw this.errorResult.ex
            }
        }
    }

    fun runOrThrow() {
        if (this is Failure) {
            this.recoverHandler.reversed().forEach { it() }
            throw this.errorResult.ex
        }
    }

    private inline fun <C> executeExceptionSafeContext(
        run: () -> C,
    ): ProcessResult<C> {
        return try {
            Success(run(), this.recoverHandler)
        } catch (e: Exception) {
            Failure(ErrorResult(e), true, this.recoverHandler)
        }
    }
}

inline fun <T> executeExceptionSafeContext(
    run: () -> T,
    failed: (e: ErrorResult) -> T,
): T = try {
    run()
} catch (e: Exception) {
    failed(ErrorResult(e))
}
