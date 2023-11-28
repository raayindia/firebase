package com.example.hospital_application.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital_application.Responses.Customer_ResponseItem
import com.example.hospital_application.databinding.CustomeritemdisplayBinding

class CustomerAdapter(private val newOrdersList: List<Customer_ResponseItem>) :
    RecyclerView.Adapter<CustomerAdapter.MyViewHolder>() {

    class MyViewHolder(private val context: Int, private var binding: CustomeritemdisplayBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SuspiciousIndentation")
        fun bind(data: Customer_ResponseItem) {






        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = CustomeritemdisplayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(viewType, binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = newOrdersList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return newOrdersList.size
    }




}
