package com.example.hospital_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospital_application.Responses.Customer_ResponseItem
import com.example.hospital_application.adapters.CustomerAdapter
import com.example.hospital_application.databinding.ActivityCustomerDataBinding

lateinit var dashboardbinding: ActivityCustomerDataBinding
class Customer_Data_Activity : AppCompatActivity() {

    lateinit var dashboardResponse:Customer_ResponseItem
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CustomerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_data)

        linearLayoutManager = LinearLayoutManager(this)

        dashboardbinding.newOrdersRequestsViewRecyclerview.layoutManager = linearLayoutManager
        dashboardbinding.newOrdersRequestsViewRecyclerview.hasFixedSize()

//        dashboarddetails()
        val authtoken =""
    }


//    private fun dashboarddetails(authtoken: String?) {
//        try {
//            val ordersService = ApiClient.buildService(Api_Interface::class.java)
//            val requestCall = ordersService.getLeads(authtoken)
//            requestCall.enqueue(object : retrofit2.Callback<Customer_ResponseItem> {
//                override fun onResponse(
//                    call: retrofit2.Call<Customer_ResponseItem>,
//                    response: retrofit2.Response<Customer_ResponseItem>
//                ) {
//                    try {
//                        dashboardResponse = response.body()!!
//                        when{
//                            response.code() == 200 ->{
//
//
//
//
//
//                            }
//
//
//                        }
//
//
//                    }catch (e: TimeoutException){
//                    }
//                }
//
//                override fun onFailure(call: retrofit2.Call<Customer_ResponseItem>, t: Throwable) {
//
//
//                }
//
//            })
//
//
//        }catch (e: Exception){
//
//        }
//
//    }

}