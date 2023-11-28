package com.example.hospital_application

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.hospital_application.Responses.Loginwithrole_Response
import com.example.hospital_application.Responses.RegisterResponse
import com.example.hospital_application.Responses.RegisterverificationResponse
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit




class VerifyOTPActivity : AppCompatActivity() {
    private lateinit var registerverifresponse: RegisterResponse
    private lateinit var inputCode1: EditText
    private lateinit var inputCode2: EditText
    private lateinit var inputCode3: EditText
    private lateinit var inputCode4: EditText
    private lateinit var inputCode5: EditText
    private lateinit var inputCode6: EditText
    private lateinit var textMobile: TextView
    private lateinit var buttonVerify: Button
//    private lateinit var progressBar: ProgressBar

    private lateinit var loginwithroleresponse:Loginwithrole_Response
    private var verificationId: String? = null
    private val editTextList = mutableListOf<EditText>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otpactivity)


        init()
        setTextMobile()
        setupOTPInputs()
        setVerificationId()
        setListener()
    }
    private fun init() {
        inputCode1 = findViewById(R.id.inputcode1)
        inputCode2 = findViewById(R.id.inputCode2)
        inputCode3 = findViewById(R.id.inputCode3)
        inputCode4 = findViewById(R.id.inputCode4)
        inputCode5 = findViewById(R.id.inputCode5)
        inputCode6 = findViewById(R.id.inputCode6)

        textMobile = findViewById(R.id.textMobile)
        buttonVerify = findViewById(R.id.verifybtn)
//        progressBar = findViewById(R.id.progressBar)
        setupTextChangeListeners()
    }

    private fun setupTextChangeListeners() {
        for (i in 0 until editTextList.size - 1) {
            val currentEditText = editTextList[i]
            val nextEditText = editTextList[i + 1]

            currentEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) { // You can change this condition as needed
                        nextEditText.requestFocus()
                    }
                }
            })
        }
    }
    private fun setListener() {
        buttonVerify.setOnClickListener {
            val sharedPreferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

            val hospitalmobile = sharedPreferences.getString("mobilenumber", "")
            if (hospitalmobile != null) {
                loginthroughrole(hospitalmobile)
            }
            if (inputCode1.text.toString().trim().isEmpty()
                || inputCode2.text.toString().trim().isEmpty()
                || inputCode3.text.toString().trim().isEmpty()
                || inputCode4.text.toString().trim().isEmpty()
                || inputCode5.text.toString().trim().isEmpty()
                || inputCode6.text.toString().trim().isEmpty()
            ) {
                Toast.makeText(this@VerifyOTPActivity, "Please enter valid code", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val code = inputCode1.text.toString() +
                    inputCode2.text.toString() +
                    inputCode3.text.toString() +
                    inputCode4.text.toString() +
                    inputCode5.text.toString() +
                    inputCode6.text.toString()

            if (verificationId != null) {
//                progressBar.visibility = View.VISIBLE
                buttonVerify.visibility = View.INVISIBLE
                val phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId!!, code)
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                    .addOnCompleteListener { task ->
//                        progressBar.visibility = View.GONE
                        buttonVerify.visibility = View.VISIBLE
                        if (task.isSuccessful) {

                        } else {
                            Toast.makeText(this@VerifyOTPActivity, "The verification code entered was invalid", Toast.LENGTH_SHORT).show()
                            if (hospitalmobile != null) {
                                loginthroughrole(hospitalmobile)
                            }
                        }
                    }
            }
        }

        findViewById<View>(R.id.textResendOTP).setOnClickListener {
            val mobile = intent.getStringExtra("mobile")
            if (mobile != null) {
                val options = PhoneAuthOptions.newBuilder()
                    .setPhoneNumber("+91$mobile")
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {}

                        override fun onVerificationFailed(e: FirebaseException) {
                            Toast.makeText(this@VerifyOTPActivity, e.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onCodeSent(newVerificationId: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                            verificationId = newVerificationId
                            Toast.makeText(this@VerifyOTPActivity, "OTP Sent", Toast.LENGTH_SHORT).show()
                        }
                    })
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }
    }
    private fun loginthroughrole(mobileNumber: String) {


        val loginService = ApiClient.buildService(Api_Interface::class.java)
        val requestCall = loginService.checkreg_mobilenumber(mobileNumber)
        if (requestCall != null) {
            requestCall.enqueue(object : Callback<Loginwithrole_Response> {
                @SuppressLint("SuspiciousIndentation")
                override fun onResponse(
                    call: Call<Loginwithrole_Response>,
                    response: Response<Loginwithrole_Response>
                ) {
                    if (response.isSuccessful) {
                        loginwithroleresponse = response.body()!!
                        val status = loginwithroleresponse.status


                        when (status) {
                            0.toString() -> {
                                val i = Intent(this@VerifyOTPActivity, AdminRegistrationActivity::class.java)
                                startActivity(i)

                            }

                            1.toString() -> {
                                val role = loginwithroleresponse.data[0].userrole
                                when (role) {
                                    1 -> {

                                        val i = Intent(this@VerifyOTPActivity, SuperAdminActivity::class.java)
                                        i.putExtra("role", role)
                                        i.putExtra("name", loginwithroleresponse.data[0].username)
                                        i.putExtra("hospitalid", loginwithroleresponse.data[0].baseid)
                                        startActivity(i)
                                    }

                                    2 -> {
                                        // Go to WelcomeActivity
                                        val i = Intent(this@VerifyOTPActivity, welcomeActivity::class.java)
                                        i.putExtra("role", role)
                                        i.putExtra("name", loginwithroleresponse.data[0].username)
                                        i.putExtra("hospitalid", loginwithroleresponse.data[0].baseid)
                                        startActivity(i)
                                    }

                                    3 -> {
                                        // Go to ReceptionActivity
                                        // ...

                                    }

                                    4 -> {
                                        // Go to TelecallerActivity
                                        // ...
                                    }
                                }



                            }
                            else -> {
                                showToast(getString(R.string.server_error))
                            }
                        }
                    } else if (response.code() == 401) {
                        showToast(getString(R.string.session_exp))
                    } else {
                        showToast(getString(R.string.server_error))
                    }
                }


                override fun onFailure(call: Call<Loginwithrole_Response>, t: Throwable) {
                    showToast(getString(R.string.session_exp))
                    showServerErrorDialog()
                }
            })
        }
    }
    @SuppressLint("MissingInflatedId")
    private fun showServerErrorDialog() {
        val dialogView = layoutInflater.inflate(R.layout.error_dialog, null)
        val errorTextView = dialogView.findViewById<TextView>(R.id.errorMessage)
        val dismissButton = dialogView.findViewById<Button>(R.id.dismissButton)

        errorTextView.text = registerverifresponse.message

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        dismissButton.setOnClickListener {
            dialog.dismiss()
            val i = Intent(this@VerifyOTPActivity, AdminRegistrationActivity::class.java)

        }

        dialog.show()
    }

    private fun setVerificationId() {
        verificationId = intent.getStringExtra("verificationId")
    }

    private fun setTextMobile() {
        textMobile.text = String.format("+91-%s", intent.getStringExtra("mobile"))
    }

    private fun setupOTPInputs() {
        val inputFields = listOf(inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6)

        for (i in 0 until inputFields.size) {
            val currentField = inputFields[i]
            val previousField = if (i > 0) inputFields[i - 1] else null

            currentField.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1) { //if the length of the new text in the currentField is equal to 1, meaning a single character has been entered
                        if (i < inputFields.size - 1) {
                            inputFields[i + 1].requestFocus()
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
            // Handle the back button press to clear the current field and move to the previous field
            currentField.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (currentField.text?.isNotEmpty() == true) {
                        // If there is text, clear the text in the current field
                        currentField.text = null
                    } else if (i > 0) {
                        // If the current field is empty, move to the previous field
                        previousField?.requestFocus()
                    }
                    true // Consume the event
                } else {
                    false
                }
            }
        }
        inputFields.first().requestFocus()
    }
}




