package com.example.serviceexample

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MyForegroundService : Service() {
    val CHANNEL_ID: String = "MyForegroundService"
    lateinit var notfcnName: String
    private val TAG: String = "Yogesh" + MyForegroundService::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return throw UnsupportedOperationException("Cannot create Binder")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notfcnName = intent?.getStringExtra("notfication_name")!!
        if (notfcnName == null) {
            notfcnName = "No Name"
        }
        createNotificationChannel()
        val pendingIntent: PendingIntent = Intent(this@MyForegroundService, MainActivity::class.java)
                .let { notificationIntent ->
                    PendingIntent.getActivity(
                            this@MyForegroundService, 1,
                            notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT,
                    )
                }

        val notification: Notification = NotificationCompat.Builder(this@MyForegroundService, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(notfcnName)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification)
        Log.d(TAG, "onStartCommand: ForeGroundSErvice Started")
        return START_NOT_STICKY

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel: NotificationChannel = NotificationChannel(CHANNEL_ID, notfcnName, NotificationManager.IMPORTANCE_HIGH)
            val manager: NotificationManager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(notificationChannel)
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ForeGround Service")
        super.onDestroy()

    }

}