package com.mentalhealth.eifie.data.models.request

enum class OpenAIRole(val key: String) {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system")
}