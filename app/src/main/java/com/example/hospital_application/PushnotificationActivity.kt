package com.example.hospital_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseApp

class PushnotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pushnotification)
        FirebaseApp.initializeApp(this)
        onNewIntent()









    }

    private fun onNewIntent() {
        super.onNewIntent(intent)

        if (intent != null && intent.hasExtra("data")) {
            val notificationData = intent.getStringExtra("data")
            // Handle the notification data here
        }
    }
}