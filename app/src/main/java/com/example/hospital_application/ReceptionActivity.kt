package com.example.hospital_application
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class ReceptionActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var editTextName: EditText
    private lateinit var editTextMobile: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextJoinDate: EditText
    private lateinit var editTextDischargeDate: EditText
    private lateinit var signout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reception)

        editTextDischargeDate = findViewById(R.id.dateofdischarge)
        editTextJoinDate = findViewById(R.id.dateofjoin)
        signout= findViewById(R.id.signOutButton)
        signout.setOnClickListener(View.OnClickListener {
            signOut()
        })
        editTextDischargeDate.setOnClickListener(View.OnClickListener {
            showDatePickerDialog()
        })
        editTextJoinDate.setOnClickListener(View.OnClickListener {
            showjoinDatePickerDialog()
        })
        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().reference

        // Initialize UI elements
        editTextName = findViewById(R.id.editTextName)
        editTextMobile = findViewById(R.id.editTextMobile)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextJoinDate = findViewById(R.id.dateofjoin)
        editTextDischargeDate = findViewById(R.id.dateofdischarge)

        val saveButton = findViewById<Button>(R.id.registerbutton)
        saveButton.setOnClickListener(View.OnClickListener {
            saveUserData()
        })
    }

    private fun signOut() {

//        val firebaseAuth = FirebaseAuth.getInstance()
//        firebaseAuth.signOut()
        intent = Intent(this@ReceptionActivity, RegistrationActivity::class.java)

    }

    private fun showjoinDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                // Handle the selected date, month, and year
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                editTextJoinDate.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                // Handle the selected date, month, and year
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                editTextDischargeDate.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun saveUserData() {
        val name = editTextName.text.toString()
        val mobile = editTextMobile.text.toString()
        val doctorname = editTextPassword.text.toString()
        val joinDate = editTextJoinDate.text.toString()
        val dischargeDate = editTextDischargeDate.text.toString()

        // Create a User object or use a Map to store the data
        val user = PersonData(name, mobile, doctorname, joinDate, dischargeDate)

        // Push the user data to the Firebase Realtime Database
        val userKey = databaseReference.child("users").push().key

        if (userKey != null) {

            Toast.makeText(this@ReceptionActivity, "successfully saved data", Toast.LENGTH_LONG).show()
            databaseReference.child("users").child(userKey).setValue(doctorname,joinDate)

            val i = Intent(this, ListActivity::class.java)
            startActivity(i)
        } else{


        }
    }


//    databaseReference.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
//        override fun onDataChange(snapshot: DataSnapshot) {
//            var dataExists = false
//            for (childSnapshot in snapshot.children) {
//                val userData = childSnapshot.getValue(Userdata::class.java)
//                if (userData != null && userData.name == name) {
//                    // Data with the same name already exists
//                    dataExists = true
//                    break
//                }
//            }
//
//            if (!dataExists) {
//                // Data doesn't exist, save it
//                val userKey = databaseReference.child("users").push().key
//                if (userKey != null) {
//                    databaseReference.child("users").child(userKey).setValue(user)
//                    Toast.makeText(this@Reception_Activity, "Data saved successfully", Toast.LENGTH_LONG).show()
//                }
//            } else {
//                Toast.makeText(this@Reception_Activity, "Data already exists", Toast.LENGTH_LONG).show()
//            }
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//            // Handle database error if needed
//        }
//    })


}