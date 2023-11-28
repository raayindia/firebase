package com.example.hospital_application.adapters

import com.example.hospital_application.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital_application.PersonData

class UserAdapter(
    private val userList: MutableList<PersonData>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    // Define an interface for item click events
    interface OnItemClickListener {
        fun onItemClick(user: PersonData)
        fun onCallButtonClick(mobile: String)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        val mobileTextView: TextView = itemView.findViewById(R.id.textViewMobile)
        val docname: TextView = itemView.findViewById(R.id.textViewDoctor)
        val joindate: TextView = itemView.findViewById(R.id.textViewJoinDate)
        val dischargedate: TextView = itemView.findViewById(R.id.textViewDischargeDate)
        val patientLayout: Button = itemView.findViewById(R.id.feedbackButton)
        val callbtn: Button = itemView.findViewById(R.id.callbutton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardviewdata, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]

        val fadeInAnimation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animationmode)
        holder.itemView.startAnimation(fadeInAnimation)

        holder.nameTextView.text = "Name: ${user.name}"
        holder.docname.text = "Doctor Name : ${user.doctorname}"
        holder.joindate.text = "Join Date: ${user.joinDate}"
        holder.mobileTextView.text = "Mobile: ${user.mobile}"
        holder.dischargedate.text = "Discharge Date: ${user.dischargeDate}"

        holder.patientLayout.setOnClickListener {
            itemClickListener.onItemClick(user)
        }
        holder.callbtn.setOnClickListener {
            itemClickListener.onCallButtonClick(user.mobile)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}


