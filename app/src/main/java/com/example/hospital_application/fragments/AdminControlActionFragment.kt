package com.example.hospital_application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital_application.ControlItemModel
import com.example.hospital_application.R
import com.example.hospital_application.adapters.ControlItemAdapter

class AdminControlActionFragment : Fragment() {
    private lateinit var backIcon: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_admin_control_action, container, false)
        // getting the recyclerview by its id
        val recyclerview = v.findViewById<RecyclerView>(R.id.recyclerview)
        backIcon = v.findViewById(R.id.backIcon)

        backIcon.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            if (fragmentManager.backStackEntryCount > 0) {

                fragmentManager.popBackStack()
            } else {
            }
        }

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ControlItemModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(ControlItemModel("Item ", "kdskjfk", true))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = ControlItemAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
return v
    }
}


