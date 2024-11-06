package com.mentalhealth.eifie.data.network

import com.google.gson.Gson
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.mappers.impl.LoginMapper
import com.mentalhealth.eifie.data.models.request.LoginRequest
import com.mentalhealth.eifie.data.models.response.BaseResponse
import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.util.TOKEN_KEY
import com.mentalhealth.eifie.util.emptyString
import com.mentalhealth.eifie.util.tokenPreferences
import com.mentalhealth.eifie.util.userMailPreferences
import com.mentalhealth.eifie.util.userPwdPreferences
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class DataAccess @Inject constructor(
    val api: ApiService,
    val preferences: EPreferences
) {
    suspend fun <I, O> performApiCall(
        call: suspend() -> Response<I>,
        deserialize: (I?) -> O?,
        attempt: Int = 1
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
        } else {
            if((response.code() == 401 || response.code() == 403) && attempt < 2) {
                when(reAuthenticate()) {
                    is EResult.Error -> EResult.Error(EHttpException(response))
                    is EResult.Success -> performApiCall(call, deserialize)
                }
            } else {
                EResult.Error(EHttpException(response))
            }
        }
    }

    suspend fun <I, O> performApiCall(
        call: suspend() -> Response<I>,
        deserialize: (I?) -> O?,
        adminHeaders: suspend(Headers) -> Unit,
        attempt: Int = 1
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
        } else {
            if((response.code() == 401 || response.code() == 403) && attempt < 2) {
                when(reAuthenticate()) {
                    is EResult.Error -> EResult.Error(EHttpException(response))
                    is EResult.Success -> performApiCall(call, deserialize, adminHeaders)
                }
            } else {
                EResult.Error(EHttpException(response))
            }
        }
    }


    private suspend fun reAuthenticate(): EResult<Boolean, Exception> {
        return performApiCall(
            {
                api.loginUser(
                    LoginRequest(
                        email = preferences.readPreference(userMailPreferences) ?: "",
                        password = preferences.readPreference(
                            userPwdPreferences
                        ) ?: ""
                    )
                )
            },
            { response -> response?.data?.profile?.let { it.user != null } },
            { headers ->
                preferences.savePreference(
                    tokenPreferences,
                    headers[TOKEN_KEY] ?: emptyString()
                )
            }
        )
    }
}

suspend inline fun <I, O> performOpenAICall(
    crossinline call: suspend() -> Response<I>,
    crossinline deserialize: (I?) -> O?,
): EResult<O, Exception> {
    val response = call()
    return if (response.isSuccessful && response.body() != null) {
        when(val result = deserialize(response.body())) {
            null -> EResult.Error(ApiException(
                errorCode = (response.body() as BaseResponse<*>).errorCode,
                message = (response.body() as BaseResponse<*>).errorMessage)
            )
            else -> EResult.Success(result)
        }
    } else EResult.Error(HttpException(response))
}


class EHttpException(response: Response<*>): HttpException(response) {

    override val message by lazy { handleHttpErrorException(this) }

    private fun handleHttpErrorException(exception: HttpException): String {
        return when(exception.code()) {
            401 -> "No autorizado."
            403 -> "No permitido."
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