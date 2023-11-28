package com.example.hospital_application.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ControlItemModel
import com.example.hospital_application.EditProductBottomSheetFragment.Companion.TAG
import com.example.hospital_application.R
import com.example.myswitchbutton.MyButtonParameter
import com.example.myswitchbutton.MyButtonView

class ControlItemAdapter(private val mList: List<ControlItemModel>) : RecyclerView.Adapter<ControlItemAdapter.ViewHolder>() {
    private val TAG = "MyAdapter"

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.control_item_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]
        holder.textView2.text = ItemsViewModel.text2

        val isActive = ItemsViewModel.isActive

        holder.switch.isChecked = isActive

        // Update cardview and textcard based on switch state
        updateUI(holder, isActive)

        // Set a click listener for the switch
        holder.switch.setOnCheckedChangeListener { _, isChecked ->
            updateUI(holder, isChecked)

        }

    }
    private fun updateUI(holder: ViewHolder, isActive: Boolean) {
        if (isActive) {
            val colorGreen = Color.parseColor("#4CAF50")
            holder.textViewcard.text = "Active"
            holder.textViewcard.setTextColor(colorGreen)
//            holder.cardview.setCardBackgroundColor(colorGreen)

        } else {
            val colorGrey = Color.parseColor("#FA4848")
            holder.textViewcard.text = "Inactive"
            holder.textViewcard.setTextColor(colorGrey)
//            holder.cardview.setCardBackgroundColor(colorGrey)
        }
    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textViewcontrol)
        val textView2: TextView = itemView.findViewById(R.id.textView2control)
        val textViewcard: TextView = itemView.findViewById(R.id.txtcard)
        val switch: Switch = itemView.findViewById(R.id.simpleSwitch)
        val cardview: CardView = itemView.findViewById(R.id.cardviewSwitch)

    }


}
