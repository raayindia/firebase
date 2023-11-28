package com.example.hospital_application.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.hospital_application.R
import com.example.hospital_application.welcomeActivity

class ProfileFirstFragment : Fragment() {
  private lateinit var usersettings: LinearLayout
  private lateinit var billinginfo: LinearLayout
  private lateinit var controlaccess: LinearLayout
  private lateinit var backbtn: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_profile_first, container, false)

        usersettings =v.findViewById(R.id.usersettings)
        billinginfo =v.findViewById(R.id.billing)
        backbtn = v.findViewById(R.id.arrow)
        controlaccess = v.findViewById(R.id.controlaccess)

        backbtn.setOnClickListener {
//
            val intent = Intent(requireContext(), welcomeActivity::class.java)
            startActivity(intent)
        }
        usersettings.setOnClickListener {
            val newFragment = ProfileFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_welcome, newFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }
        billinginfo.setOnClickListener {
            // Inside your fragment class
// For example, on a button click or any action triggering the navigation

// Create an instance of the new fragment you want to navigate to
            val newFragment = InVoiceFragment()

// Get the FragmentManager
            val fragmentManager = requireActivity().supportFragmentManager

// Start a FragmentTransaction
            val fragmentTransaction = fragmentManager.beginTransaction()

// Replace the current fragment with the new one
            fragmentTransaction.replace(R.id.frame_welcome, newFragment) // R.id.container is the container where fragments are placed

// Optionally, add the transaction to the back stack
            fragmentTransaction.addToBackStack(null)

// Commit the transaction
            fragmentTransaction.commit()

        }

        controlaccess.setOnClickListener {
            val newFragment = AdminControlActionFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_welcome, newFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        return v
    }


}