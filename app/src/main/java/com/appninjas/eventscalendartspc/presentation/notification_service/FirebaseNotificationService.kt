package com.appninjas.eventscalendartspc.presentation.notification_service

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.appninjas.eventscalendartspc.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class FirebaseNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        sendNotification(message)
    }

    private fun sendNotification(message: RemoteMessage) {
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, "")
            .setSmallIcon(R.drawable.icon)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["body"])
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

}