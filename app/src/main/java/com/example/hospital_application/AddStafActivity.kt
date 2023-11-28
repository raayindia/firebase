package com.example.hospital_application

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.hospital_application.databinding.AddstafBinding
import com.example.hospital_application.Responses.AddstafResponse
import com.example.hospital_application.Responses.UpdatehospitalStaf_Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddStafActivity : AppCompatActivity() {

    private lateinit var addstafbinding: AddstafBinding
    private lateinit var addstafresponse: AddstafResponse
    private lateinit var updatestafresponse: UpdatehospitalStaf_Response
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the binding
        addstafbinding = AddstafBinding.inflate(layoutInflater)
        val view = addstafbinding.root
        setContentView(view)
        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        addstafbinding.addstafbutton.setOnClickListener {
            val Hospital_id = sharedPref.getString("hospital_id", "")
//            val Hadmincontac = "8328227728"
            val Staff_name = addstafbinding.editTextName.text.toString().trim()
            val Staff_phoneno = addstafbinding.editmobilenumber.text.toString().trim()
            val Staff_email = addstafbinding.editemail.text.toString().trim()
            val Staff_role = addstafbinding.editdesignation.text.toString().trim()


//
            if (Hospital_id != null) {
                fromadminaddstaf(Hospital_id, Staff_phoneno, Staff_email, Staff_name,Staff_role)
            }

        }



        addstafbinding.updatebutton.setOnClickListener {
            val Hospital_id = sharedPref.getString("hospital_id", "")
//            val Hadmincontac = "8328227728"
            val Staff_name = addstafbinding.editTextName.text.toString().trim()
            val Staff_phoneno = addstafbinding.editmobilenumber.text.toString().trim()
            val Staff_email = addstafbinding.editemail.text.toString().trim()
            val Staff_role = addstafbinding.editdesignation.text.toString().trim()

            if (addstafbinding.editmobilenumber.length() ==10) {

            } else {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                addstafbinding.editmobilenumber.setError("Mobile number should be of 10 digits")
            }
            if (Hospital_id != null) {
                updatehospitalstafdetails(Hospital_id, Staff_phoneno, Staff_email, Staff_name,Staff_role)
            }

        }

    }

    @SuppressLint("MissingInflatedId")
    private fun showServerErrorDialog() {
        val dialogView = layoutInflater.inflate(R.layout.error_dialog, null)
        val errorTextView = dialogView.findViewById<TextView>(R.id.errorMessage)
        val dismissButton = dialogView.findViewById<Button>(R.id.dismissButton)

        errorTextView.text = addstafresponse.message

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        dismissButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun fromadminaddstaf(
        Hospital_id: String,
        Staff_phoneno: String,
        Staff_email: String,
        Staff_name: String,
        Staff_role: String
    ) {
        val loginService = ApiClient.buildService(Api_Interface::class.java)
        val requestCall = loginService.fromadminaddstaf(Hospital_id, Staff_phoneno, Staff_email, Staff_name,Staff_role)
        requestCall.enqueue(object : Callback<AddstafResponse> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<AddstafResponse>,
                response: Response<AddstafResponse>
            ) {
                if (response.isSuccessful) { // Status code between 200 to 299
                    addstafresponse = response.body()!!
                    if (addstafresponse.status == "1") {
                         showToast(addstafresponse.message)
//                        val i = Intent(this@AddStafActivity, AdminloginActivity::class.java)
//                        startActivity(i)
                    } else {

                        showServerErrorDialog()
                    }
                } else if (response.code() == 401) { // Unauthorized
                    showToast(getString(R.string.session_exp))
                } else { // Application-level failure
                    showToast(getString(R.string.server_error))
                }
            }

            override fun onFailure(call: Call<AddstafResponse>, t: Throwable) {
                showToast(getString(R.string.session_exp))
            }
        })
    }

    private fun updatehospitalstafdetails(
        Hospital_id: String,
        Staff_phoneno: String,
        Staff_email: String,
        Staff_name: String,
        Staff_role: String
    ) {
        val loginService = ApiClient.buildService(Api_Interface::class.java)
        val requestCall = loginService.updatehospitalstafdetails(Hospital_id, Staff_phoneno, Staff_email, Staff_name,Staff_role)
        requestCall.enqueue(object : Callback<UpdatehospitalStaf_Response> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<UpdatehospitalStaf_Response>,
                response: Response<UpdatehospitalStaf_Response>
            ) {
                if (response.isSuccessful) { // Status code between 200 to 299
                    updatestafresponse = response.body()!!
                    if (updatestafresponse.status == "1") {
                        showToast(updatestafresponse.message)

//                        val i = Intent(this@AddStafActivity, AdminloginActivity::class.java)
//                        startActivity(i)
                    } else {

                        showServerErrorDialog()
                    }
                } else if (response.code() == 401) { // Unauthorized
                    showToast(getString(R.string.session_exp))
                } else { // Application-level failure
                    showToast(getString(R.string.server_error))
                }
            }

            override fun onFailure(call: Call<UpdatehospitalStaf_Response>, t: Throwable) {
                showToast(getString(R.string.session_exp))
            }
        })
    }

}


