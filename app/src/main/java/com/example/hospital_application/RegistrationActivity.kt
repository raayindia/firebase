package com.example.hospital_application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hospital_application.Responses.User
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegistrationActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var mobileEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var alreadyreg: TextView
    private lateinit var usertype: String
    private lateinit var auth: FirebaseAuth
    private lateinit var radioButton: RadioButton
    private lateinit var radioGroup: RadioGroup
    private lateinit var database: DatabaseReference
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()
        initViews()
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            radioButton = findViewById<RadioButton>(checkedId)
            Toast.makeText(
                this@RegistrationActivity,
                "Selected Radio Button is : " + radioButton.text,
                Toast.LENGTH_SHORT
            ).show()
            usertype = radioButton.text.toString()
            if (currentUser != null) {
                startActivity(Intent(this, ReceptionActivity::class.java))
                finish()
            } else {
                alreadyreg.setOnClickListener {

                    val i = Intent(this, LoginActivity::class.java)
                    startActivity(i)

                }



            }
        }





        registerButton.setOnClickListener {

            val name = nameEditText.text.toString()
            val mobile = mobileEditText.text.toString()
            val password = passwordEditText.text.toString()
            val email = mobileEditText.text.toString() + "@gmail.com"

            if (nameEditText.text.toString().trim().isEmpty()) {
                nameEditText.requestFocus()
                nameEditText.error = "Enter Name"

            } else if (mobileEditText.text.toString().trim().length < 10) {

                mobileEditText.requestFocus()
                mobileEditText.error = "Mobile number should be of minimum of 10 numbers"
            }else if (mobileEditText.text.toString().trim().isEmpty()) {
                mobileEditText.requestFocus()
                mobileEditText.error = "Enter Mobilenumber"
            }else if
                          (passwordEditText.text.toString().trim().isEmpty()) {
                passwordEditText.requestFocus()
                passwordEditText.error = "Enter Password"
            } else if
                           (usertype.isEmpty()) {
                // Ensure a radio button is selected
                Toast.makeText(this@RegistrationActivity, "Please select a user type.", Toast.LENGTH_SHORT).show()


            }else{
                firebaseAuth(name, mobile, password, email)
            }

        }
        alreadyreg.setOnClickListener {

            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)

        }
    }


    private fun firebaseAuth(name: String, mobile: String, password: String, email: String) {

        database = FirebaseDatabase.getInstance().getReference("Users")
        val userData = User(name, email, mobile, password, usertype)
        database.child(name).setValue(userData).addOnSuccessListener {
            nameEditText.text.clear()
            mobileEditText.text.clear()
            passwordEditText.text.clear()
            Toast.makeText(this, "Reg success", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, LoginActivity::class.java))
        }
            .addOnFailureListener{ e->
                Toast.makeText(this, "Reg failed",Toast.LENGTH_LONG).show()
            }
    }

    private fun initViews() {
        nameEditText = findViewById(R.id.editTextName)
        mobileEditText = findViewById(R.id.editTextMobile)
        passwordEditText = findViewById(R.id.editTextPassword)
        registerButton = findViewById(R.id.registerbutton)
        alreadyreg = findViewById(R.id.alreadyreg)
        radioGroup = findViewById(R.id.radioGroup)
    }


}





