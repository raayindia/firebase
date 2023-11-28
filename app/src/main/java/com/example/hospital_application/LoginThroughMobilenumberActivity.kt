package com.example.hospital_application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hospital_application.Responses.MobilenumberResponse
import com.example.hospital_application.databinding.ActivityAdminloginBinding

class LoginThroughMobilenumberActivity : AppCompatActivity() {

    lateinit var activityadiminlogingbinding: ActivityAdminloginBinding
    private lateinit var adminloginResponse: MobilenumberResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityadiminlogingbinding = ActivityAdminloginBinding.inflate(layoutInflater)
        val view = activityadiminlogingbinding.root
        setContentView(view)

        activityadiminlogingbinding.idBtnGetOtp.setOnClickListener {
            // Get the input values when the button is clicked
            val MobileNo = activityadiminlogingbinding.idEdtPhoneNumber.text.toString().trim()
//            loginthroughmobilenumber(MobileNo)

//            if (activityadiminlogingbinding.idEdtPhoneNumber.length() ==10) {
//
//            } else {
//                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
//                activityadiminlogingbinding.idEdtPhoneNumber.setError("Mobile number should be of 10 digits")
//            }
//
//            if (MobileNo.isNotEmpty() ) {
//
//            } else {
//                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
//            }
        }
    }
//    private fun loginthroughmobilenumber(MobileNo: String) {
//        val loginService = ApiClient.buildService(Api_Interface::class.java)
//        val requestCall = loginService.loginthroughmobilenumber(MobileNo)
//        requestCall.enqueue(object : Callback<MobilenumberResponse> {
//            override fun onResponse(
//                call: Call<MobilenumberResponse>,
//                response: Response<MobilenumberResponse>
//            ) {
//                if (response.isSuccessful) { // Status code between 200 to 299
//                    val adminloginResponse = response.body()
//
//                    if (adminloginResponse != null && adminloginResponse.data.isNotEmpty()) {
//                        val role = adminloginResponse.data[0].role
//
//                        when (role) {
//                            1 -> {
//                                val i = Intent(this@LoginThroughMobilenumberActivity, VerifyOTPActivity::class.java)
//                                i.putExtra("role", adminloginResponse.data[0].role)
//                                i.putExtra("name", adminloginResponse.data[0].Name)
//                                startActivity(i)
//                            }
//                            2 -> {
//                                val i = Intent(this@LoginThroughMobilenumberActivity, LoginActivity::class.java)
//                                i.putExtra("role", adminloginResponse.data[0].role)
//                                i.putExtra("name", adminloginResponse.data[0].Name)
//                                startActivity(i)
//                            }
//                            3 -> {
//                                val i = Intent(this@LoginThroughMobilenumberActivity, OtpAccessActivity::class.java)
//                                i.putExtra("role", adminloginResponse.data[0].role)
//                                i.putExtra("name", adminloginResponse.data[0].Name)
//                                startActivity(i)
//                            }
//                            4 -> {
//                                val i = Intent(this@LoginThroughMobilenumberActivity, TelecallerActivity::class.java)
//                                i.putExtra("role", adminloginResponse.data[0].role)
//                                i.putExtra("name", adminloginResponse.data[0].Name)
//                                startActivity(i)
//                            }
//                            else -> {
//                                showServerErrorDialog()
//                            }
//                        }
//                    } else {
//                        showServerErrorDialog()
//                    }
//                } else if (response.code() == 401) { // Unauthorized
//                    showToast(getString(R.string.session_exp))
//                } else { // Application-level failure
//                    showToast(getString(R.string.server_error))
//                }
//            }
//
//            override fun onFailure(call: Call<MobilenumberResponse>, t: Throwable) {
//                showToast(getString(R.string.session_exp))
//            }
//        })
//    }


//    private fun showServerErrorDialog() {
//        val dialogView = layoutInflater.inflate(R.layout.error_dialog, null)
//        val errorTextView = dialogView.findViewById<TextView>(R.id.errorMessage)
//        val dismissButton = dialogView.findViewById<Button>(R.id.dismissButton)
//
//        errorTextView.text = adminloginResponse.message
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
