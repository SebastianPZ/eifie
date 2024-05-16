package com.mentalhealth.eifie.data.network.models.request

data class Question(
    val model: String = "gpt-3.5-turbo",
    val messages:List<Message>
)
data class Message(
    val role: String,
    val content: String
)

val Message.fromUser:Boolean get() {
    return role == "user"
}
