package com.mentalhealth.eifie.data.network.apiopenai

import com.mentalhealth.eifie.data.models.request.Question
import com.mentalhealth.eifie.data.models.response.Answer
import com.mentalhealth.eifie.util.decode
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

fun interface OpenAIService {
    @POST("completions")
    @Headers("Content-Type: application/json")
    suspend fun sendMessage(@Body question: Question, @Header("Authorization") authToken: String): Response<Answer>
}