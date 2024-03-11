package com.mentalhealth.eifie.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import kotlin.reflect.KFunction1

internal fun emptyString(): String {
    return ""
}

internal fun String.formatToken(): String {
    return "Bearer $this"
}

internal fun String?.ifBlank(value: () -> String): String {
    if(this.isNullOrBlank() || this.isEmpty()) return value.invoke()
    else return this
}

internal fun String.getInitials(divider: String? = emptyString()): String {
    if(this.isEmpty()) return emptyString()
    val words = this.split(" ")
    var initials = emptyString()
    words.forEachIndexed { index, word ->
        initials = if(index >= words.size - 1) initials.plus(word.first())
        else initials.plus("${word.first()}$divider")
    }
    return initials.uppercase()
}

val emailPattern = "^[A-Za-z](.*)([@])(.+)(\\.)(.+)".toRegex()

val defaultRules = listOf(
    ValidateText::noEmpty
)

val emailRules = listOf(
    ValidateText::noEmpty,
    ValidateText::whitValidEmail
)

val passwordRules = listOf(
    ValidateText::noEmpty,
    ValidateText::whitDigitRule,
    ValidateText::whitSpecialCharRule,
    ValidateText::lengthRule,
    ValidateText::withoutWhitespaceRule
)

@JvmInline
value class ValidateText(val text: String) : CharSequence by text {

    fun lengthRule() = require(text.length >= 4) { ERR_LENGTH }
    fun withoutWhitespaceRule() = require(text.none { it.isWhitespace() }) { ERR_WHITESPACE }
    fun whitDigitRule() = require(text.any { it.isDigit() }) { ERR_DIGIT }
    fun whitSpecialCharRule() = require(text.any { !it.isLetterOrDigit() }) { ERR_SPECIAL }
    fun noEmpty() = require(text.isNotBlank()) { ERR_EMPTY }
    fun whitValidEmail() = require(emailPattern.matches(text)) { ERR_EMAIL }

    infix fun checkWith(rules: List<KFunction1<ValidateText, Unit>>) = runCatching { rules.forEach { it(this) } }
}

internal fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}