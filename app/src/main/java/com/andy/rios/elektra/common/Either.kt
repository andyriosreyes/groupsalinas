package com.andy.rios.elektra.common

sealed class Either<out L, out R>{
    data class Success<out R>(val b: R) : Either<Nothing, R>()
    data class Error<out L>(val a: L) : Either<L, Nothing>()

    fun either(funError: (L) -> Any, funSuccess: (R) -> Any): Any = when (this) {
        is Error -> funError(a)
        is Success -> funSuccess(b)
    }
}