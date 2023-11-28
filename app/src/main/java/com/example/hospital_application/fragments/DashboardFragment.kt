package com.example.hospital_application.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital_application.ApiClient
import com.example.hospital_application.Api_Interface
import com.example.hospital_application.R
import com.example.hospital_application.Responses.DetailXX
import com.example.hospital_application.Responses.ViewStaffResponse
import com.example.hospital_application.adapters.ViewStaffAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class DashboardFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ViewStaffAdapter


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerView = view.findViewById(R.id.recyclerView_dashboard)
        viewStaffCards()
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        // ArrayList of class ItemsViewModel
//        val data = ArrayList<NotificationModel>()
//
//        // This loop will create 20 Views containing
//        // the image with the count of view
//        for (i in 1..20) {
//            data.add(NotificationModel(R.drawable.calender_icon, "3 Schedules!" + i))
//        }
//
//        // This will pass the ArrayList to our Adapter
//        val adapter = NotificationAdapter(data)
//
//        // Setting the Adapter with the recyclerview
//        recyclerView.adapter = adapter
        return view
    }
    private fun viewStaffCards() {

        val hospitalid = "45"
        val loginService = ApiClient.buildService(Api_Interface::class.java)
        val requestCall = loginService.viewStaffDetails(hospitalid)
        requestCall.enqueue(object : Callback<ViewStaffResponse> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<ViewStaffResponse>,
                response: Response<ViewStaffResponse>
            ) {
                if (response.isSuccessful) {
                    val viewStaffResponse: ViewStaffResponse? = response.body()
                    viewStaffResponse?.let { staffResponse ->
                        val staffList: List<DetailXX> = staffResponse.Details
                        val adapter = ViewStaffAdapter(staffList)
                        recyclerView.layoutManager = LinearLayoutManager(requireContext())
                        recyclerView.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<ViewStaffResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.session_exp),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }
    }