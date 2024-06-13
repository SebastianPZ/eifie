package com.mentalhealth.eifie.domain.entities

import java.util.Date

data class Notification(
    val key: String = "",
    val title: String = "",
    val content: String = "",
    val type: Int = NotificationType.APPOINTMENT.ordinal,
    val date: Date? = null,
    val action: String? = null
)