package com.wk.mediate

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wk.mediate.present.config.Pref
import com.wk.mediate.present.views.MainActivity

class FirebaseMessagingService  : FirebaseMessagingService() {
    //메세지를 수신할 때 호출된다.(메세지를 받을때) remoteMessage는 수신한 메세지이다.
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("CHECK_VALUE", remoteMessage.data.toString())
        }

        val notificationInfo: MutableMap<String, String> = mutableMapOf()

        remoteMessage.data.forEach {
            notificationInfo[it.key] = it.value
        }

        sendNotification(notificationInfo)
    }
//
//    private fun scheduleJob() {
//        Logger.d("scheduleJob() 실행")
//
//        // [START dispatch_job]
//        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
//        WorkManager.getInstance(this).beginWith(work).enqueue()
//        // [END dispatch_job]
//    }

    //    private fun handleNow() {
//        Logger.d("handleNow() 실행")
//    }
//
    override fun onNewToken(token: String) {
        Pref.fcmToken = token
    }

    //FCM 테스트 코드
    private fun sendNotification(messageBody: Map<String, String>) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(messageBody["title"])
            .setContentText(messageBody["body"])
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        //안드로이드 오레오 알림채널이 필요하기 때문에 넣음.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

}


