package com.mentalhealth.eifie.data.api

sealed class DataResult<out T, out E> {
    data class Success<out T>(val data: T) : DataResult<T, Nothing>()
    data class Error<out E>(val error: Exception) : DataResult<Nothing, E>()
    object Loading : DataResult<Nothing, Nothing>()
}

data class ApiException(val errorCode: Int? = null): Exception()