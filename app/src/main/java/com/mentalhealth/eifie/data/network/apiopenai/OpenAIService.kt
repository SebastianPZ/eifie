package com.mentalhealth.eifie.data.network.apiopenai

import com.mentalhealth.eifie.data.models.request.Question
import com.mentalhealth.eifie.data.models.response.Answer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val APIKEY = "sk-proj-wFlYNCCo99z7sVZXK6yRT3BlbkFJFdAdLN7ok08llX65hRsk"
fun interface OpenAIService {
    @POST("completions")
    @Headers("Authorization: Bearer $APIKEY", "Content-Type: application/json")
    suspend fun sendMessage(@Body question: Question): Response<Answer>
}