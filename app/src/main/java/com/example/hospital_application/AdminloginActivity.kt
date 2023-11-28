package com.example.hospital_application

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hospital_application.R
import com.example.hospital_application.databinding.ActivityAdminloginBinding
import com.example.hospital_application.Responses.AdminLoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminloginActivity : AppCompatActivity() {

    lateinit var activityadiminlogingbinding: ActivityAdminloginBinding
    private lateinit var adminloginResponse: AdminLoginResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityadiminlogingbinding = ActivityAdminloginBinding.inflate(layoutInflater)
        val view = activityadiminlogingbinding.root
        setContentView(view)

        activityadiminlogingbinding.idBtnGetOtp.setOnClickListener {
            // Get the input values when the button is clicked

            val Hadmincontac = activityadiminlogingbinding.idEdtPhoneNumber.text.toString().trim()
            if (activityadiminlogingbinding.idEdtPhoneNumber.length() ==10) {

            } else {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                activityadiminlogingbinding.idEdtPhoneNumber.setError("Mobile number should be of 10 digits")
            }

            if (Hadmincontac.isNotEmpty() ) {
                adminloginmobilenumber(Hadmincontac)
            } else {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun adminloginmobilenumber(
        Hadmincontac: String,

    ) {
        val loginService = ApiClient.buildService(Api_Interface::class.java)
        val requestCall = loginService.adminloginmobilenumber(Hadmincontac)
        requestCall.enqueue(object : Callback<AdminLoginResponse> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<AdminLoginResponse>,
                response: Response<AdminLoginResponse>
            ) {
                when {
                    response.isSuccessful -> { // Status code between 200 to 299
                        adminloginResponse = response.body()!!
                        if (adminloginResponse.status == "1") {

                            val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            val editor = sharedPref.edit()
                            editor.putString("hospital_id",
                                adminloginResponse.data.Hid.toString()
                            )
                            editor.apply()


                            showToast("sucessfully done")

                            val i = Intent(this@AdminloginActivity, AddStafActivity::class.java)
                            startActivity(i)
                        } else {

                            showToast(adminloginResponse.message)

                        }
                    }
                    response.code() == 401 -> { // Unauthorized
                        // showToast(getString(R.string.session_exp))
                    }
                    else -> { // Application-level failure
                        // Status code in the range of 300's, 400's, and 500's
                        // showToast(getString(R.string.server_error))
                    }
                }
            }

            override fun onFailure(call: Call<AdminLoginResponse>, t: Throwable) {
                showToast(getString(R.string.session_exp))
            }
        })
    }
}
