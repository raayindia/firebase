package com.example.hospital_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
//import com.razorpay.Checkout
//import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(), PaymentResultListener {

  // Variables for our edit text and button.
  private lateinit var amountEdt: EditText
  private lateinit var payBtn: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_payment)

    // Initializing all our variables.
    amountEdt = findViewById(R.id.idEdtAmt)
    payBtn = findViewById(R.id.paybutton)
// Adding an onClickListener to our button.
    payBtn.setOnClickListener {
      // On the next line, we are getting the amount entered by the user.
      val samount = amountEdt.text.toString()

      // Rounding off the amount.
      val amount = (samount.toFloat() * 100).toInt()

      // Initialize Razorpay account.
      val checkout = Checkout()
      // Set your ID as shown below.
      checkout.setKeyID("rzp_live_KfAucmawQrshTJ")
      // Set image.
      checkout.setImage(R.drawable.ic_launcher_background)
      // Initialize a JSON object.
      val `object` = JSONObject()
      try {
        // Put name.
        `object`.put("name", "RazorPay")

        // Put description.
        `object`.put("description", "Test payment")

        // To set the theme color.
        `object`.put("theme.color", "")

        // Put the currency.
        `object`.put("currency", "INR")

        // Put amount.
        `object`.put("amount", amount)

        // Put mobile number.
        `object`.put("prefill.contact", "9284064503")

        // Put email.
        `object`.put("prefill.email", "chaitanyamunje@gmail.com")

        // Open Razorpay checkout activity.
        checkout.open(this@PaymentActivity, `object`)
      } catch (e: JSONException) {
        e.printStackTrace()
      }
    }


  }

  override fun onPaymentSuccess(s: String) {
    // This method is called on payment success.
    Toast.makeText(this, "Payment is successful : $s", Toast.LENGTH_SHORT).show()

  }

  override fun onPaymentError(i: Int, s: String) {
    // On payment failure.
    Toast.makeText(this, "Payment Failed due to error : $s", Toast.LENGTH_SHORT).show()
  }

}








