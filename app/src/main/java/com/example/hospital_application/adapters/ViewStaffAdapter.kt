package com.example.hospital_application.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital_application.R
import com.example.hospital_application.Responses.DetailXX

class ViewStaffAdapter(private val staffList: List<DetailXX>) :
    RecyclerView.Adapter<ViewStaffAdapter.StaffViewHolder>() {

    inner class StaffViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val staffName: TextView = itemView.findViewById(R.id.staff_name)
        private val staffRole: TextView = itemView.findViewById(R.id.staff_role)
        private val email: TextView = itemView.findViewById(R.id.email)
        private val phoneno: TextView = itemView.findViewById(R.id.phone)
        private val viewcard: ImageView = itemView.findViewById(R.id.viewcard)
        val hiddenView: LinearLayout = itemView.findViewById(R.id.hidden_view)
        init {
            // Set a click listener for the lay2 layout
            viewcard.setOnClickListener {

                // Check if the hiddenView is currently visible
                if (hiddenView.visibility == View.VISIBLE) {
                    // If it's visible, hide it
                    hiddenView.visibility = View.GONE
                    viewcard.setImageResource(R.drawable.add_card)
                } else {
                    // If it's not visible, show it
                    hiddenView.visibility = View.VISIBLE
                    viewcard.visibility = View.VISIBLE
                    viewcard.setImageResource(R.drawable.removecard)

                }


            }
        }
        fun bind(staff: DetailXX) {
            staffName.text = staff.staffname // Set staff name
//            staffRole.text = staff.staffdesignation.toString()
            email.text = staff.staffemail
            phoneno.text = staff.staffphone.toString()
            val designationString = when (staff.staffdesignation) {
                1 -> "Super Admin"
                2 -> "Admin"
                3 -> "Telecaller"
                4 -> "Receptionist"
                else -> "Unknown Designation"
            }
            staffRole.text = designationString

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_staff, parent, false)
        return StaffViewHolder(view)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val staff = staffList[position]
        holder.bind(staff)
        val fadeInAnimation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animationmode)
        holder.itemView.startAnimation(fadeInAnimation)

    }

    override fun getItemCount(): Int {
        return staffList.size
    }
}
