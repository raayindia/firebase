package com.example.hospital_application.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital_application.R
import com.example.hospital_application.Responses.AllloginResponse

class LoginDataAdapter(private val loginDataList: List<AllloginResponse>) :
    RecyclerView.Adapter<LoginDataAdapter.LoginViewHolder>() {

    class LoginViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.name)
        val userrole: TextView = itemView.findViewById(R.id.name)

        // Add more views to bind other properties as needed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_login, parent, false)
        return LoginViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LoginViewHolder, position: Int) {
        val loginData = loginDataList[position]
        holder.usernameTextView.text = loginData.data[0].Mobileno.toString()
        // Bind other properties to their respective views
    }

    override fun getItemCount(): Int {
        return loginDataList.size
    }
}
