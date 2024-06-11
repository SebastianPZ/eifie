package com.mentalhealth.eifie.data.network

import com.google.gson.Gson
import com.mentalhealth.eifie.data.models.response.BaseResponse
import com.mentalhealth.eifie.domain.entities.EResult
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

suspend inline fun <I, O> performApiCall(
    crossinline call: suspend() -> Response<I>,
    crossinline deserialize: (I?) -> O?,
): EResult<O, Exception> {
    val response = call()
    return if (response.isSuccessful && response.body() != null) {
        when {
            (response.body() as BaseResponse<*>).errorCode?.let { it > 0 } == true -> {
                EResult.Error(ApiException(
                    errorCode = (response.body() as BaseResponse<*>).errorCode,
                    message = (response.body() as BaseResponse<*>).errorMessage)
                )
            }
            else -> {
                when(val result = deserialize(response.body())) {
                    null -> EResult.Error(ApiException(
                        errorCode = (response.body() as BaseResponse<*>).errorCode,
                        message = (response.body() as BaseResponse<*>).errorMessage)
                    )
                    else -> EResult.Success(result)
                }
            }
        }
    } else EResult.Error(HttpException(response))
}

suspend inline fun <I, O> performApiCall(
    crossinline call: suspend() -> Response<I>,
    crossinline deserialize: (I?) -> O?,
    crossinline adminHeaders: suspend(Headers) -> Unit
): EResult<O, Exception> {
    val response = call()
    return if (response.isSuccessful && response.body() != null) {
        adminHeaders(response.headers())
        when {
            (response.body() as BaseResponse<*>).errorCode?.let { it > 0 } == true -> {
                EResult.Error(ApiException(
                    errorCode = (response.body() as BaseResponse<*>).errorCode,
                    message = (response.body() as BaseResponse<*>).errorMessage)
                )
            }
            else -> {
                when(val result = deserialize(response.body())) {
                    null -> EResult.Error(ApiException(
                        errorCode = (response.body() as BaseResponse<*>).errorCode,
                        message = (response.body() as BaseResponse<*>).errorMessage)
                    )
                    else -> EResult.Success(result)
                }
            }
        }
    } else EResult.Error(EHttpException(response))
}


class EHttpException(response: Response<*>): HttpException(response) {

    override val message by lazy { handleHttpErrorException(this) }

    private fun handleHttpErrorException(exception: HttpException): String {
        return when(exception.code()) {
            404 -> "Servicio no encontrado."
            503 -> "Servicio no disponible."
            else -> "Error de servicio."
        }
    }
}

internal fun prepareMultipartRequest(
    request: Any? = null,
    file: File? = null
): Pair<MultipartBody.Part, MultipartBody.Part?> {

    val json: RequestBody = request.let {
        Gson().toJson(it).toRequestBody("application/json".toMediaType())
    }

    val partJson = MultipartBody.Part.createFormData("request", "request", json)

    val picture: RequestBody? = file?.asRequestBody("img/jpg".toMediaType())

    val partPicture = picture?.let { MultipartBody.Part.createFormData("profilePic", "profilePic", it) }

    return partJson to partPicture

}