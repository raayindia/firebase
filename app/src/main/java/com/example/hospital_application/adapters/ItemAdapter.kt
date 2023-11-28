package com.example.hospital_application.adapters
import MyModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital_application.R

class ItemAdapter(private val itemList: List<MyModel>,private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    interface ItemClickListener {
        fun onItemClick(item: MyModel)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val remedyTextView: TextView = itemView.findViewById(R.id.remedy)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)


        //        val subscribeTextView: TextView = itemView.findViewById(R.id.subscribe)
        val addressTextView: TextView = itemView.findViewById(R.id.txt)
        val iconImageView: ImageView = itemView.findViewById(R.id.addi)
        val relativeLayout:RelativeLayout=itemView.findViewById(R.id.fixed_layout)
        val hiddenView: LinearLayout = itemView.findViewById(R.id.hidden_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hospitalcarditem, parent, false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = itemList[position]
        holder.checkBox.isChecked = item.isChecked
        // Bind data to views in the item layout
        holder.remedyTextView.text = item.remedyText
//        holder.subscribeTextView.text = item.subscribeText
        holder.addressTextView.text = item.addressText
//        holder.iconImageView.text = " " // Set text or any other properties for MaterialTextView

        // Set a click listener for the entire item
        holder.itemView.setOnClickListener {
            item.isChecked = !item.isChecked
            holder.checkBox.isChecked = item.isChecked
            notifyItemChanged(position)
            itemClickListener.onItemClick(item)
        }



        // Set a click listener for the lay2 layout
        holder.relativeLayout.setOnClickListener {
            // Check if the hiddenView is currently visible
            if (holder.hiddenView.visibility == View.VISIBLE) {
                // If it's visible, hide it
                holder.hiddenView.visibility = View.GONE
            } else {
                // If it's not visible, show it
                holder.hiddenView.visibility = View.VISIBLE
            }





        }





    }

    override fun getItemCount(): Int {
        return itemList.size
    }



}