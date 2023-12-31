package com.commit.egusajo.config

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.commit.egusajo.R
import com.commit.egusajo.app.App
import com.commit.egusajo.presentation.ui.intro.IntroActivity
import com.commit.egusajo.presentation.ui.splash.SplashActivity
import com.commit.egusajo.util.Constants.TAG
import com.commit.egusajo.util.PushUtils
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()
        PushUtils.acquireWakeLock(this)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(TAG, "new Token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        PushUtils.acquireWakeLock(App.context())
        //수신한 메시지를 처리
        val title = message.data["title"]
        val body = message.data["body"]
        val code = message.data["code"]
        sendNotification(title, body, code)
    }

    private fun sendNotification(title: String?, body: String?, code: String?) {

        val uniId = (System.currentTimeMillis() / 7).toInt()
        val intent = Intent(this, SplashActivity::class.java).apply{
            putExtra("type", when(code){
                "complete" -> "complete"
                "friend" -> "friend"
                "accept" -> "accept"
                "fund" -> "fund"
                else -> "normal"
            })
        }
        val pIntent = PendingIntent.getActivity(this, uniId, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        val channelId = "egasajo"

        val notificationBuilder = NotificationCompat.Builder(this, channelId).apply {
            priority = NotificationCompat.PRIORITY_HIGH
            setContentTitle(title)
            setContentText(body)
            setContentIntent(pIntent)
            setAutoCancel(true)
            when(code){
                "complete" -> setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.icon_ballon))
                "friend" -> setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.icon_friend))
                "accept" -> setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.icon_laugh))
                "fund" -> setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.icon_gift))
            }

            setSmallIcon(R.drawable.icon_gift)
        }


        // Head up 알람 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setFullScreenIntent(pIntent, true)
        }

        getSystemService(NotificationManager::class.java).run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelId, "알림", NotificationManager.IMPORTANCE_HIGH)
                createNotificationChannel(channel)
            }

            notify(uniId, notificationBuilder.build())
        }
    }

    suspend fun getFirebaseToken(): String = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                continuation.resume(token)
            } else {
                continuation.resumeWithException(task.exception ?: RuntimeException("Error getting FCM token"))
            }
        }
    }

}