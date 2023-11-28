package com.example.tablayout.UpdateDelAdd

import com.example.hospital_application.Responses.DetailXX

interface OnStaffClick {

    fun onUpdate(model: DetailXX, isUpdate: Boolean)

    fun onDelete(model: DetailXX,current:ArrayList<String>)

    fun onStaffSelected(model: DetailXX, staffId: Int)

}
