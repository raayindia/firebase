package com.example.hospital_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class Onboardingone : AppCompatActivity() {
    private lateinit var skiptext : TextView
    private lateinit var next1: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboardingone)

        skiptext = findViewById(R.id.txtviewskip )
        next1 = findViewById(R.id.nexttextview1)



        next1.setOnClickListener {

            intent = Intent(this, OnboardingTwoActivity::class.java)
            startActivity(intent)

        }


    }
}