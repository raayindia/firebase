package com.example.hospital_application.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital_application.Responses.GetAllHospitalDataResponse
import com.example.hospital_application.Responses.Hospitaldetail
import com.example.hospital_application.databinding.ItemHospitaldetailsBinding
import retrofit2.Callback

class DashboardAdapter(private var pendingOrdersList: List<Hospitaldetail>, private val context: Callback<GetAllHospitalDataResponse>): RecyclerView.Adapter<DashboardAdapter.ViewHolder>(){
    class ViewHolder(private var binding: ItemHospitaldetailsBinding, private var context: Callback<GetAllHospitalDataResponse>) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: Hospitaldetail) {
            //"#${data.store_name}".also { binding.storeName.text = it }
            binding.hospitalNameTextView.text = data.hospitaladminname




        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemHospitaldetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding,context )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = pendingOrdersList[position]
        holder.bind(data)
    }
    override fun getItemCount(): Int {
        return pendingOrdersList.size
    }
}

