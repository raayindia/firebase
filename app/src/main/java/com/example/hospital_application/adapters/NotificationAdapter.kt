package com.example.hospital_application.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital_application.R
import com.example.hospital_application.Responses.NotificationModelResponse


class NotificationAdapter (private val mList: List<NotificationModelResponse>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notif_card_item, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
            val imageView: ImageView = itemView.findViewById(R.id.imgview)
            val textView: TextView = itemView.findViewById(R.id.textView1)
        }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // binds the list items to a view
        val NotificationModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(NotificationModel.image)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = NotificationModel.text

    }

    override fun getItemCount(): Int {
return mList.size
    }
}