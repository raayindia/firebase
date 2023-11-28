package com.example.hospital_application

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hospital_application.Responses.LoginwithPwConformationResp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

        private lateinit var loginconformresponse: LoginwithPwConformationResp

        @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)

            val password = findViewById<EditText>(R.id.idEdtPasswordlogin)
            val loginbtn = findViewById<Button>(R.id.idBtnsubmit)
            val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

// Retrieve the value from SharedPreferences, use a default value if not found
            val defaultValue = "" // Replace with your desired default value
            val mobileno = sharedPreferences.getString("mobilenumber", defaultValue)

            loginbtn.setOnClickListener {
                if (mobileno != null) {
                    passwordchecking(mobileno, password.text.toString())
                }
            }
        }

        private fun passwordchecking(mobileno: String, password: String) {
            val loginService = ApiClient.buildService(Api_Interface::class.java)
            val requestCall = loginService.checkregmobilenumber(mobileno, password)

            requestCall.enqueue(object : Callback<LoginwithPwConformationResp> {
                @SuppressLint("SuspiciousIndentation")
                override fun onResponse(
                    call: Call<LoginwithPwConformationResp>,
                    response: Response<LoginwithPwConformationResp>
                ) {
                    loginconformresponse = response.body()!!

                    if (response.isSuccessful) { // Status code between 200 to 299

                        // Assuming data is a list, modify the index accordingly

                        if (loginconformresponse.status.equals("1")) {

                            val role = loginconformresponse.data[0].userrole


                            when (role) {
                                1 -> {
                                    showToastAndNavigate("welcome viswa you are entered in Admin home", welcomeActivity::class.java)

                                    intent.putExtra("hospitalname", loginconformresponse.data[0].hospitalname)




                                }

                                2 -> {
                                    showToastAndNavigate(
                                        "welcome  you are entered superadmin home",
                                        welcomeActivity::class.java
                                    )
                                }

                                3 -> {
                                    showToastAndNavigate(
                                        "welcome you are entered Reception home",
                                        welcomeActivity::class.java
                                    )
                                }

                                4 -> {
                                    showToastAndNavigate(
                                        "welcome you are entered Telecaller home",
                                        welcomeActivity::class.java
                                    )
                                }
                            }


                        }else{
                            val intent = Intent(this@LoginActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }

                    } else if (response.code() == 401) { // Unauthorized
                        showToast(getString(R.string.session_exp))
                    } else { // Application-level failure
                        showToast(getString(R.string.server_error))
                    }
                }

                override fun onFailure(call: Call<LoginwithPwConformationResp>, t: Throwable) {
                    showToast(getString(R.string.session_exp))
//                showServerErrorDialog
                }
            })
        }

        private fun showToast(message: String) {
            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
        }

        private fun showToastAndNavigate(message: String, destination: Class<*>) {
            showToast(message)
            startActivity(Intent(this@LoginActivity, destination))
        }

//    @SuppressLint("MissingInflatedId")
//    private fun showServerErrorDialog() {
//        val dialogView = layoutInflater.inflate(R.layout.error_dialog, null)
//        val errorTextView = dialogView.findViewById<TextView>(R.id.errorMessage)
//        val dismissButton = dialogView.findViewById<Button>(R.id.dismissButton)
//
////        errorTextView.text = bankdetailsResponse.message
//
//        val builder = AlertDialog.Builder(this)
//        builder.setView(dialogView)
//        val dialog = builder.create()
//
//        dismissButton.setOnClickListener {
//            dialog.dismiss()
//        }
//
//        dialog.show()
//    }




}















