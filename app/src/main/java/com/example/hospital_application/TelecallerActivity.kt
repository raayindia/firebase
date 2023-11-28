package com.example.hospital_application

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class TelecallerActivity : AppCompatActivity() {
    private lateinit var cleanlinessRating: RadioGroup
    private lateinit var qualityRating: RadioGroup
    private lateinit var friendly: RadioGroup
    private lateinit var cleanliness2Rating: RadioGroup
    private lateinit var quality2Rating: RadioGroup
    private lateinit var friendly2: RadioGroup
    private lateinit var comment1: EditText
    private lateinit var comment2: EditText

    private lateinit var submitButton: Button
    private var db = FirebaseDatabase.getInstance().reference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.telecaller_layout)
        val role = intent.getStringExtra("role")
        val name = intent.getStringExtra("name")

        val textViewRole = findViewById<TextView>(R.id.role)
        val textViewName = findViewById<TextView>(R.id.name)


        // Set the 'role' in the TextView
        textViewRole.text = role
        textViewName.text=name

        submitButton = findViewById(R.id.submit)
        cleanlinessRating = findViewById(R.id.rating1 )



        qualityRating = findViewById(R.id.rating2)

        friendly = findViewById(R.id.rating3 )


        cleanliness2Rating = findViewById(R.id.rating4 )


        quality2Rating = findViewById(R.id.rating5 )


        friendly2 = findViewById(R.id.rating6 )


        comment1 = findViewById(R.id.edittext_comments )
        comment2 = findViewById(R.id.edittext_suggestions )




        submitButton.setOnClickListener {

            val selectedRadioButtonId1 = cleanlinessRating.checkedRadioButtonId
            val selectedRadioButton1 = findViewById<RadioButton>(selectedRadioButtonId1)

            val selectedRadioButtonId2 = qualityRating.checkedRadioButtonId
            val selectedRadioButton2 = findViewById<RadioButton>(selectedRadioButtonId2)

            val selectedRadioButtonId3 = friendly.checkedRadioButtonId
            val selectedRadioButton3 = findViewById<RadioButton>(selectedRadioButtonId3)

            val selectedRadioButtonId4 = cleanliness2Rating.checkedRadioButtonId
            val selectedRadioButton4 = findViewById<RadioButton>(selectedRadioButtonId4)

            val selectedRadioButtonId5 = quality2Rating.checkedRadioButtonId
            val selectedRadioButton5 = findViewById<RadioButton>(selectedRadioButtonId5)

            val selectedRadioButtonId6 = friendly2.checkedRadioButtonId
            val selectedRadioButton6 = findViewById<RadioButton>(selectedRadioButtonId6)

            if (selectedRadioButton1!= null) {
                val selectedAnswer1 = selectedRadioButton1.text.toString()
                val question1 = "How would you rate the cleanliness of the hospital?"

                val selectedAnswer2 = selectedRadioButton2.text.toString()
                val question2 = "How satisfied are you with the quality of care you received?"

                val selectedAnswer3 = selectedRadioButton3.text.toString()
                val question3 = "Were the hospital staff friendly and helpful?"

                val selectedAnswer4 = selectedRadioButton4.text.toString()
                val question4 = "How would you rate the cleanliness of the hospital?"

                val selectedAnswer5 = selectedRadioButton5.text.toString()
                val question5 = "How satisfied are you with the quality of care you received?"

                val selectedAnswer6 = selectedRadioButton6.text.toString()
                val question6 = "Were the hospital staff friendly and helpful?"

                // Create a feedback object
                val feedback = Feedback(question1, selectedAnswer1,question2, selectedAnswer2,question3, selectedAnswer3,question4, selectedAnswer4,question5, selectedAnswer5,question6, selectedAnswer6)

                // Push the feedback data to the Firebase Realtime Database
                val feedbackRef = db.child("feedback").push()
                feedbackRef.setValue(feedback)
                Toast.makeText(this, "Feedback Information Saved Successfully", Toast.LENGTH_SHORT).show()

                // Clear the selected answer
                cleanlinessRating.clearCheck()
                val i = Intent(this, ListActivity::class.java)
                startActivity(i)

            } else {
                // Handle the case when no RadioButton is selected
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

