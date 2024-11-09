package com.mentalhealth.eifie.data.network.apiopenai

import com.mentalhealth.eifie.data.models.request.Question
import com.mentalhealth.eifie.data.models.response.Answer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val APIKEY = "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"
fun interface OpenAIService {
    @POST("completions")
    @Headers("Authorization: Bearer $APIKEY", "Content-Type: application/json")
    suspend fun sendMessage(@Body question: Question): Response<Answer>
}