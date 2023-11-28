package com.example.hospital_application

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 3000
    private lateinit var imgView: ImageView
    private val networkCheckInterval = 5000L // Check every 5 seconds
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        checkNetworkAvailability()
        val imgView = findViewById<ImageView>(R.id.logo)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_anim)
        imgView.startAnimation(fadeInAnimation)
        Handler().postDelayed({
            // This code will run after the splashTimeOut milliseconds
            val intent = Intent(this, Onboardingone::class.java)
            startActivity(intent)
            finish() // Close this activity to prevent going back to the splash screen
        }, splashTimeOut)

        // Schedule periodic network checks
        handler.postDelayed(object : Runnable {
            override fun run() {
                checkNetworkAvailability()
                handler.postDelayed(this, networkCheckInterval)
            }
        }, networkCheckInterval)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove the periodic network check when the activity is destroyed
        handler.removeCallbacksAndMessages(null)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            val mobileNetworkInfo: NetworkInfo? = it.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            val wifiNetworkInfo: NetworkInfo? = it.getNetworkInfo(ConnectivityManager.TYPE_WIFI)

            return (mobileNetworkInfo != null && mobileNetworkInfo.isConnected) ||
                    (wifiNetworkInfo != null && wifiNetworkInfo.isConnected)
        }
        return false
    }

    private fun checkNetworkAvailability() {
        if (!isNetworkAvailable()) {
            // Network is not available, show a toast message

            Toast.makeText(this, "Mobile data and Wi-Fi are not available", Toast.LENGTH_LONG).show()
        } else {

        }
    }

}