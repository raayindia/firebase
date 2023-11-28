package com.example.hospital_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Onboardingscreenthree : AppCompatActivity() {

    private lateinit var next2: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboardingscreenthree)


        next2 = findViewById(R.id.getstart_textview)

        next2.setOnClickListener {

            val i = Intent(this, RegistrationActivity::class.java)
            startActivity(i)


        }



    }

}