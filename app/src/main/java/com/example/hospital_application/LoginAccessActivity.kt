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
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.hospital_application.Responses.LogincheckResponse
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class LoginAccessActivity : AppCompatActivity() {
    private lateinit var logincheckresponse: LogincheckResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_access)

        val inputMobile = findViewById<EditText>(R.id.idEdtPhoneNumberlogin)
        val buttonGetOTP = findViewById<Button>(R.id.idBtnlogin)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // Initialize shared preferences
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Use text property to set the text of EditText
        inputMobile.setText(sharedPreferences.getString("mobilenumber", ""))

        buttonGetOTP.setOnClickListener {
            val mobileNumber = inputMobile.text.toString()

            if (mobileNumber.isEmpty()) {
                Toast.makeText(this, "Enter mobile", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save mobile number to shared preferences
            editor.putString("mobilenumber", mobileNumber)
            editor.apply()

            // Call the login service
            checkLoginType(mobileNumber)
        }
    }

    private fun checkLoginType(mobileNumber: String) {
        val loginService = ApiClient.buildService(Api_Interface::class.java)
        val requestCall = loginService.checklogintype(mobileNumber)
        requestCall.enqueue(object : Callback<LogincheckResponse> {
            override fun onResponse(
                call: Call<LogincheckResponse>,
                response: Response<LogincheckResponse>
            ) {
                if (response.isSuccessful) {
                    logincheckresponse = response.body()!!
                    when (logincheckresponse.status) {
                        0, 2 -> {

                            navigateToOTPScreen(mobileNumber)
                        }
                        1 -> {
                            navigateToLoginActivity()
                        }
                    }
                } else if (response.code() == 401) {
                    // Unauthorized
                    showToast(getString(R.string.session_exp))
                } else {
                    // Application-level failure
                    showToast(getString(R.string.server_error))
                }
            }

            override fun onFailure(call: Call<LogincheckResponse>, t: Throwable) {
                showToast(getString(R.string.session_exp))
            }
        })
    }

    private fun navigateToOTPScreen(mobileNumber: String) {
        val options = PhoneAuthOptions.newBuilder()
            .setPhoneNumber("+91$mobileNumber")
            .setTimeout(80L, TimeUnit.SECONDS)
            .setActivity(this@LoginAccessActivity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    // Handle verification completed
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // Handle verification failed
                    showToast(e.message ?: getString(R.string.app_name))
                }

                override fun onCodeSent(
                    verificationId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    val intent = Intent(applicationContext, VerifyOTPActivity::class.java)
                    intent.putExtra("mobile", mobileNumber)
                    intent.putExtra("verificationId", verificationId)
                    startActivity(intent)
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this@LoginAccessActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}

