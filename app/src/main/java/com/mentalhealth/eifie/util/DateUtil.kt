package com.mentalhealth.eifie.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@SuppressLint("SimpleDateFormat")
internal fun Long.toDateFormat(dateFormat: String = "yyyy-MM-dd"): String {
    val formatter = SimpleDateFormat(dateFormat)
    formatter.timeZone = TimeZone.getTimeZone("UTC-5")
    return formatter.format(Date(this))
}

internal fun Date.compareWith(dateToCompare: Date, locale: Locale = Locale("es", "PE")): Boolean {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", locale)
    return dateFormatter.format(this) == dateFormatter.format(dateToCompare)
}