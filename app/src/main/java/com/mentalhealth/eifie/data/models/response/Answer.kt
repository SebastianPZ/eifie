package com.mentalhealth.eifie.data.models.response

import com.google.gson.annotations.SerializedName
import com.mentalhealth.eifie.data.models.request.Message

data class Answer(
    val id: String,
    @SerializedName("object") val obj: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage
)

data class Choice(
    val index: Int,
    val message: Message,
    @SerializedName("finish_reason") val finishReason: String
)

data class Usage(
    @SerializedName("prompt_tokens") val promptTokens: Int,
    @SerializedName("completion_tokens") val completionTokens: Int,
    @SerializedName("total_tokens") val totalTokens: Int
)
