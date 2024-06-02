package com.mentalhealth.eifie.data.models.response

data class BaseResponse<T>(
    val httpCode: Int? = null,
    val errorCode: Int? = null,
    val errorMessage: String? = null,
    val data: T? = null
)
