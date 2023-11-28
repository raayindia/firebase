package com.example.hospital_application

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital_application.R
import com.example.hospital_application.Responses.NotificationModelResponse
import com.example.hospital_application.adapters.NotificationAdapter


class NotificationActivity : AppCompatActivity() {
private lateinit var backIcon: ImageView
private lateinit var recyclerView: RecyclerView
private lateinit var adapter: NotificationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_admin)
        backIcon = findViewById(R.id.backIcon)
        recyclerView = findViewById(R.id.recy_notif)
        backIcon.setOnClickListener {
           finish()
        }

    recyclerView.layoutManager = LinearLayoutManager(this)
        // ArrayList of class ItemsViewModel
        val data = ArrayList<NotificationModelResponse>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(NotificationModelResponse(R.drawable.calender_icon, "3 Schedules!" + i))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = NotificationAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter

    }
}