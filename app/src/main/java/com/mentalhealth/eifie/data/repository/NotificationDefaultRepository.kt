package com.mentalhealth.eifie.data.repository

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import com.mentalhealth.eifie.data.local.database.EDatabase
import com.mentalhealth.eifie.data.local.database.entities.LocalNotification
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.service.NotificationReceiver
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Notification
import com.mentalhealth.eifie.domain.entities.NotificationType
import com.mentalhealth.eifie.domain.repository.NotificationRepository
import com.mentalhealth.eifie.util.getFormattedDate
import com.mentalhealth.eifie.util.toDateTimeFormat
import com.mentalhealth.eifie.util.toTimeFormat
import com.mentalhealth.eifie.util.userPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
import kotlin.Exception

class NotificationDefaultRepository @Inject constructor(
    private val context: Context,
    private val preferences: EPreferences,
    private val database: EDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): NotificationRepository {

    private val notificationDao = database.notificationDao()

    override suspend fun save(notifications: List<Notification>): EResult<Int, Exception> = withContext(dispatcher){
        try {
            val user = preferences.readPreference(userPreferences) ?: 0
            notifications.forEach {
                when(it.type) {
                    NotificationType.APPOINTMENT.ordinal -> saveAppointmentNotification(it.date, it.key, user)
                    else -> Unit
                }
            }
            EResult.Success(notifications.size)
        }catch (e: Exception){
            EResult.Error(e)
        }
    }

    private suspend fun saveAppointmentNotification(date: Date?, key: String, user: Long) {
        if(notificationDao.getByKeyAndUser(listOf("$key-p", "$key-r"), user).isEmpty() && date != null) {
            try {
                val timeZone = TimeZone.getTimeZone("America/Lima")
                val locale = Locale("es", "PE")
                val calendarReminder = Calendar.getInstance(timeZone, locale)
                val calendarDayReminder = Calendar.getInstance(timeZone, locale)

                calendarDayReminder.time = date
                calendarReminder.time = date
                calendarDayReminder.time = calendarDayReminder.apply { add(Calendar.HOUR, -2) }.time
                calendarReminder.time = calendarReminder.apply { add(Calendar.DATE, -1) }.time

                val notifications = mutableListOf(
                    LocalNotification(key = "$key-r", user = user, date = calendarDayReminder.time.toDateTimeFormat())
                )
                if(calendarReminder.time.after(Date())) {
                    notifications.add(LocalNotification(key = "$key-p", user = user, date = calendarReminder.time.toDateTimeFormat()))
                }

                notificationDao.insertAll(notifications).let {
                    saveNotificationIntent(calendarDayReminder, it.first(), "No te olvides de tu cita",
                        "Hoy tienes una cita a las ${date.toTimeFormat()} horas")
                    if(it.size > 1) saveNotificationIntent(calendarReminder, it.last(),
                        "Prepara todo para tu cita", "El día de mañana tienes una cita a las ${date.toTimeFormat()} horas")

                }
            } catch (e: Exception) {
                throw e
            }
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun saveNotificationIntent(calendar: Calendar, id: Long, title: String, message: String) {
        try{
            val notificationIntent = Intent(context, NotificationReceiver::class.java)
            notificationIntent.putExtra("notificationId", id)
            notificationIntent.putExtra("message", message)
            notificationIntent.putExtra("title", title)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } catch (e: Exception) {
            throw e
        }
    }

}