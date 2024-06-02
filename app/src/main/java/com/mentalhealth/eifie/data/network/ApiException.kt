package com.mentalhealth.eifie.data.network

class ApiException(
    val errorCode: Int? = null,
    override val message: String? = null
): Exception()