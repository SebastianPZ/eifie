package com.mentalhealth.eifie.domain.entities

sealed class EResult<out T, out E> {
    data class Success<out T>(val data: T) : EResult<T, Nothing>()
    data class Error<out E>(val error: Exception) : EResult<Nothing, E>()
}