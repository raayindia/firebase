package com.example.hospital_application

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.hospital_application.Responses.RegisterResponse
import com.example.hospital_application.Responses.RegisterverificationResponse
import com.example.hospital_application.databinding.AdminregisterScreenBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdminRegistrationActivity : AppCompatActivity() {
    private lateinit var bankaccountdetailsbinding: AdminregisterScreenBinding
    private lateinit var registerresponse: RegisterResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the binding
        bankaccountdetailsbinding = AdminregisterScreenBinding.inflate(layoutInflater)
        val view = bankaccountdetailsbinding.root
        setContentView(view)




        bankaccountdetailsbinding.registerbtn.setOnClickListener {
            // Get the input values when the button is clicked
            val hospitaladminname = bankaccountdetailsbinding.editTextUsername.text.toString().trim()
            val hospitalname = bankaccountdetailsbinding.editTexthospitalName.text.toString().trim()
            val hospitalmobile = bankaccountdetailsbinding.editTextnumber.text.toString().trim()
            val hospitalemail = bankaccountdetailsbinding.editTextmail.text.toString().trim()


            val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("hospitalname", hospitalname)
            editor.putString("hospitalmobile", hospitalmobile)
            editor.putString("hospitalmail", hospitalemail)
            editor.putString("hospitaladminname", hospitaladminname)
            editor.apply()


            if (hospitalmobile.length == 10) {
                addhospital(hospitalname, hospitalmobile, hospitalemail, hospitaladminname)


            } else {
                Toast.makeText(this, "Mobile number should be of 10 digits", Toast.LENGTH_SHORT).show()
                bankaccountdetailsbinding.editTextnumber.error = "Mobile number should be of 10 digits"
            }
//            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(bankaccountdetailsbinding.editTextmail.toString()).matches()) {
//                showToast("Invalid email address")
//
//            }

        }
    }

    private fun addhospital(
        hospitalname: String,
        hospitalmobile: String,
        hospitalemail: String,
        hospitaladminname: String
    ) {
        val loginService = ApiClient.buildService(Api_Interface::class.java)
        val requestCall = loginService.registerdetails(hospitalname, hospitalmobile, hospitalemail, hospitaladminname)
        requestCall.enqueue(object : Callback<RegisterResponse> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) { // Status code between 200 to 299
                    registerresponse = response.body()!!
                    val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("hospitalid",registerresponse.data )
                    editor.apply()

                    if (registerresponse.status == "1") {
                        val i = Intent(this@AdminRegistrationActivity, welcomeActivity::class.java)
                        i.putExtra("hospitalname",hospitalname )
                        i.putExtra("hospitalmobile",hospitalmobile )
                        i.putExtra("hospitalmail",hospitalemail )
                        i.putExtra("hospitaladminname",hospitaladminname )
                        i.putExtra("hospitalid",registerresponse.data )


                        startActivity(i)

                    } else {

                        showServerErrorDialog()
                    }
                } else if (response.code() == 401) { // Unauthorized
                    showToast(getString(R.string.session_exp))
                } else { // Application-level failure
                    showToast(getString(R.string.server_error))
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                showToast(getString(R.string.session_exp))
            }
        })
    }

    @SuppressLint("MissingInflatedId")
    private fun showServerErrorDialog() {
        val dialogView = layoutInflater.inflate(R.layout.error_dialog, null)
        val errorTextView = dialogView.findViewById<TextView>(R.id.errorMessage)
        val dismissButton = dialogView.findViewById<Button>(R.id.dismissButton)

        errorTextView.text = registerresponse.message

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        dismissButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
}
