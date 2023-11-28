package com.example.hospital_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class OnboardingTwoActivity : AppCompatActivity() {
    private lateinit var skiptext2 : TextView
    private lateinit var next2: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)

        skiptext2 = findViewById(R.id.txtviewskip2 )
        next2 = findViewById(R.id.nexttextview2)

        next2.setOnClickListener {

    val i = Intent(this, Onboardingscreenthree::class.java)
    startActivity(i)


}

        skiptext2.setOnClickListener {

            val i = Intent(this, RegistrationActivity::class.java)
            startActivity(i)


        }

    }
}