package com.example.hospital_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospital_application.Responses.GetAllHospitalDataResponse
import com.example.hospital_application.adapters.DashboardAdapter
import com.example.hospital_application.databinding.ActivityGetAllDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetAllDetailsActivity : AppCompatActivity() {
    private lateinit var activitygetallbinding: ActivityGetAllDetailsBinding

    private lateinit var getallhospitalresponse: GetAllHospitalDataResponse
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: DashboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitygetallbinding = ActivityGetAllDetailsBinding.inflate(layoutInflater)
        //sharedPreference = SharedPreference(this)
        setContentView(activitygetallbinding.root)

        linearLayoutManager = LinearLayoutManager(this)
        activitygetallbinding.newOrdersRequestsViewRecyclerview.layoutManager = linearLayoutManager
        activitygetallbinding.newOrdersRequestsViewRecyclerview.hasFixedSize()
        getalldetails()




    }
    private fun getalldetails() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://save.al/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(Api_Interface::class.java)
        val call = service.getAllHospitals()
        call.enqueue(object : Callback<GetAllHospitalDataResponse> {
            override fun onResponse(
                call: Call<GetAllHospitalDataResponse>,
                response: Response<GetAllHospitalDataResponse>
            ) {
                if (response.isSuccessful) {
                    getallhospitalresponse = response.body()!!

                    if (getallhospitalresponse != null) {
                        adapter = DashboardAdapter(getallhospitalresponse.Hospitaldetails, this)
                        activitygetallbinding.newOrdersRequestsViewRecyclerview.adapter = adapter

                        // Set the adapter to your RecyclerView here
                    } else {
                        Toast.makeText(
                            this@GetAllDetailsActivity,
                            "No data available",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@GetAllDetailsActivity,
                        "Failed to retrieve data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<GetAllHospitalDataResponse>, t: Throwable) {
                Toast.makeText(
                    this@GetAllDetailsActivity,
                    "Network request failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


    }

    }