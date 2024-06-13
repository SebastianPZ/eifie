package com.mentalhealth.eifie.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.mentalhealth.eifie.R

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationId = intent?.getLongExtra("notificationId", 0) ?: 0
        val message = intent?.getStringExtra("message")
        val title = intent?.getStringExtra("title")

        val notificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(
            NotificationChannel("runner_channel_id", "Running Notification", NotificationManager.IMPORTANCE_DEFAULT))

        val notificationBuilder = NotificationCompat.Builder(context, "runner_channel_id")
            .setSmallIcon(R.drawable.ic_lock)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true) // styling the notification

        notificationManager.notify(notificationId.toInt(), notificationBuilder.build())
    }
}