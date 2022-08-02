package com.git_kt_project_01

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random
private const val CHANNEL_ID ="my_channel"
//thats all for our service class
class FireBaseService :FirebaseMessagingService() {
    //this message referce to the message we received from the firebase
    @Override
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
     //this methode will be called whenever this device receives a message
    //now we want to create a notification whenever we receive a message and show it

         // the notification
        val intent = Intent(this,MainActivity::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        val notificationID = Random.nextInt()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(notificationManager)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,FLAG_ONE_SHOT)
// we need to creata a channel too with the notification
// this data is a map
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSmallIcon(R.drawable.ic_baseline_camera_24)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        //now we have the notification variable
        notificationManager.notify(notificationID,notification)
    }

    private fun createNotificationChannel (notificationManager: NotificationManager){
         val channelName = "channelName"
        val channel = NotificationChannel(CHANNEL_ID , channelName
            , IMPORTANCE_HIGH).apply {
                description = " my channel description"
            enableLights(true)
            lightColor=Color.GREEN

        }
        notificationManager.createNotificationChannel(channel)
    }


}