package com.mentalhealth.eifie.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@SuppressLint("SimpleDateFormat")
internal fun Long.toDateFormat(dateFormat: String = "yyyy-MM-dd"): String {
    val formatter = SimpleDateFormat(dateFormat)
    formatter.timeZone = TimeZone.getTimeZone("UTC-5")
    return formatter.format(Date(this))
}
