package com.example.tablayout.UpdateDelAdd

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital_application.EditProductBottomSheetFragment
import com.example.hospital_application.R
import com.example.hospital_application.Responses.DetailXX

class ProductListAdapter(
    private val mContext: Context,
    private val mFragmentManager: FragmentManager,
    val onStaffclick: OnStaffClick,
    private val staffList: List<DetailXX>
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    val myArrayList = ArrayList<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_product, parent, false)
        val holder = ProductViewHolder(view)

        // item view is the root view for each row
        holder.itemView.setOnClickListener {

            // adapterPosition give the actual position of the item in the RecyclerView
            val position = holder.adapterPosition
            val model = staffList[position]


        }



        return holder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {


        val product = staffList[position]
//        product.edit = "Edit"
        holder.productName.text = product.staffname
        holder.productPhone.text =
            Editable.Factory.getInstance().newEditable(product.staffphone.toString())
//        holder.designation.text = product.staffdesignation.toString()
        holder.email.text = product.staffemail
        val designationString = when (product.staffdesignation) {
            1 -> "Super Admin"
            2 -> "Admin"
            3 -> "Telecaller"
            4 -> "Receptionist"
            else -> "Unknown Designation"
        }
        holder.designation.text = designationString


        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val product = staffList[position]

        }

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            val product = staffList[position]
            //product.isChecked = isChecked

            if (isChecked) {
                holder.itemView.setBackgroundResource(R.color.black)
                holder.checkbox.isChecked = true
                myArrayList.add(product.staffid.toString())
                onStaffclick.onDelete(product, myArrayList)
            } else {
                holder.itemView.setBackgroundResource(android.R.color.transparent)
                holder.checkbox.isChecked = false
                myArrayList.remove(product.staffid.toString())
                onStaffclick.onDelete(product, myArrayList)
            }
        }

        // Set a click listener for the lay2 layout
        holder.productDelete.setOnClickListener {

            // Check if the hiddenView is currently visible
            if (holder.hiddenView.visibility == View.VISIBLE) {
                // If it's visible, hide it
                holder.hiddenView.visibility = View.GONE
                holder.productDelete.setImageResource(R.drawable.add_card)
            } else {
                // If it's not visible, show it
                holder.hiddenView.visibility = View.VISIBLE
                holder.productDelete.visibility = View.VISIBLE
                holder.productDelete.setImageResource(R.drawable.removecard)

            }


        }


        holder.editbutton.setOnClickListener {
            onStaffclick.onUpdate(product, true)
        }


    }


    override fun getItemCount(): Int {
        return staffList.size
    }


    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPhone: TextView = itemView.findViewById(R.id.product_phone)
        val productDelete: ImageView = itemView.findViewById(R.id.delete_product)
        val designation: TextView = itemView.findViewById(R.id.designationdetails)
        val email: TextView = itemView.findViewById(R.id.email)
        val editbutton: AppCompatButton = itemView.findViewById(R.id.editbtn)
        val password: TextView = itemView.findViewById(R.id.password)
        val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
        val relativeLayout: ConstraintLayout = itemView.findViewById(R.id.rellayout)
        val hiddenView: LinearLayout = itemView.findViewById(R.id.hidden_view)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val product = staffList[position]
                    //product.isChecked = !product.isChecked
                }
            }

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val product = staffList[position]
                    // product.isChecked = isChecked
                }
            }
        }
    }
}


