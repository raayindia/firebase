package com.example.hospital_application

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import com.example.hospital_application.Responses.DetailXX
import com.example.tablayout.UpdateDelAdd.ProductListAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditProductBottomSheetFragment(private val productModel: DetailXX, private val adapter: ProductListAdapter) : BottomSheetDialogFragment() {
    private lateinit var editName: EditText
    private lateinit var editDesignation: EditText
    private lateinit var editPhone: EditText
    private lateinit var editEmail: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_product_bottom_sheet, container, false)
        editName = view.findViewById(R.id.editname)
        editDesignation = view.findViewById(R.id.editdesignation)
        editPhone = view.findViewById(R.id.editphone)
        editEmail = view.findViewById(R.id.editemail)

        // Initialize UI elements with existing product details
        editName.setText(productModel.staffname)
        editDesignation.setText(productModel.staffdesignation.toString())
        editPhone.setText(productModel.staffphone.toString())
        editEmail.setText(productModel.staffemail)
        // Initialize UI elements and set existing product details in the views

        // Add a Save button click listener to save the changes
        view.findViewById<Button>(R.id.saveButton).setOnClickListener {
            // Get the updated details from the UI elements
            val updatedName = editName.text.toString()
            val updatedDesignation = editDesignation.text.toString()
            val updatedPhone = editPhone.text.toString()
            val updatedEmail = editEmail.text.toString()

            // Update the productModel with the new details
            productModel.staffname = updatedName
            productModel.staffdesignation = updatedDesignation.toInt()
            productModel.staffphone = updatedPhone.toLong()
            productModel.staffemail = updatedEmail

            // Notify the adapter that data has changed
            adapter.notifyDataSetChanged()

            // Dismiss the bottom sheet
            dismiss()
        }
        view.findViewById<ImageView>(R.id.edit_clear).setOnClickListener {
            dismiss()
        }
        view.findViewById<AppCompatButton>(R.id.edit_cancelbtn).setOnClickListener {
            dismiss()
        }

        return view
    }

    companion object {
        const val TAG = "EditProductBottomSheetFragment"
    }
}
