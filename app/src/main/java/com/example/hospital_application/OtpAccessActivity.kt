package com.example.hospital_application

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
class OtpAccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminlogin)
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        val savedText1 = sharedPreferences.getString("hospitalmobile", "")

        val inputMobile = findViewById<EditText>(R.id.idEdtPhoneNumber)
        val register = findViewById<TextView>(R.id.register)
        inputMobile.setText(savedText1)


        val buttonGetOTP = findViewById<Button>(R.id.idBtnGetOtp)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)


        register.setOnClickListener {
            val intent = Intent(applicationContext, AdminRegistrationActivity::class.java)
            startActivity(intent)
        }

        buttonGetOTP.setOnClickListener {

            if (inputMobile.text.toString().isEmpty()) {

                Toast.makeText(this@OtpAccessActivity, "Enter mobile", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            buttonGetOTP.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE


            val options = PhoneAuthOptions.newBuilder()

                .setPhoneNumber("+91" + inputMobile.text.toString())
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                        progressBar.visibility = View.GONE
                        buttonGetOTP.visibility = View.VISIBLE

                    }

                    override fun onVerificationFailed(e: FirebaseException) {

                        progressBar.visibility = View.GONE
                        buttonGetOTP.visibility = View.VISIBLE
//                        val intent = Intent(applicationContext, VerifyOTPActivity::class.java)

                        Toast.makeText(this@OtpAccessActivity, e.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onCodeSent(verificationId: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                        progressBar.visibility = View.GONE
                        buttonGetOTP.visibility = View.VISIBLE
                        val intent = Intent(applicationContext, VerifyOTPActivity::class.java)
                        intent.putExtra("mobile", inputMobile.text.toString())
                        intent.putExtra("verificationId", verificationId)
                        startActivity(intent)

                    }
                })
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }
}


