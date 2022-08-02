package com.git_kt_project_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

const val TOPIC ="/topics/myTopic" // topic to subscribe to
class MainActivity : AppCompatActivity() {
    val TAG ="MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //the source video :
        // https://www.youtube.com/watch?v=HoFWPPv1ih8&list=PLQkwcJG4YTCSSow0ulsj-kIc6drYG_D0E

        //now we need to subscribe to the topic
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
      Send_btn.setOnClickListener {
          val title = etTitel.text.toString()
          val message = etMessage.text.toString()

          if(title.isNotEmpty() && message.isNotEmpty()){
            PushNotification(
                NotificationData(title,message),
                TOPIC
            ).also {
                sendNotification(it)
            }
          }
      }
// what is above is enough to send a message now we need a service to receive it


    }
    private fun sendNotification(notification: PushNotification)=
        CoroutineScope(Dispatchers.IO).launch {
            try {
            val response = RetrofitInstance.api.postNotification(notification)
                if(response.isSuccessful){
                    Log.d(TAG," Response : ${Gson().toJson(response)}")
                }else {
                    Log.e(TAG,response.errorBody().toString())
                }
            }catch (e:Exception){
                Log.e(TAG,e.toString())
            }
        }
}