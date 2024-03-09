package com.mentalhealth.eifie.data.api

import com.google.gson.Gson
import com.mentalhealth.eifie.data.api.models.response.BaseResponse
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
): DataResult<O, Exception> {
    val response = call()
    return if (response.isSuccessful && response.body() != null) {
        when(val result = deserialize(response.body())) {
            null -> DataResult.Error(ApiException((response.body() as BaseResponse<*>).errorCode))
            else -> {
                DataResult.Success(result)
            }
        }
    } else {
        DataResult.Error(HttpException(response))
    }
}

suspend inline fun <I, O> performApiCall(
    crossinline call: suspend() -> Response<I>,
    crossinline deserialize: (I?) -> O?,
    crossinline adminHeaders: suspend(Headers) -> Unit
): DataResult<O, Exception> {
    val response = call()
    return if (response.isSuccessful && response.body() != null) {
        adminHeaders(response.headers())
        when(val result = deserialize(response.body())) {
            null -> DataResult.Error(ApiException((response.body() as BaseResponse<*>).errorCode))
            else -> {
                DataResult.Success(result)
            }
        }
    } else {
        DataResult.Error(EHttpException(response))
    }
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